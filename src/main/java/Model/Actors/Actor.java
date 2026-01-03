package Model.Actors;

public class Actor {

    private double chips;

    public Actor(double startingChips) {
        this.chips = startingChips;
    }

    /** receipts a given number of chips to the actor's total. */
    public void receiveChips(double chips) {
        this.chips += chips;
    }

    /** dispenses a given number of chips from the actor's total. */
    public void dispenseChips(double chips) {
        this.chips -= chips;
    }

    public double getChips() {
        return chips;
    }

    public void setChips(double chips) {
        this.chips = chips;
    }
}
