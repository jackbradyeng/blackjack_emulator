package Model.Table.Positions;

import Model.Table.Hands.DealerHand;

public class DealerPosition {

    private DealerHand hand;

    public DealerPosition() {
        this.hand = new DealerHand();
    }

    public void clearHand() {
        hand.getCards().clear();
    }

    public DealerHand getHand() {
        return hand;
    }

    public void setHand(DealerHand hand) {
        this.hand = hand;
    }
}
