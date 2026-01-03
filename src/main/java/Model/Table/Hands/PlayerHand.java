package Model.Table.Hands;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Cards.Ace;
import Model.Table.Bets.Bet;
import Model.Table.Positions.PlayerPosition;

public class PlayerHand extends Hand {

    // the position to which the hand is allocated.
    private PlayerPosition position;

    /* a list of player-bet pairs for each position. This is necessary because players can "back-bet" other player's hands. */
    private ArrayList<Map.Entry<Player, Bet>> pairs;

    /* The acting player is the player with agency in the hand. Other players may still "back-bet" the position, but
     * ultimately the acting player chooses the action. By default, this is the player assigned to the position. */
    private Player actingPlayer;

    public PlayerHand(PlayerPosition position) {
        super();
        this.position = position;
        this.pairs = new ArrayList<>();
    }

    /** returns whether the hand can be split. */
    public boolean hasSplitOption() {
        return cards.get(0).getValue() == cards.get(1).getValue();
    }

    /** returns whether players can buy insurance on the hand. */
    public boolean hasInsuranceOption(DealerHand hand) {
        return hand.getCards().getFirst() instanceof Ace;
    }

    /** returns whether the hand has a bet placed on it. */
    public boolean hasBet() {
        return !pairs.isEmpty();
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    /** returns the list of player-bet pairs associated with the hand (if any). */
    public ArrayList<Map.Entry<Player, Bet>> getPairs() {
        return pairs;
    }

    public Player getActingPlayer() {
        return actingPlayer;
    }

    public void setActingPlayer(Player actingPlayer) {
        this.actingPlayer = actingPlayer;
    }
}
