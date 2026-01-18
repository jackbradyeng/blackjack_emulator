package Model.Actors.Strategies;

import java.util.Map;
import java.util.HashMap;
import Model.Cards.Card;
import Model.Table.Hands.DealerHand;
import Model.Table.Hands.PlayerHand;
import static Model.Constants.*;

public class PlayerStrategy {

    // player hard value -> dealer hard value map
    private HashMap<Map.Entry<Integer, Integer>, String> hardValuesActionTable;
    private HashMap<Map.Entry<Map.Entry<Integer, Integer>, Integer>, String> splitActionTable;

    public PlayerStrategy() {
        this.hardValuesActionTable = new HashMap<>();
        this.splitActionTable = new HashMap<>();
        populateActionTables();
    }

    private void populateActionTables() {
        populateHardValueTable();
        populateSplittingTable();
    }

    /** executes the player's strategy for a given hand and dealer face-up card. */
    public String executeStrategy(PlayerHand playerHand, DealerHand dealerHand) {
        return hardValuesAndSplitting(playerHand, dealerHand);
    }

    /** incorporates the hard values strategy as well as optimal splitting cases. */
    private String hardValuesAndSplitting(PlayerHand playerHand, DealerHand dealerHand) {
        // if the two player cards are equal in value, first test to see if the action is a split
        if (playerHand.hasSplitOption()) {
            String action = executeSplittingStrategy(playerHand, dealerHand);
            if(action.equals(NO_SPLIT)) {
                return executeHardValuesStrategy(playerHand, dealerHand);
            } else {
                return action;
            }
        } else {
            return executeHardValuesStrategy(playerHand, dealerHand);
        }
    }

    /** returns the player action based on the hard-values strategy table. */
    private String executeHardValuesStrategy(PlayerHand playerHand, DealerHand dealerHand) {
        int playerHV = playerHand.getHandValue();
        int dealerHV = dealerHand.getCards().getFirst().getValue();
        Map.Entry<Integer, Integer> entry = Map.entry(playerHV, dealerHV);
        return hardValuesActionTable.get(entry);
    }

    /** returns the player action based on the splitting strategy table. */
    private String executeSplittingStrategy(PlayerHand playerHand, DealerHand dealerHand) {
        Card first = playerHand.getCards().get(0);
        Card second = playerHand.getCards().get(1);
        Card dealerFirst = dealerHand.getCards().getFirst();
        return splitActionTable.get(Map.entry(Map.entry(first.getValue(), second.getValue()), dealerFirst.getValue()));
    }

    private void executeSoftValuesStrategy(PlayerHand playerHand, DealerHand dealerHand) {

    }

    private void executeInsuranceStrategy(PlayerHand playerHand, DealerHand dealerHand) {

    }

    /** determines player behaviour based on hard-value comparisons (i.e. neglecting the flexibility of the Ace). */
    private void populateHardValueTable() {
        for (int playerHV = 2; playerHV <= BLACKJACK_CONSTANT; playerHV++) {
            for (int dealerHV = 2; dealerHV <= ACE_UPPER_VALUE; dealerHV++) {
                Map.Entry<Integer, Integer> entry = Map.entry(playerHV, dealerHV);
                // always hit if the player's hand value is less than 9
                if (playerHV < 9) {
                    hardValuesActionTable.put(entry, HIT);
                }
                else if(playerHV == 9) {
                    if(dealerHV == 3 || dealerHV == 4 || dealerHV == 5 || dealerHV == 6) {
                        hardValuesActionTable.put(entry, DOUBLE);
                    }
                    else {
                        hardValuesActionTable.put(entry, HIT);
                    }
                }
                else if(playerHV == 10) {
                    if(dealerHV < 10) {
                        hardValuesActionTable.put(entry, DOUBLE);
                    }
                    else {
                        hardValuesActionTable.put(entry, HIT);
                    }
                }
                else if(playerHV == 11) {
                    hardValuesActionTable.put(entry, DOUBLE);
                }
                else if(playerHV == 12) {
                    if(dealerHV == 4 || dealerHV == 5 || dealerHV == 6) {
                        hardValuesActionTable.put(entry, STAND);
                    }
                    else {
                        hardValuesActionTable.put(entry, HIT);
                    }
                }
                else if(playerHV < 17) {
                    if(dealerHV < 7) {
                        hardValuesActionTable.put(entry, STAND);;
                    }
                    else {
                        hardValuesActionTable.put(entry, HIT);
                    }
                }
                else {
                    hardValuesActionTable.put(entry, STAND);
                }
            }
        }
    }

    private void populateSoftValueTable() {

    }

    /** for pairs where the first card's value equals the second, determines whether the player should split or not
     * based on the value of the dealer's face-up card. Note: This method does specific the action to be taken if the
     * decision is not to split. This must be handled by another method. */
    private void populateSplittingTable() {
        for (int first = 2; first <= ACE_UPPER_VALUE; first++) {
            for (int dealerValue = 2; dealerValue <= ACE_UPPER_VALUE; dealerValue++) {
                int second = first;
                Map.Entry<Map.Entry<Integer, Integer>, Integer> entry = Map.entry(Map.entry(first, second), dealerValue);

                if (first == 2 & second == 2 || first == 3 & second == 3) {
                    if (dealerValue <= 7) {
                        splitActionTable.put(entry, SPLIT);
                    } else {
                        splitActionTable.put(entry, NO_SPLIT);
                    }
                }
                // never split these hands
                else if (first == 4 & second == 4 || first == 5 & second == 5 || first == 10 & second == 10) {
                    splitActionTable.put(entry, NO_SPLIT);
                } else if (first == 6 & second == 6) {
                    if (dealerValue <= 6) {
                        splitActionTable.put(entry, SPLIT);
                    } else {
                        splitActionTable.put(entry, NO_SPLIT);
                    }
                } else if (first == 7 & second == 7) {
                    if (dealerValue <= 7) {
                        splitActionTable.put(entry, SPLIT);
                    } else {
                        splitActionTable.put(entry, NO_SPLIT);
                    }
                }
                // always split these hands
                else if (first == 8 & second == 8 || first == ACE_UPPER_VALUE & second == ACE_UPPER_VALUE) {
                    splitActionTable.put(entry, SPLIT);
                } else {
                    if (dealerValue <= 9) {
                        splitActionTable.put(entry, SPLIT);
                    } else {
                        splitActionTable.put(entry, NO_SPLIT);
                    }
                }
            }
        }
    }

    /** a primitive player strategy which mirrors the same behaviour as the dealer. */
    private String playerStandOnX(PlayerHand hand) {
        if(hand.getHandValue() < DEFAULT_PLAYER_DRAW_VALUE) {
            return HIT;
        } else {
            return STAND;
        }
    }
}
