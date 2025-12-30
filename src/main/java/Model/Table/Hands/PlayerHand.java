package Model.Table.Hands;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Cards.Ace;
import Model.Table.Bets.Bet;
import Model.Table.Positions.PlayerPosition;

public class PlayerHand extends Hand {

    // the position with which the hand is associated.
    PlayerPosition position;

    /* Player-bet pairs for each position. This is necessary because players can "back-bet" other player's hands.
     * A special type of data structure is required for logging the bets on each position. This is because Blackjack
     * allows players to buy insurance on multiple positions, meaning that the keys in the map will not be unique. Since
     * the Map.Entry interface does not enforce key-value uniqueness I've chosen to compose it in an ArrayList of entries
     * which logs all the bets on a particular position. This data structure is similar to a Multimap but without the
     * O(1) lookup time. */
    private ArrayList<Map.Entry<Player, Bet>> pairs;

    /* The acting player is the player with agency in the hand. Other players may still "back-bet" the position, but
     * ultimate decision-making authority resides with the acting player. By default, this is the plater assigned
     * to the position. */
    private Player actingPlayer;

    public PlayerHand(PlayerPosition position) {
        super();
        this.position = position;
        this.pairs = new ArrayList<>();
    }

    public boolean hasSplitOption() {
        return cards.get(0).getValue() == cards.get(1).getValue();
    }

    public boolean hasInsuranceOption(DealerHand hand) {
        return hand.getCards().getFirst() instanceof Ace;
    }

    public boolean hasBet() {
        return !pairs.isEmpty();
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public ArrayList<Map.Entry<Player, Bet>> getPairs() {
        return pairs;
    }

    public void setPairs(ArrayList<Map.Entry<Player, Bet>> pairs) {
        this.pairs = pairs;
    }

    public Player getActingPlayer() {
        return actingPlayer;
    }

    public void setActingPlayer(Player actingPlayer) {
        this.actingPlayer = actingPlayer;
    }
}
