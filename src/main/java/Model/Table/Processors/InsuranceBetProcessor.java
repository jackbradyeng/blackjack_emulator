package Model.Table.Processors;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Table.Bets.Bet;
import Model.Table.Bets.InsuranceBet;
import Model.Table.Positions.PlayerPosition;
import Model.Table.Validators.InsuranceBetValidator;


public class InsuranceBetProcessor implements BetProcessor {

    private final Player player;
    private final PlayerPosition position;
    private final Double amount;
    private final InsuranceBetValidator validator;

    public InsuranceBetProcessor(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                                Player player, PlayerPosition position, Double amount) {
        this.player = player;
        this.position = position;
        this.amount = amount;
        this.validator = new InsuranceBetValidator(isSimulation, players, playerPositions, player, position, amount);
    }

    public void process() {
        bookInsuranceBet(player, position, amount);
    }

    /** books an insurance bet for a player on a given position for a given amount. To be called AFTER the cards are
     * dealt. */
    private void bookInsuranceBet(Player player, PlayerPosition position, double amount) {
        if (validator.isValid()) {
            bookBet(player, position, amount);
        } else {
            System.out.println("INVALID BET");
        }

    }

    /** books an insurance bet for a player on a given position for a given amount. */
    private void bookBet(Player player, PlayerPosition position, double amount) {
        InsuranceBet iBet = new InsuranceBet(amount, true);
        Map.Entry<Player, Bet> entry = Map.entry(player, iBet);
        position.getHands().getFirst().getPairs().add(entry);
        player.dispenseChips(amount);
        System.out.println("Your insurance bet has been placed! You have " + (int) player.getChips() +
                " chips remaining.");
    }
}
