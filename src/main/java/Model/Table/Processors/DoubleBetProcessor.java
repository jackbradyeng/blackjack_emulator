package Model.Table.Processors;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Table.Bets.Bet;
import Model.Table.Bets.DoubleBet;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;
import Model.Table.Validators.DoubleBetValidator;

public class DoubleBetProcessor implements BetProcessor {

    private final Player player;
    private final PlayerPosition position;
    private final DoubleBetValidator validator;

    public DoubleBetProcessor(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                                Player player, PlayerPosition position, PlayerHand hand) {
        this.player = player;
        this.position = position;
        this.validator = new DoubleBetValidator(isSimulation, players, playerPositions, player, position, hand);
    }

    public void process() {
        if(validator.isValid()) {
            double amount = validator.getOriginalBet();
            bookBet(player, position, amount);
        }
    }

    /** books a bet for a player on a given position for a given amount. */
    private void bookBet(Player player, PlayerPosition position, double amount) {
        Bet playerBet = new DoubleBet(amount);
        Map.Entry<Player, Bet> entry = Map.entry(player, playerBet);
        position.getHands().getFirst().getPairs().add(entry);
        player.dispenseChips(amount);
        System.out.println("Your double down bet has been placed! You have " + (int) player.getChips() +
                " chips remaining.");
    }
}
