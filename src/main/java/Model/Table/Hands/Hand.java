package Model.Table.Hands;

import java.util.ArrayList;
import static Model.Constants.*;
import Model.Cards.Ace;
import Model.Cards.Card;
import Model.Cards.Deck;


public class Hand {

    // stores the cards allocated to the hand
    protected ArrayList<Card> cards;

    // stores the numeric value of the hand
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

    public void receiveCard(Card card) {
        cards.add(card);
    }

    public boolean isBust() {
        return handValue > BLACKJACK_CONSTANT;
    }

    public boolean isBlackjack() {
        return handValue == BLACKJACK_CONSTANT;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getHandValue() {
        return handValue;
    }

    public void setHandValue(Deck deck) {
        this.handValue = calculateHandValue(deck);
    }

    /* Needs to handle the ace value as well. I think this works best when we value the ace card last. */
    private int calculateHandValue(Deck deck) {
        int handValue = 0;

        // first sweep, sums the total of non-Ace cards
        for(Card card : cards) {
            if(!(card instanceof Ace)) {
                handValue += card.getValue();
            }
        }

        // second sweep, sums the total of Ace cards
        for(Card card : cards) {
            if(card instanceof Ace) {
                if(handValue < ACE_UPPER_VALUE) {
                    handValue += ACE_UPPER_VALUE;
                } else {
                    handValue += ACE_LOWER_VALUE;
                }
            }
        }
        return handValue;
    }
}
