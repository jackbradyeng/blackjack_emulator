package Tests;

import static Model.Constants.DEFAULT_NUMBER_OF_DECKS;
import static Model.Constants.DEFAULT_NUMBER_OF_PLAYERS;
import Model.Table.Table;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TableTesting {

    // table instance
    Table table;

    public TableTesting() {
        table = new Table(DEFAULT_NUMBER_OF_PLAYERS, DEFAULT_NUMBER_OF_DECKS, true);
    }

    /** Tests that the player array size returns as expected in a single-player game. */
    @Test
    public void testPlayerCount() {assertEquals(1, table.getPlayers().size());}
}
