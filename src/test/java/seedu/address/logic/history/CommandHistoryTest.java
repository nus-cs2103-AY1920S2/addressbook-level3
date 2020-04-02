package seedu.address.logic.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private static final String EMPTY_STRING = "";
    private static final String VALID_COMMAND_1 = "edit-c 1 t/";
    private static final String VALID_COMMAND_2 = "edit-c 2 n/Nimar";

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void clearHistory_correctOutput() {
        commandHistory.clearHistory();
        assertEquals(commandHistory.getNextCommand(), EMPTY_STRING);
        assertEquals(commandHistory.getPreviousCommand(), EMPTY_STRING);
    }

    @Test
    public void getPreviousCommand_oneItemHistory_sameStringReturned() {
        commandHistory.clearHistory();
        commandHistory.addToHistory(VALID_COMMAND_1);
        String command1 = commandHistory.getPreviousCommand();
        String command2 = commandHistory.getPreviousCommand();
        String command3 = commandHistory.getPreviousCommand();
        String command4 = commandHistory.getPreviousCommand();
        String command5 = commandHistory.getPreviousCommand();
        assertTrue(command1 == VALID_COMMAND_1
                && command2 == VALID_COMMAND_1
                && command3 == VALID_COMMAND_1
                && command4 == VALID_COMMAND_1
                && command5 == VALID_COMMAND_1);
    }

    @Test
    public void simulatedUsage() {
        commandHistory.clearHistory();
        commandHistory.addToHistory(VALID_COMMAND_1);
        commandHistory.addToHistory(VALID_COMMAND_2);

        String shouldBeValidCommand2 = commandHistory.getPreviousCommand(); // returns VALID_COMMAND_2
        assertTrue(shouldBeValidCommand2 == VALID_COMMAND_2);

        String shouldBeValidCommand1 = commandHistory.getPreviousCommand(); // returns VALID_COMMAND_1
        assertTrue(shouldBeValidCommand1 == VALID_COMMAND_1);

        shouldBeValidCommand1 = commandHistory.getPreviousCommand(); // returns VALID_COMMAND_1
        assertTrue(shouldBeValidCommand1 == VALID_COMMAND_1);

        shouldBeValidCommand2 = commandHistory.getNextCommand();
        assertTrue(shouldBeValidCommand2 == VALID_COMMAND_2);

        String shouldBeEmptyString = commandHistory.getNextCommand();
        assertTrue(shouldBeEmptyString == EMPTY_STRING);
    }
}
