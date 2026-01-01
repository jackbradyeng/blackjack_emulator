package Model.Table.Validators;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Table.Bets.Bet;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;

public class InsuranceBetValidator extends BetValidator {

    protected Player player;
    protected PlayerPosition position;
    protected double amount;

    public InsuranceBetValidator(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions,
                                 Player player, PlayerPosition position, Double amount) {
        super(isSimulation, players, playerPositions);
        this.player = player;
        this.position = position;
        this.amount = amount;
    }

    public boolean isValid() {
        return isValidInsuranceBet(player, position, amount);
    }

    /** validates a given insurance bet by verifying that a standard bet already exists on the selected position and
     * that the insurance bet amount is less than or equal to half the size of the standard bet. */
    private boolean isValidInsuranceBet(Player player, PlayerPosition position, double amount) {
        double playerStandardBet = playerHasBet(player, position);
        return playerStandardBet != 0 && amount <= (playerStandardBet / 2);
    }

    /** returns true if the given player already has a standard bet on a hand at the given position. Returns false
     * otherwise. Assumes that the player only has ONE bet on the given position. */
    private double playerHasBet(Player player, PlayerPosition position) {
        for(PlayerHand hand : position.getHands()) {
            for(Map.Entry<Player, Bet> pair : hand.getPairs()) {
                if(player.equals(pair.getKey())) {
                    return pair.getValue().getAmount();
                }
            }
        }
        return 0;
    }
}
