package Model.Table.Validators;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Table.Bets.Bet;
import Model.Table.Bets.DoubleBet;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;

public class DoubleBetValidator extends BetValidator {

    protected Player player;
    protected PlayerPosition position;
    protected PlayerHand hand;

    public DoubleBetValidator(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                                 Player player, PlayerPosition position, PlayerHand hand) {
        super(isSimulation, players, playerPositions);
        this.player = player;
        this.position = position;
        this.hand = hand;
    }

    public boolean isValid() {
        if(isSimulation) {
            return isValidSimulationDoubleBet();
        } else {
            return isValidDoubleBet();
        }
    }

    /** validates that the betting player has an existing bet on the given hand. */
    public boolean hasExistingBet() {
        return getOriginalBet() != 0;
    }

    /** returns the amount corresponding to the player's original bet on the hand. */
    public double getOriginalBet() {
        for(Map.Entry<Player, Bet> pair : hand.getPairs()) {
            if(pair.getKey().equals(player)) {
                return pair.getValue().getAmount();
            }
        }
        System.out.println("Existing bet not found. Double down bet invalid.");
        return 0;
    }

    private boolean isValidSimulationDoubleBet() {
        return isValidPlayer(player) && isValidPosition(position) && hasExistingBet() && hasNotHit() && hasNotDoubled();
    }

    private boolean isValidDoubleBet() {
        return isValidSimulationDoubleBet() && hasSufficientChips(player, getOriginalBet()); // and has sufficient chips
    }

    /** validates that the given hand has not yet been hit. Opening hands should have a size of two while split hands
     * should have a size of one. */
    private boolean hasNotHit() {
        return hand.getCards().size() < 3;
    }

    /** validates that a particular player has not yet doubled on the given hand. */
    private boolean hasNotDoubled() {
        for(Map.Entry<Player, Bet> pair : hand.getPairs()) {
            if(pair.getKey().equals(player) && pair.getValue() instanceof DoubleBet) {
                System.out.println("Player has already used this action. Double down bet invalid.");
                return false;
            }
        }
        return true;
    }

    /** validates that the player has sufficient chips to place a particular bet. */
    protected boolean hasSufficientChips(Player player, double amount) {
        return player.getChips() >= amount;
    }
}
