package Model.Actors;

import Model.Actors.Strategies.DealerStrategy;
import Model.Table.Positions.DealerPosition;

public class Dealer extends Actor {

    DealerPosition position;
    DealerStrategy strategy;

    public Dealer(double startingChips) {
        super(startingChips);
        this.strategy = new DealerStrategy();
    }

    public int getHandValue() {
        return position.getHand().getHandValue();
    }

    public DealerPosition getPosition() {
        return position;
    }

    public void setPosition(DealerPosition position) {
        this.position = position;
    }

    public String executeStrategy() {
        return getStrategy().executeStrategy(position.getHand());
    }

    public DealerStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(DealerStrategy strategy) {
        this.strategy = strategy;
    }
}
