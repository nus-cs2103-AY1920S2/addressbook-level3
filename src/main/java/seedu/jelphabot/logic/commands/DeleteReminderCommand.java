//@@author yapdianhao
package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * Deletes a reminder according to the task's index.
 */
public class DeleteReminderCommand extends Command {

    public static final String COMMAND_WORD = "delrem";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                                   + ": Deletes the reminder identified by the index number"
                                                   + " of the task used in the displayed task list.\n"
                                                   + "Parameters: INDEX (must be a positive integer)\n"
                                                   + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REMINDER_SUCCESS = "Deleted Reminder for task %1$s";

    public static final String MESSAGE_DELETE_REMINDER_FAILURE = "Reminder not found!";

    private final Index targetIndex;

    public DeleteReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ViewTaskList lastShownList = model.getLastShownList();
        List<Reminder> reminderList = model.getFilteredReminderList();
        if (targetIndex.getZeroBased() < 0 || targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Reminder toDelete = null;
        for (Reminder reminder : reminderList) {
            if (reminder.getIndex().equals(targetIndex)) {
                toDelete = reminder;
            }
        }
        if (toDelete == null) {
            throw new CommandException(MESSAGE_DELETE_REMINDER_FAILURE);
        } else {
            model.deleteReminder(toDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_REMINDER_SUCCESS, this.targetIndex.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof DeleteReminderCommand // instanceof handles nulls
                           && targetIndex.equals(((DeleteReminderCommand) other).targetIndex)); // state check
    }
}
