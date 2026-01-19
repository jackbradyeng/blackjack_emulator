package Model.Cards;

import java.util.*;
import static Model.Constants.*;
import Exceptions.DeckCountException;

public class Deck {

    private int copies; // the number of standard decks used
    private final ArrayList<String> suits;
    private final Map<String, Integer> cardValueMap;
    private ArrayList<Card> deck;
    private Random random;

    public Deck(int copies) {
        this.suits = new ArrayList<>();
        this.cardValueMap = new HashMap<>();
        this.deck = new ArrayList<>();
        this.random = new Random();
        processDeckCount(copies);
        createSuits();
        mapCardValues(cardValueMap);
        populate();
        shuffle();
    }

    /** validates that an appropriate number of deck copies are passed to the constructor. */
    private void processDeckCount(int copies) throws DeckCountException {
        if(copies < 1) {
            throw new DeckCountException("The deck must use at least one copy.");
        } else {
            this.copies = copies;
        }
    }

    /** populates the suits list with each of the major suits. Used to streamline card creation process. */
    private void createSuits() {
        suits.add("Hearts");
        suits.add("Diamonds");
        suits.add("Clubs");
        suits.add("Spades");
    }

    /** maps cards to their default values. By default, the ace is mapped to its higher value. */
    private void mapCardValues(Map<String, Integer>  cardValuesMap) {
        for(String suit : suits) {
            cardValuesMap.put("TwoOf" + suit, 2);
            cardValuesMap.put("ThreeOf" + suit, 3);
            cardValuesMap.put("FourOf" + suit, 4);
            cardValuesMap.put("FiveOf" + suit, 5);
            cardValuesMap.put("SixOf" + suit, 6);
            cardValuesMap.put("SevenOf" + suit, 7);
            cardValuesMap.put("EightOf" + suit, 8);
            cardValuesMap.put("NineOf" + suit, 9);
            cardValuesMap.put("TenOf" + suit, 10);
            cardValuesMap.put("JackOf" + suit, 10);
            cardValuesMap.put("QueenOf" + suit, 10);
            cardValuesMap.put("KingOf" + suit, 10);
            cardValuesMap.put("AceOf" + suit, ACE_UPPER_VALUE);
        }
    }

    /** populates the deck with cards from the map. */
    private void populate() {
        for (int i = 0; i < this.copies; i++) {
            for (Map.Entry<String, Integer> pair : cardValueMap.entrySet()) {
                Card newCard;
                if (pair.getKey().contains("Ace")) {
                    newCard = new Ace(pair.getKey(), pair.getValue());
                } else {
                    newCard = new Card(pair.getKey(), pair.getValue());
                }
                deck.add(newCard);
            }
        }
    }

    /** shuffles the deck. */
    private void shuffle() {
        simpleShuffle();
    }

    /** uses a modified version of the Fisher-Yates shuffling algorithm. Fisher-Yates involves iterating over a set of
     * integers, and for each integer, selecting a random index in the iteration range, and swapping it with the
     * current integer. Essentially, this method performs Fisher-Yates but for each of the decks one by one, maintaining
     * their respective boundaries. This is how Blackjack decks are designed to be shuffled, since it minimizes the
     * chance of duplicate cards being dealt in a single hand. */
    private void compoundShuffle() {
        for(int i = this.copies; i > 0; i--) {
            for(int j = NUMBER_OF_CARDS_PER_DECK; j > 0; j--) {
                int upper = (i * NUMBER_OF_CARDS_PER_DECK) - 1;
                int lower = ((i - 1) * NUMBER_OF_CARDS_PER_DECK);
                int rand = random.nextInt(lower, upper);
                Collections.swap(deck, rand, (i * j) - 1);
            }
        }
    }

    /** uses the Fisher-Yates shuffling algorithm for the whole deck stack. */
    private void simpleShuffle() {
        for (int i = 0; i < this.copies * NUMBER_OF_CARDS_PER_DECK; i++) {
            int lower = 0;
            int upper = this.copies * NUMBER_OF_CARDS_PER_DECK;
            int rand = random.nextInt(lower, upper);
            Collections.swap(deck, rand, i);
        }
    }

    /** deals a card from the deck. */
    public Card deal() {
        if(!deck.isEmpty()) {
            return deck.removeLast();
        }
        return null;
    }

    /** returns the size of the deck. */
    public int size() {
        return deck.size();
    }

    /** clears the stack and creates a fresh deck instance. */
    public void createNewDeck() {
        deck.clear();
        populate();
        shuffle();
    }

    /// testing methods ///
    public ArrayList<Card> getDeck() {
        return deck;
    }
}
