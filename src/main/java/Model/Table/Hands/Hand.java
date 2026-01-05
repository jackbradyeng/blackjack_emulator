package Model.Table.Hands;

import java.util.ArrayList;
import static Model.Constants.*;
import Model.Cards.Ace;
import Model.Cards.Card;

public class Hand {

    // stores the cards allocated to the hand
    protected ArrayList<Card> cards;

    // stores the numerical value of the hand
    protected int handValue;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Card card : cards) {
            builder.append(card.getName());
            builder.append(" ");
        }
        return builder.toString();
    }

    /** adds a card to the hand. */
    public void receiveCard(Card card) {
        cards.add(card);
    }

    /** returns whether the hand is bust or not.*/
    public boolean isBust() {
        return handValue > BLACKJACK_CONSTANT;
    }

    /** returns whether the hand is a blackjack or not. */
    public boolean isBlackjack() {
        return handValue == BLACKJACK_CONSTANT;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getHandValue() {
        return handValue;
    }

    public void setHandValue() {
        this.handValue = calculateHandValue();
    }

    /** calculates the final hand value. Sums the non-ace cards before considering aces. */
    private int calculateHandValue() {
        int handValue = 0;
        int aceCount = 0;

        // first sweep, sums the total of non-Ace cards
        for(Card card : cards) {
            if(!(card instanceof Ace)) {
                handValue += card.getValue();
            }
        }

        // second sweep, sums the total of Ace cards
        for(Card card : cards) {
            if(card instanceof Ace) {
                aceCount++;
                /* if an ace has already been counted then any subsequent aces need to be assigned their lower value
                rather than their higher value. */
                if(aceCount > 0) {
                    if(handValue + ACE_UPPER_VALUE < BLACKJACK_CONSTANT) {
                        handValue += ACE_UPPER_VALUE;
                    } else {
                        handValue += ACE_LOWER_VALUE;
                    }
                } else if(handValue < ACE_UPPER_VALUE) {
                    handValue += ACE_UPPER_VALUE;
                } else {
                    handValue += ACE_LOWER_VALUE;
                }
            }
        }
        return handValue;
    }
}
