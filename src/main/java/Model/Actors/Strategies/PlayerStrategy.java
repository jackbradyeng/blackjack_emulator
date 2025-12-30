package Model.Actors.Strategies;

import Model.Table.Hands.DealerHand;
import Model.Table.Hands.PlayerHand;
import java.util.Map;
import java.util.HashMap;

import static Model.Constants.*;

public class PlayerStrategy {

    // player hard value -> dealer hard value map
    private HashMap<Map.Entry<Integer, Integer>, String> actionTable;

    public PlayerStrategy() {
        this.actionTable = new HashMap<>();
        populateActionTable();
    }

    private void populateActionTable() {
        hardValueStrategy();
    }

    public String executeStrategy(PlayerHand playerHand, DealerHand dealerHand) {
        return executeHardValuesStrategy(playerHand, dealerHand);
        // return playerStandOnX(playerHand);
    }

    public String executeHardValuesStrategy(PlayerHand playerHand, DealerHand dealerHand) {
        int playerHV = playerHand.getHandValue();
        int dealerHV = dealerHand.getCards().getFirst().getValue();
        Map.Entry<Integer, Integer> entry = Map.entry(playerHV, dealerHV);
        return actionTable.get(entry);
    }

    /** determines player behaviour based on hard-value comparisons (ie. neglecting the flexibility of the Ace). */
    private void hardValueStrategy() {
        for (int playerHV = 2; playerHV <= BLACKJACK_CONSTANT; playerHV++) {
            for (int dealerHV = 2; dealerHV <= ACE_UPPER_VALUE; dealerHV++) {

                Map.Entry<Integer, Integer> entry = Map.entry(playerHV, dealerHV);
                // always hit if the player's hand value is less than 9
                if (playerHV < 9)
                    actionTable.put(entry, HIT);
                else if(playerHV == 9) {
                    if(dealerHV == 3 || dealerHV == 4 || dealerHV == 5 || dealerHV == 6)
                        actionTable.put(entry, DOUBLE);
                    else
                        actionTable.put(entry, HIT);
                }
                else if(playerHV == 10) {
                    if(dealerHV < 10)
                        actionTable.put(entry, DOUBLE);
                    else
                        actionTable.put(entry, HIT);
                }
                else if(playerHV == 11) {
                    actionTable.put(entry, DOUBLE);
                }
                else if(playerHV == 12) {
                    if(dealerHV == 4 || dealerHV == 5 || dealerHV == 6)
                        actionTable.put(entry, STAND);
                    else
                        actionTable.put(entry, HIT);
                }
                else if(playerHV < 17) {
                    if(dealerHV < 7)
                        actionTable.put(entry, STAND);
                    else
                        actionTable.put(entry, HIT);
                }
                else
                    actionTable.put(entry, STAND);
            }
        }
    }

    /** executes the player's strategy. */
    private String playerStandOnX(PlayerHand hand) {
        if(hand.getHandValue() < DEFAULT_PLAYER_DRAW_VALUE) {
            return HIT;
        } else {
            return STAND;
        }
    }
}
