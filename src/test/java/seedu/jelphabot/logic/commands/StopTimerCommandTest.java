//@@author Clouddoggo

package seedu.jelphabot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jelphabot.logic.commands.CommandTestUtil.showTaskAtIndex;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and
 * RedoCommand) and unit tests for {@code StopTimerCommand}.
 */
class StopTimerCommandTest {
    // unable to automate test for success as we require a model that has a running timer.

    private Model model = new ModelManager(getTypicalJelphaBot(), new UserPrefs());

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getLastShownList().size() + 1);
        StopTimerCommand stopTimerCommand = new StopTimerCommand(outOfBoundIndex);

        assertCommandFailure(stopTimerCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_TASK);

        Index outOfBoundIndex = INDEX_THIRD_TASK;
        // ensures that outOfBoundIndex is still in bounds of task list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getJelphaBot().getTaskList().size());

        StopTimerCommand stopTimerCommand = new StopTimerCommand(outOfBoundIndex);

        assertCommandFailure(stopTimerCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        StopTimerCommand stopFirstTimerCommand = new StopTimerCommand(INDEX_FIRST_TASK);
        StopTimerCommand stopSecondTimerCommand = new StopTimerCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(stopFirstTimerCommand.equals(stopFirstTimerCommand));

        // same values -> returns true
        StopTimerCommand stopFirstCommandCopy = new StopTimerCommand(INDEX_FIRST_TASK);
        assertTrue(stopFirstTimerCommand.equals(stopFirstCommandCopy));

        // different types -> returns false
        assertFalse(stopFirstTimerCommand.equals(1));

        // null -> returns false
        assertFalse(stopFirstTimerCommand.equals(null));

        // different commands -> returns false
        assertFalse(stopFirstTimerCommand.equals(stopSecondTimerCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
