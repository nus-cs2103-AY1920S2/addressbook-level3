package seedu.address.logic.commands.reminder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalReminders.getTypicalReminderTAble;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.reminders.DeleteReminderCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.student.StudentTAble;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteReminderCommand}.
 */
public class DeleteReminderCommandTest {

    private Model model = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
            new TutorialTAble(), new ModTAble(), getTypicalReminderTAble());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Reminder reminderToDelete = model.getFilteredReminderList().get(INDEX_FIRST.getZeroBased());
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(new StudentTAble(), new UserPrefs(), new ConsultTAble(),
                new TutorialTAble(), new ModTAble(), model.getReminderTAble());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(deleteReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredReminderList().size() + 1);
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(outOfBoundIndex);

        assertCommandFailure(deleteReminderCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteReminderCommand deleteFirstCommand = new DeleteReminderCommand(INDEX_FIRST);
        DeleteReminderCommand deleteSecondCommand = new DeleteReminderCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteReminderCommand deleteFirstCommandCopy = new DeleteReminderCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different reminder -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
