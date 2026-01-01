package Model.Table.Processors;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Table.Bets.Bet;
import Model.Table.Positions.PlayerPosition;
import Model.Table.Validators.StandardBetValidator;

public class StandardBetProcessor implements BetProcessor {

    private final Player player;
    private final PlayerPosition position;
    private final Double amount;
    private final StandardBetValidator validator;

    public StandardBetProcessor(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                                Player player, PlayerPosition position, Double amount) {
        this.player = player;
        this.position = position;
        this.amount = amount;
        this.validator = new StandardBetValidator(isSimulation, players, playerPositions, player, position, amount);
    }

    public void process() {
        bookStandardBet(player, position, amount);
    }

    private void bookStandardBet(Player player, PlayerPosition position, double amount) {
        if(validator.isValid()) {
            bookBet(player, position, amount);
        } else {
            System.out.println("INVALID BET!");
        }
    }

    /** books a bet for a player on a given position for a given amount. */
    private void bookBet(Player player, PlayerPosition position, double amount) {
        Bet playerBet = new Bet(amount, false);
        Map.Entry<Player, Bet> entry = Map.entry(player, playerBet);
            /* a key-value pair is stored in the hand's log so it can be accessed later when payouts are calculated and
            transferred. Also note: arraylists maintain insertion order so we can index the list by the position's
            number. */
        position.getHands().getFirst().getPairs().add(entry);
        player.dispenseChips(amount);
        System.out.println("Your bet has been placed! You have " + (int) player.getChips() +
                " chips remaining.");
    }
}
