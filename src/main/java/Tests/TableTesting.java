package Tests;

import Model.Actors.Player;
import Model.Table.Hands.PlayerHand;
import Model.Table.Positions.PlayerPosition;
import Model.Table.Table;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import static Model.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TableTesting {

    // table instance
    Table table;

    public TableTesting() {
        table = new Table(DEFAULT_NUMBER_OF_PLAYERS, DEFAULT_NUMBER_OF_DECKS, false);
    }

    /** tests that the player array size returns as expected in a single-player game. */
    @Order(1)
    @Test
    public void testSinglePlayerCount() {assertEquals(1, table.getPlayers().size());}

    /** tests that the default number of table positions are instantiated and stored in the iterable list. **/
    @Order(2)
    @Test
    public void testPlayerPositions() {assertEquals(DEFAULT_TABLE_POSITIONS,
            table.getPlayerPositionsIterable().size());}

    /** tests that in a single-player game, the player is assigned to the middle position. */
    @Order(3)
    @Test
    public void testSinglePlayerDefaultPosition() {
        int middlePosition = DEFAULT_TABLE_POSITIONS / 2 + 1;
        Player singlePlayer = table.getPlayers().getFirst();
        assertEquals(table.getPlayerPositionsIterable().get(middlePosition).getDefaultPlayer(), singlePlayer);
    }

    /** tests that each position has a single (empty) hand at the beginning of the game. */
    @Order(4)
    @Test
    public void testPositionHandCount() {
        table.startupRoutine();
        for(PlayerPosition position : table.getPlayerPositionsIterable()) {
            assertEquals(1, position.getHands().size());
        }
    }

    /** tests that each player has the default number of starting chips at the beginning of the game. */
    @Order(5)
    @Test
    public void testPlayerStartingChips() {
        for(Player player : table.getPlayers()) {
            assertEquals(DEFAULT_PLAYER_STARTING_CHIPS, player.getChips());
        }
    }

    /** tests that a player's standard bet is correctly processed on a given position. */
    @Order(6)
    @Test
    public void testPlayerStandardBet() {
        Player singlePlayer = table.getPlayers().getFirst();
        table.startupRoutine();
        table.bookStandardBet(singlePlayer, singlePlayer.getDefaultPosition(), 100);

        // a standard bet should be allocated to the first hand at a given position
        PlayerHand hand = singlePlayer.getDefaultPosition().getHands().getFirst();

        // the key in the set of pairs should be the player object while the value should be the corresponding bet
        assertTrue(hand.getPairs().getFirst().getKey().equals(singlePlayer)
                && hand.getPairs().getFirst().getValue().getAmount() == 100);
    }

    /** tests that the table's active hands list contains the correct number and instances of hands. */
    @Order(7)
    @Test
    public void testActiveHandCount() {
        Player singlePlayer = table.getPlayers().getFirst();
        table.startupRoutine();
        table.bookStandardBet(singlePlayer, singlePlayer.getDefaultPosition(), 100);
        table.setActiveHands();

        assertTrue(table.getActiveHands().size() == 1 &&
                table.getActiveHands().getFirst().equals(singlePlayer.getDefaultPosition().getHands().getFirst()));
    }
}
