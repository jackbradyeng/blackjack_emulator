package Controller;

import java.util.Scanner;
import Model.Actors.*;
import Model.Table.*;
import Model.Table.Hands.DealerHand;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;
import static Model.Constants.*;

public class Controller {

    // private instance variables
    private boolean isSimulation;
    private boolean isRunning;
    private final Scanner scanner;
    private final Table table;

    // default constructor
    public Controller(int playerCount, int deckCount, boolean isSimulation) {
        this.isSimulation = isSimulation;
        this.isRunning = true;
        this.scanner = new Scanner(System.in);
        this.table = new Table(playerCount, deckCount, isSimulation);
    }

    /** initializes the emulator. */
    public void startGame() {
        if(isSimulation) {runSimulation();} else {runGameLoop(); } }

    /// Monte Carlo Simulation
    public void runSimulation() {
        table.printWelcomeMessage();
        Player mainPlayer = table.getPlayers().getFirst();

        // blackjack count, win count, loss count, stand count

        for(int i = 0; i < DEFAULT_NUMBER_OF_ITERATIONS; i++) {
            table.startupRoutine();
            table.bookStandardBet(mainPlayer, mainPlayer.getDefaultPosition(), DEFAULT_PLAYER_BET_AMOUNT);
            table.drawRoutine();
            table.executePlayerStrategyForAll();
            table.printDealerHand();
            table.executeDealerStrategy();
            table.windDownRoutine();
            double runningProfit = mainPlayer.getChips() - DEFAULT_PLAYER_STARTING_CHIPS;
            double averageProfitPerHand = runningProfit / ((double) i + 1);
            double expectedValuePerHand = averageProfitPerHand / DEFAULT_PLAYER_BET_AMOUNT;
            printStatistics(i + 1, runningProfit, averageProfitPerHand, expectedValuePerHand);
        }
    }

    /// Interactive Game Loop
    public void runGameLoop() {

        while(isRunning) {
            table.startupRoutine();
            initialWager();
            table.drawRoutine();
            playerActions();
            System.out.println("PLAYER ACTIONS HAVE BEEN PROCESSED" + "\n");
            table.printDealerHand();
            table.executeDealerStrategy();
            table.windDownRoutine();
        }
    }

    /** prints summary statistics following a round of blackjack, including average profit per hand and the expected
     * value percentage. */
    public void printStatistics(int handNumber, double runningProfit, double averageProfitPerHand,
                                double expectedValuePerHand) {
        System.out.print("\n");
        System.out.println("---- Summary Statistics ----");
        System.out.println("Hand No. : " + handNumber);
        System.out.println("Running Profit (Loss) : " + runningProfit);
        System.out.println("Average Profit Per Hand: " + averageProfitPerHand);
        System.out.println("Expected Value Per Hand: " + expectedValuePerHand * 100 + "%");
    }

    // parameterized method for simulations
    public void initialWager(boolean placeBet, Player player, double amount, int position) {
        if(placeBet) {
            table.bookStandardBet(player, table.getPlayerPositionsIterable().get(position - 1), amount);
            System.out.println("Bet placed on position " + position + " for " + amount + " chips.");
        }
    }

    // initializes the first round of betting
    // non-parameterized method for regular command line interactions
    public void initialWager() {

        // flag used to determine when a player has waged all their bets
        boolean initialWager;

        // initial betting round
        for(Player player : table.getPlayers()) {

            initialWager = true;

            while(initialWager) {

                System.out.println("Would you like to place a bet? Enter Y/N");
                String response = scanner.next();

                if (response.equalsIgnoreCase("Y")) {

                    System.out.println("Specify your bet size. You have " + (int) player.getChips() + " chips.");
                    int playerBet = scanner.nextInt();
                    System.out.println("Which position would you like to bet on? There are " +
                                table.getNumberOfPositions() + " total positions.");
                    int position = scanner.nextInt();

                    table.bookStandardBet(player, table.getPlayerPositionsIterable().get(position - 1), playerBet);

                } else if (response.equalsIgnoreCase("N")) {
                    initialWager = false;
                } else {
                    System.out.println("Please enter a valid response. (Y/N)");
                }
            }
        }
    }

    // core gameplay loop involving hitting, standing, splitting, and/or buying insurance
    public void playerActions() {

        // store an instance of the dealer hand for easy reference
        DealerHand dealerHand = table.getDealerPosition().getHand();

        for(PlayerPosition position : table.getPlayerPositionsIterable()) {
            for(PlayerHand hand : position.getHands()) {
                if(hand.hasBet()) {
                    System.out.println("Position no. " + hand.getPosition().getPositionNumber());

                    boolean playerCanAct = true;
                    boolean hasBoughtInsurance = false;

                    while(playerCanAct) {

                        if(hand.isBust()) {
                            playerCanAct = false;
                        } else if(hand.isBlackjack()) {
                            System.out.println("Blackjack!");
                            if(hand.hasInsuranceOption(dealerHand) & !hasBoughtInsurance) {
                                // handleInsuranceCase
                            }
                            playerCanAct = false;
                        } else {
                            System.out.println("Player " + hand.getActingPlayer() + " to act. Select an action:");

                            if(hand.hasSplitOption() && hand.hasInsuranceOption(dealerHand)) {
                                System.out.println("HIT | STAND | SPLIT | INSURANCE");
                            } else if(hand.hasSplitOption() && !hand.hasInsuranceOption(dealerHand)) {
                                System.out.println("HIT | STAND | SPLIT");
                            } else if(!hand.hasSplitOption() && hand.hasInsuranceOption(dealerHand)) {
                                System.out.println("HIT | STAND | INSURANCE");
                            } else {
                                System.out.println("HIT | STAND");
                            }
                            String playerAction = scanner.next();
                            playerCanAct = handlePlayerAction(playerAction, hand);
                        }
                    }
                }
            }
        }
    }

    private boolean handlePlayerAction(String action, PlayerHand hand) {
        if(action.equalsIgnoreCase("HIT")) {
            table.hit(hand);
            table.printActivePlayerHands();
            table.printDealerHand();
            return !hand.isBust();
        } else if(action.equalsIgnoreCase("STAND")) {
            return false;
        } else if(action.equalsIgnoreCase("INSURANCE")) {
            return true;
        }
        else {
            System.out.println("Please enter a valid response.");
            return true;
        }
    }
}
