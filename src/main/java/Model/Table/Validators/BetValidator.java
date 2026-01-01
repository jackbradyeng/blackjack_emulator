package Model.Table.Validators;

import java.util.ArrayList;
import Model.Actors.Player;
import Model.Table.Positions.PlayerPosition;


public abstract class BetValidator {

    protected boolean isSimulation;
    protected ArrayList<Player> players;
    protected ArrayList<PlayerPosition> playerPositions;

    public BetValidator(boolean isSimulation, ArrayList<Player> players, ArrayList<PlayerPosition> playerPositions) {
        this.isSimulation = isSimulation;
        this.players = players;
        this.playerPositions = playerPositions;
    }

    public abstract boolean isValid();

    /** validates a given player by verifying that they are registered at the table. */
    protected boolean isValidPlayer(Player player) {
        for(Player playerFromList : players) {
            if(player.equals(playerFromList))
                return true;
        }
        return false;
    }

    /** validates a given position by verifying that it is registered at the table. */
    protected boolean isValidPosition(PlayerPosition position) {
        for(PlayerPosition positionFromList : playerPositions) {
            if(position.equals(positionFromList))
                return true;
        }
        return false;
    }
}
