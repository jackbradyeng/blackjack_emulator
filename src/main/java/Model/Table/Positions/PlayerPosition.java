package Model.Table.Positions;

import java.util.ArrayList;
import java.util.Map;
import Model.Actors.Player;
import Model.Table.Bets.Bet;
import Model.Table.Hands.PlayerHand;

public class PlayerPosition {

    // unique identifier
    private final int positionNumber;

    /* The default player is the player assigned to a particular position. This is similar to the way in which a player
    * might sit at a seat in front of a blackjack table. By default, the player seated in front of a position is the
    * acting player. But if they do not place a bet on that position and some other player does, then they are no
    * longer the acting player in that hand. */
    private Player defaultPlayer;

    /* Because player's can elect to "split" their opening hand, the position class must be capable of handling multiple
    * hands. By default, the game permits two splits only, resulting in a total of 1 * 2 * 2 = 4 hands. Although these
    * hands are only instantiated once the acting player has elected to split. Note that other players with bets on the
    * position do not have agency in the split. Their initial bet is randomly allocated to one of the two hands. */
    private ArrayList<PlayerHand> hands;

    // constructor with player parameter
    public PlayerPosition(int number, Player defaultPlayer) {
        this.positionNumber = number;
        this.defaultPlayer = defaultPlayer;
        this.hands = new ArrayList<>();
    }

    // constructor with no player parameter
    public PlayerPosition(int number) {
        this.positionNumber = number;
        this.hands = new ArrayList<>();
    }

    // returns true if the default player has a live bet on the position, false otherwise
    public boolean isDefaultPlayerInHand() {
        for(PlayerHand hand : hands) {
            for(Map.Entry<Player, Bet> pair : hand.getPairs()) {
                if(pair.getKey().equals(defaultPlayer)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getPositionNumber() {
        return positionNumber;
    }

    public Player getDefaultPlayer() {
        return defaultPlayer;
    }

    public void setDefaultPlayer(Player defaultPlayer) {
        this.defaultPlayer = defaultPlayer;
    }

    public ArrayList<PlayerHand> getHands() {
        return hands;
    }

    public void setHands(ArrayList<PlayerHand> hands) {
        this.hands = hands;
    }

    public void clearHands() {
        hands.clear();
    }
}
