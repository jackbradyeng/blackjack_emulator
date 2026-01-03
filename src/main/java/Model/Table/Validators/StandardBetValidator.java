package Model.Table.Validators;

import java.util.ArrayList;
import Model.Actors.Player;
import Model.Table.Positions.PlayerPosition;
import static Model.Constants.DEFAULT_MIN_BET_SIZE;

public class StandardBetValidator extends BetValidator {

    private final Player player;
    private final PlayerPosition position;
    private final Double amount;

    public StandardBetValidator(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                                Player player, PlayerPosition position, Double amount) {
        super(isSimulation, players, playerPositions);
        this.player = player;
        this.position = position;
        this.amount = amount;
    }

    /** determines if the given player, position, and bet amount are valid. */
    public boolean isValid() {
        if(isSimulation) {
            return isValidSimulationBet();
        } else {
            return isValidStandardBet();
        }
    }

    /** books a standard bet for a player on a given position for a given amount. To be called before the cards are
     * dealt. */
    private boolean isValidStandardBet() {
        return isValidPlayer(player) && isValidPosition(position) && isValidBetSize(amount)
                && hasSufficientChips(player, amount);
    }

    /** same as above but allows the player to overdraw on their stack. Required for collecting statistics such as
     * average profit per hand and expected value as these can be negative. */
    private boolean isValidSimulationBet() {
        return isValidPlayer(player) && isValidPosition(position) && isValidBetSize(amount);
    }

    /** validates a given bet size by verifying that it is greater than the minimum allowed for a standard bet. */
    private boolean isValidBetSize(double betAmount) {
        if(betAmount < DEFAULT_MIN_BET_SIZE) {
            System.out.println("Bet size of: " + (int) betAmount + " is less than the minimum bet size: "
                    + DEFAULT_MIN_BET_SIZE + ".");
            return false;
        } else {
            return true;
        }
    }
}
