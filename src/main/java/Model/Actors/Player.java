package Model.Actors;

import Model.Actors.Strategies.PlayerStrategy;
import Model.Table.Hands.DealerHand;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;

public class Player extends Actor {

    PlayerPosition defaultPosition;
    PlayerStrategy strategy;

    public Player(double startingChips) {
        super(startingChips);
        this.strategy = new PlayerStrategy();
    }

    public PlayerPosition getDefaultPosition() {
        return defaultPosition;
    }

    public void setDefaultPosition(PlayerPosition defaultPosition) {
        this.defaultPosition = defaultPosition;
    }

    public String invokeStrategy(PlayerHand playerHand, DealerHand dealerHand) {
        return getStrategy().executeStrategy(playerHand, dealerHand);
    }

    public PlayerStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(PlayerStrategy strategy) {
        this.strategy = strategy;
    }
}
