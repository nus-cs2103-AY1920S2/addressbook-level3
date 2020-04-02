package csdev.couponstash.model.history;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommandTextHistoryTest {
    @Test
    void getUp() {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        commandTextHistory.add("first");
        commandTextHistory.add("second");
        commandTextHistory.add("third");

        commandTextHistory.getDown();
        commandTextHistory.getDown();

        assertEquals(commandTextHistory.getUp(), "third");
        assertEquals(commandTextHistory.getUp(), "second");
        assertEquals(commandTextHistory.getUp(), "first");
        assertEquals(commandTextHistory.getUp(), "first");
        assertEquals(commandTextHistory.getUp(), "first");
    }

    @Test
    void getDown() {
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        commandTextHistory.add("first");
        commandTextHistory.add("second");
        commandTextHistory.add("third");

        commandTextHistory.getDown();
        commandTextHistory.getDown();

        assertEquals(commandTextHistory.getUp(), "third");
        assertEquals(commandTextHistory.getUp(), "second");
        assertEquals(commandTextHistory.getUp(), "first");
        assertEquals(commandTextHistory.getUp(), "first");
        assertEquals(commandTextHistory.getDown(), "second");
        assertEquals(commandTextHistory.getDown(), "third");
        assertEquals(commandTextHistory.getDown(), "");
        assertEquals(commandTextHistory.getDown(), "");
    }
}
