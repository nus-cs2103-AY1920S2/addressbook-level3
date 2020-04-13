//@@author yapdianhao
package seedu.jelphabot.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.jelphabot.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.jelphabot.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.jelphabot.testutil.TypicalReminders.ASSESSMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalReminders.ASSIGNMENT_REMINDER;
import static seedu.jelphabot.testutil.TypicalReminders.BOOK_REPORT_REMINDER;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.UserPrefs;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.testutil.ReminderBuilder;

public class ReminderCommandTest {

    private Model model = new ModelManager(getTypicalJelphaBot(), new UserPrefs());

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ReminderCommand(null, null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Reminder reminder = new ReminderBuilder().withIndex("2").build();
        ReminderCommand reminderCommand = new ReminderCommand(INDEX_THIRD_TASK, reminder);

        String expectedMessage = String.format(ReminderCommand.MESSAGE_SUCCESS, INDEX_THIRD_TASK.getOneBased());

        ModelManager expectedModel = new ModelManager(model.getJelphaBot(), new UserPrefs());
        expectedModel.addReminder(reminder);

        assertCommandSuccess(reminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        int outOfBoundIndex = model.getFilteredTaskList().size() + 1;
        Reminder reminder = new ReminderBuilder().withIndex("" + outOfBoundIndex).build();
        ReminderCommand reminderCommand = new ReminderCommand(Index.fromOneBased(outOfBoundIndex), reminder);

        assertCommandFailure(reminderCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }


    @Test
    public void execute_taskAlreadyCompletedUnfilteredList_failure() {
        //Task task = model.getGroupedTaskList(GroupedTaskList.Category.MODULE).get(INDEX_FIRST_TASK.getZeroBased());
        Reminder reminder = new ReminderBuilder().withIndex("" + INDEX_SECOND_TASK.getZeroBased()).build();
        ReminderCommand reminderCommand = new ReminderCommand(INDEX_SECOND_TASK, reminder);
        Model expectedModel = new ModelManager(model.getJelphaBot(), new UserPrefs());
        //expectedModel.getGroupedTaskList(GroupedTaskList.Category.MODULE);
        //expectedModel.addReminder(reminder);
        assertCommandFailure(reminderCommand, expectedModel, ReminderCommand.MESSAGE_TASK_ALREADY_MARKED_COMPLETE);
    }

    @Test
    public void execute_taskAlreadyCompletedFilteredList_failure() {
        Reminder reminder = new ReminderBuilder().withIndex("" + INDEX_SECOND_TASK.getZeroBased()).build();
        ReminderCommand reminderCommand = new ReminderCommand(INDEX_SECOND_TASK, reminder);
        Model expectedModel = new ModelManager(model.getJelphaBot(), new UserPrefs());
        assertCommandFailure(reminderCommand, expectedModel, ReminderCommand.MESSAGE_TASK_ALREADY_MARKED_COMPLETE);
    }

    @Test
    public void execute_taskDuplicateUnfilteredList_failure() {
        ReminderCommand reminderCommand = new ReminderCommand(INDEX_FIRST_TASK, ASSESSMENT_REMINDER);
        Model expectedModel = new ModelManager(model.getJelphaBot(), new UserPrefs());
        assertCommandFailure(reminderCommand, expectedModel, ReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_addReminderSuccess() {
        ReminderCommand reminderCommand = new ReminderCommand(INDEX_THIRD_TASK, ASSIGNMENT_REMINDER);
        Model expectedModel = new ModelManager(model.getJelphaBot(), new UserPrefs());
        expectedModel.addReminder(ASSIGNMENT_REMINDER);
        assertCommandSuccess(reminderCommand, model,
            String.format(ReminderCommand.MESSAGE_SUCCESS, INDEX_THIRD_TASK.getOneBased()), expectedModel);
    }

    @Test
    public void equals() {

        ReminderCommand firstReminderCommand = new ReminderCommand(INDEX_FIRST_TASK, ASSIGNMENT_REMINDER);
        ReminderCommand secReminderCommand = new ReminderCommand(INDEX_SECOND_TASK, BOOK_REPORT_REMINDER);
        assertTrue(firstReminderCommand.equals(firstReminderCommand));

        ReminderCommand firstReminderCommandCopy = new ReminderCommand(INDEX_FIRST_TASK, ASSIGNMENT_REMINDER);
        assertTrue(firstReminderCommand.equals(firstReminderCommandCopy));

        assertFalse(firstReminderCommand.equals(1));

        assertFalse(firstReminderCommand.equals("1"));

        assertFalse(firstReminderCommand.equals('1'));

        assertFalse(firstReminderCommandCopy.equals(true));

        assertFalse(firstReminderCommandCopy.equals(null));

        assertFalse(firstReminderCommand.equals(secReminderCommand));

    }
}
