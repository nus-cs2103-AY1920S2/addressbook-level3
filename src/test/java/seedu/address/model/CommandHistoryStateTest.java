package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class CommandHistoryStateTest {

    private static final ArrayList<String> INITIAL_EMPTY_STATE = new ArrayList<>();
    private static final String EMPTY_STRING = "";
    private static final String VALID_COMMAND_1 = "edit-c 1 t/";
    private static final String VALID_COMMAND_2 = "edit-c 2 n/Nimar";
    private static final int LIST_SIZE_EMPTY = 0;
    private static final int LIST_SIZE_ONE = 1;
    private static final int LIST_SIZE_FIVE = 5;

    private final CommandHistoryState historyState = new CommandHistoryState(INITIAL_EMPTY_STATE);

    @Test
    public void constructor() {
        // no matter what, history must be initialised
        assertTrue(historyState.getCurrentState() != null);
    }

    @Test
    public void clearState() {
        historyState.clearState();
        assertEquals(Collections.emptyList(), historyState.getCurrentState());
    }

    @Test
    public void add_emptyString_nothingAdded() {
        historyState.clearState(); // sanity check that history is empty
        historyState.add(EMPTY_STRING);
        assertTrue(historyState.getCurrentState().size() == LIST_SIZE_EMPTY);
    }

    @Test
    public void add_similarValidCommandMultipleTimes_onlyOneAdded() {
        historyState.clearState(); // sanity check that history is empty
        historyState.add(VALID_COMMAND_1);
        historyState.add(VALID_COMMAND_1);
        historyState.add(VALID_COMMAND_1);
        historyState.add(VALID_COMMAND_1);
        assertTrue(historyState.getCurrentState().size() == LIST_SIZE_ONE);
    }

    @Test
    public void add_validCommandMultipleTimes_allAdded() {
        historyState.clearState(); // sanity check that history is empty
        historyState.add(VALID_COMMAND_1);
        historyState.add(VALID_COMMAND_2);
        historyState.add(VALID_COMMAND_1);
        historyState.add(VALID_COMMAND_2);
        historyState.add(VALID_COMMAND_1);
        assertTrue(historyState.getCurrentState().size() == LIST_SIZE_FIVE);
    }

    @Test
    public void getNextCommand_emptyHistory_emptyStringReturned() {
        historyState.clearState(); // sanity check that history is empty
        assertTrue(historyState.getNextCommand() == EMPTY_STRING);
    }

    @Test
    public void getPreviousCommand_emptyHistory_emptyStringReturned() {
        historyState.clearState(); // sanity check that history is empty
        assertTrue(historyState.getPreviousCommand() == EMPTY_STRING);
    }

    @Test
    public void getNextCommand_nonEmptyHistory_emptyStringReturned() {
        historyState.clearState(); // sanity check that history is empty
        historyState.add(VALID_COMMAND_1);
        historyState.add(VALID_COMMAND_2);
        assertTrue(historyState.getNextCommand() == EMPTY_STRING);
    }

    @Test
    public void getPreviousCommand_oneItemHistory_sameStringReturned() {
        historyState.clearState(); // sanity check that history is empty
        historyState.add(VALID_COMMAND_1);
        String command1 = historyState.getPreviousCommand();
        String command2 = historyState.getPreviousCommand();
        String command3 = historyState.getPreviousCommand();
        String command4 = historyState.getPreviousCommand();
        String command5 = historyState.getPreviousCommand();
        assertTrue(command1 == VALID_COMMAND_1
                && command2 == VALID_COMMAND_1
                && command3 == VALID_COMMAND_1
                && command4 == VALID_COMMAND_1
                && command5 == VALID_COMMAND_1);
    }

    @Test
    public void getPreviousCommand_twoItemsHistory_correctStringsReturned() {
        historyState.clearState(); // sanity check that history is empty
        historyState.add(VALID_COMMAND_1);
        historyState.add(VALID_COMMAND_2);
        String shouldBeValidCommand2 = historyState.getPreviousCommand();
        String shouldBeValidCommand1 = historyState.getPreviousCommand();
        assertTrue(shouldBeValidCommand2 == VALID_COMMAND_2 && shouldBeValidCommand1 == VALID_COMMAND_1);
    }

    @Test
    public void getNextCommand_twoItemsHistoryAfterGetPrevCommand_correctStringReturned() {
        historyState.clearState(); // sanity check that history is empty
        historyState.add(VALID_COMMAND_1);
        historyState.add(VALID_COMMAND_2);
        historyState.getPreviousCommand(); // returns VALID_COMMAND_2
        historyState.getPreviousCommand(); // returns VALID_COMMAND_1
        String shouldBeValidCommand2 = historyState.getNextCommand();
        assertTrue(shouldBeValidCommand2 == VALID_COMMAND_2);
    }
}
