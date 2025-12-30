package Model.Table.Bets;

public class Bet {

    private double amount;

    public Bet(double amount, boolean isInsurance) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
