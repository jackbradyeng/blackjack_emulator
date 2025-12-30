package Model.Actors;

public class Actor {

    private double chips;

    public Actor(double startingChips) {
        this.chips = startingChips;
    }

    public void receiveChips(double chips) {
        this.chips += chips;
    }

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
