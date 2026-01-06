package Controller;

import java.util.Scanner;
import Model.Actors.*;
import Model.Table.*;
import Model.Table.Hands.DealerHand;
import Model.Table.Hands.PlayerHand;

import static Model.Constants.*;

public class Controller {

    // private instance variables
    private final boolean isSimulation;
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
        table.printWelcomeMessage();

        while(isRunning) {
            table.startupRoutine();
            initialWager();
            table.drawRoutine();
            playerActions();
            gamePause("Dealer drawing in...");
            table.printDealerHand();
            table.executeDealerStrategy();
            gamePause("Printing results in...");
            table.windDownRoutine();
        }

        System.out.println("Thanks for playing!");
    }

    /** initiates a countdown before revealing the dealer's hand. Creates a bit of suspense. */
    private void gamePause(String output) {
        System.out.println(output);
        threadSleep();
        System.out.println("3...");
        threadSleep();
        System.out.println("2...");
        threadSleep();
        System.out.println("1...");
    }

    /** thread sleep routine.*/
    private void threadSleep() {
        try {Thread.sleep(DEFAULT_COUNTDOWN_TIME);}
        catch (InterruptedException i) {Thread.currentThread().interrupt();}
    }

    /** prints summary statistics following a round of blackjack, including average profit per hand and the expected
     * value percentage. */
    private void printStatistics(int handNumber, double runningProfit, double averageProfitPerHand,
                                double expectedValuePerHand) {
        System.out.print("\n");
        System.out.println("---- Summary Statistics ----");
        System.out.println("Hand No. : " + handNumber);
        System.out.println("Running Profit (Loss) : " + runningProfit);
        System.out.println("Average Profit Per Hand: " + averageProfitPerHand);
        System.out.println("Expected Value Per Hand: " + expectedValuePerHand * 100 + "%");
    }

    /** initializes the first round of betting. This is a non-parameterized method for regular command line
     * interactions. */
    private void initialWager() {
        // first bet
        for(Player player : table.getPlayers()) {
            while(true) {
                System.out.println("Would you like to place a bet? Enter Y/N.");
                try {
                    String response = scanner.next();
                    if (processStandardBet(player, response)) {
                        break;
                    }
                } catch (RuntimeException e) {
                    System.out.println("Please enter a valid response (Y/N).");
                }
            }

            // additional bets
            while(true) {
                System.out.println("Would you like to place another bet?");
                try {
                    String response = scanner.next();
                    if (response.equalsIgnoreCase("N")) {
                        break;
                    } else {
                        processStandardBet(player, response);
                    }
                } catch (RuntimeException e) {
                    System.out.println("Please enter a valid response (Y/N).");
                }
            }
        }
    }

    /** processes a standard bet during the initial wager phase. */
    private boolean processStandardBet(Player player, String action) {
        if (action.equalsIgnoreCase("Y")) {
            System.out.println("Specify your bet size. You have " + (int) player.getChips() + " chips." +
                    " The minimum bet size is " + DEFAULT_MIN_BET_SIZE + " chips.");
            double playerBet = scanner.nextDouble();
            System.out.println("Which position would you like to bet on? There are " +
                    table.getNumberOfPositions() + " total positions.");
            int position = scanner.nextInt();
            table.bookStandardBet(player, table.getPlayerPositionsIterable().get(position - 1), playerBet);
            return true;
        } else return action.equalsIgnoreCase("N");
    }

    /** handles cases where the player has a natural blackjack in non-simulation games. */
    private boolean handleBlackjackCase(PlayerHand hand) {
        if(!hand.hasInsuranceOption(table.getDealerHand())) {
            return false;
        } else {
            System.out.println("Would you like to buy insurance? (Y/N)");
            try {
                String playerAction = scanner.next();
                if (playerAction.equalsIgnoreCase("Y")) {
                    handleInsuranceCase(hand);
                    return false;
                } else return !playerAction.equalsIgnoreCase("N");
            } catch (RuntimeException e) {
                System.out.println("Please enter a valid input.");
                return true;
            }
        }
    }

    /** handles cases where the player orders insurance in non-simulation games. */
    private void handleInsuranceCase(PlayerHand hand) {
        table.handlePlayerAction(hand.getActingPlayer(), hand, INSURANCE);
    }

    // core gameplay loop involving hitting, standing, splitting, and/or buying insurance
    private void playerActions() {
        for (int i = 0; i < table.getActiveHands().size(); i++) {
            PlayerHand hand = table.getActiveHands().get(i);
            System.out.println("Position no. " + hand.getPosition().getPositionNumber());
            boolean playerCanAct = true;

            while (playerCanAct) {
                if (hand.isBust()) {
                    playerCanAct = false;
                } else if (hand.isBlackjack()) {
                    playerCanAct = handleBlackjackCase(hand);
                } else {
                    System.out.println("Player " + hand.getActingPlayer() + " to act. Select an action:");
                    DealerHand dealerHand = table.getDealerHand();
                    if (hand.isBlackjack()) {
                        handleBlackjackCase(hand);
                    } else if (hand.hasSplitOption() && hand.hasInsuranceOption(dealerHand) && !hand.hasHit()) {
                        System.out.println("HIT | STAND | DOUBLE | SPLIT | INSURANCE");
                    } else if(!hand.hasSplitOption() && hand.hasInsuranceOption(dealerHand) && !hand.hasHit()) {
                        System.out.println("HIT | STAND | INSURANCE");
                    } else if (hand.hasSplitOption() && !hand.hasInsuranceOption(dealerHand) && !hand.hasHit()) {
                        System.out.println("HIT | STAND | DOUBLE | SPLIT");
                    } else if (!hand.hasSplitOption() && !hand.hasInsuranceOption(dealerHand) && !hand.hasHit()) {
                        System.out.println("HIT | STAND | DOUBLE");
                    } else if (hand.hasSplitOption() && hand.hasInsuranceOption(dealerHand)) {
                        System.out.println("HIT | STAND | SPLIT | INSURANCE");
                    } else if (hand.hasSplitOption() && !hand.hasInsuranceOption(dealerHand)) {
                        System.out.println("HIT | STAND | SPLIT");
                    } else if (!hand.hasSplitOption() && hand.hasInsuranceOption(dealerHand)) {
                        System.out.println("HIT | STAND | INSURANCE");
                    } else {
                        System.out.println("HIT | STAND");
                    }
                    String playerAction;
                    try {
                        playerAction = scanner.next().toUpperCase();
                        table.handlePlayerAction(hand.getActingPlayer(), hand, playerAction);
                        table.printActivePlayerHands();
                        table.printDealerFirstCard();
                        if (playerAction.equalsIgnoreCase(STAND)) {
                            playerCanAct = false;
                        } else if (playerAction.equalsIgnoreCase(DOUBLE)) {
                            playerCanAct = false;
                        }
                    } catch (RuntimeException e) {
                        System.out.println("Invalid input.");
                    }
                }
            }
        }
    }
}
