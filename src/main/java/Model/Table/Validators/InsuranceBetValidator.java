package Model.Table.Validators;

import java.util.ArrayList;
import Model.Actors.Player;
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

    /** validates a given insurance bet by verifying that a standard bet already exists on the selected position and
     * that the insurance bet amount is less than or equal to half the size of the standard bet. */
    private boolean isValidInsuranceBet(Player player, double amount) {
        return hasExistingBet(player) && amount <= (getOriginalBet(player) / 2);
    }
}
