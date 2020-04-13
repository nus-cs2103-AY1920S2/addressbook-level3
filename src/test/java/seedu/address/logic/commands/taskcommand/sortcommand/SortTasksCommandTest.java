package seedu.address.logic.commands.taskcommand.sortcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;

class SortTasksCommandTest {

    @Test
    public void equals() {
        final SortTasksCommand standardCommand = new SortTasksCommand("priority");

        // same values -> returns true
        SortTasksCommand commandWithSameValues = new SortTasksCommand("priority");
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different sorting param -> returns false
        assertFalse(standardCommand.equals(new SortTasksCommand("date")));
    }
}
