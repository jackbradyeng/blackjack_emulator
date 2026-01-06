package Model.Table.Validators;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Table.Bets.Bet;
import Model.Table.Bets.InsuranceBet;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;

public class InsuranceBetValidator extends BetValidator {

    protected Player player;
    protected PlayerPosition position;
    protected double amount;

    public InsuranceBetValidator(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                                 Player player, PlayerPosition position, PlayerHand hand, Double amount) {
        super(isSimulation, players, playerPositions, hand);
        this.player = player;
        this.position = position;
        this.amount = amount;
    }

    public boolean isValid() {
        return isValidInsuranceBet(player, amount);
    }

    /** returns whether the given player has an insurance bet on the hand. */
    private boolean hasInsuranceBet(Player player) {
        for (Map.Entry<Player, Bet> pair : hand.getPairs()) {
            if (pair.getKey().equals(player) && pair.getValue() instanceof InsuranceBet) {
                return true;
            }
        }
        return false;
    }

    /** validates a given insurance bet by verifying that a standard bet already exists on the selected position and
     * that the insurance bet amount is less than or equal to half the size of the standard bet. */
    private boolean isValidInsuranceBet(Player player, double amount) {
        return hasExistingBet(player) && !hasInsuranceBet(player) && amount <= (getOriginalBet(player) / 2);
    }
}
