package Model.Cards;

import static Model.Constants.ACE_UPPER_VALUE;

public class Ace extends Card {

    int secondValue = ACE_UPPER_VALUE;

    public Ace(String name, int value) {
        super(name, value);
    }

    /* Ace cards have a second value depending on the aggregate sum of the hand. */
    public int getSecondValue() {
        return secondValue;
    }
}
