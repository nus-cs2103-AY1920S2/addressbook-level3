package seedu.address.logic.history;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * This test ensures integration between the storage {@code FileUtil} and model
 * {@code CommandHistoryState}. More stringent unit tests can be found in their
 * respective tests.
 */
public class CommandHistoryTest {

    private static final String EMPTY_STRING = "";
    private static final String VALID_COMMAND_1 = "edit-c 1 t/";
    private static final String VALID_COMMAND_2 = "edit-c 2 n/Nimar";
    private static final String VALID_COMMAND_3 = "help";

    // helper methods test cases
    private static final String STORAGE_STRING = String.format("%s\n%s\n%s", VALID_COMMAND_1, VALID_COMMAND_2,
            VALID_COMMAND_3);
    private static final List<String> STATE_LIST = List.of(VALID_COMMAND_1, VALID_COMMAND_2, VALID_COMMAND_3);

    @TempDir
    public Path testFolder;

    private CommandHistory commandHistory;

    @BeforeEach
    public void setUp() {
        commandHistory = new CommandHistory(getTempFilePath("test.txt"));
        commandHistory.clearHistory(); // sanity check that history is cleared
    }

    public void constructor_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandHistory(null));
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void storageStringToStateList_validStorageString_returnsCorrectList() {
        List<String> convertedList = commandHistory.storageStringToStateList(STORAGE_STRING);
        assertTrue(convertedList.equals(STATE_LIST));
    }

    @Test
    public void stateListToStorageString_validStateList_returnsCorrectString() {
        String convertedString = commandHistory.stateListToStorageString(STATE_LIST);
        assertTrue(convertedString.equals(STORAGE_STRING));
    }

    @Test
    public void clearHistory_returnsCorrectOutputs() {
        commandHistory.clearHistory(); // sanity check that history is cleared
        assertEquals(commandHistory.getNextCommand(), EMPTY_STRING);
        assertEquals(commandHistory.getPreviousCommand(), EMPTY_STRING);
    }

    @Test
    public void getPreviousCommand_oneItemHistory_returnsSameString() {
        commandHistory.addToHistory(VALID_COMMAND_1);
        String command1 = commandHistory.getPreviousCommand();
        String command2 = commandHistory.getPreviousCommand();
        String command3 = commandHistory.getPreviousCommand();
        String command4 = commandHistory.getPreviousCommand();
        String command5 = commandHistory.getPreviousCommand();
        assertTrue(command1 == VALID_COMMAND_1 && command2 == VALID_COMMAND_1 && command3 == VALID_COMMAND_1
                && command4 == VALID_COMMAND_1 && command5 == VALID_COMMAND_1);
    }

    @Test
    public void simulatedUsage_returnsCorrectOutputs() {
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
