package Model.Table.Validators;

import java.util.ArrayList;
import Model.Actors.Player;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;

public class SplitBetValidator extends BetValidator {

    protected Player player;
    protected PlayerPosition position;

    public SplitBetValidator(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                              Player player, PlayerPosition position, PlayerHand hand) {
        super(isSimulation, players, playerPositions, hand);
        this.player = player;
        this.position = position;
    }

    public boolean isValid() {
        if(isSimulation) {
            return isValidSimulationSplit();
        } else {
            return isValidStandardSplit();
        }
    }

    /** ensures that the player has an existing bet and that the hand's split option is live. */
    private boolean isValidSimulationSplit() {
        return isValidPlayer(player) && isValidPosition(position) && hasExistingBet(player) && hasNotHit() &&
                hand.hasSplitOption();
    }

    /** ensures that all the requirements for a simulation bet are met AND that the player has sufficient chips to post
     * the split bet. */
    private boolean isValidStandardSplit() {
        return isValidSimulationSplit() && hasSufficientChips(player, getOriginalBet(player));
    }

    /** validates that the given hand has not yet been hit. Opening hands should have a size of two while split hands
     * should have a size of one. */
    private boolean hasNotHit() {
        return hand.getCards().size() < 3;
    }
}
