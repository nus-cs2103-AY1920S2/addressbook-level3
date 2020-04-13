//@@author yapdianhao
package seedu.jelphabot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.UserPrefs;
import seedu.jelphabot.model.reminder.Reminder;

public class DeleteReminderCommandTest {

    private Model model = new ModelManager(getTypicalJelphaBot(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredReminder_success() {
        Reminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST_TASK.getZeroBased());
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_TASK);
        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS,
                INDEX_FIRST_TASK.getOneBased());
        ModelManager expectedModel = new ModelManager(model.getJelphaBot(), new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);
        assertCommandSuccess(deleteReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredReminder_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(outOfBoundIndex);

        assertCommandFailure(deleteReminderCommand, model, DeleteReminderCommand.MESSAGE_DELETE_REMINDER_FAILURE);
    }

    @Test
    public void equals() {
        DeleteReminderCommand firstDeleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST_TASK);
        DeleteReminderCommand secDeleteReminderCommand = new DeleteReminderCommand(INDEX_SECOND_TASK);
        DeleteReminderCommand firstDeleteReminderCommandCopy = new DeleteReminderCommand(INDEX_FIRST_TASK);

        assertTrue(firstDeleteReminderCommand.equals(firstDeleteReminderCommand));

        assertTrue(firstDeleteReminderCommand.equals(firstDeleteReminderCommandCopy));

        assertFalse(firstDeleteReminderCommand.equals(1));

        assertFalse(firstDeleteReminderCommand.equals(null));

        assertFalse(firstDeleteReminderCommand.equals("1"));

        assertFalse(firstDeleteReminderCommand.equals(true));

        assertFalse(firstDeleteReminderCommand.equals(secDeleteReminderCommand));
    }
}
