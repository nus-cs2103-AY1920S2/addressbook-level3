//@@author yapdianhao
package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_REMIND_DAY;
import static seedu.jelphabot.logic.parser.CliSyntax.PREFIX_REMIND_HOUR;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.reminder.Reminder;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * Displays to the user a list of tasks that will due in a week.
 */
public class ReminderCommand extends Command {

    public static final String MESSAGE_DUPLICATE_REMINDER = "This task already has a reminder!"
                                                                + " Please delete the current reminder with delrem "
                                                                + "[INDEX] first!";
    public static final String MESSAGE_TASK_ALREADY_MARKED_COMPLETE = "The specified task has already "
                                                                          + "been marked as complete!";
    public static final String MESSAGE_SUCCESS = "Added reminder for task %d!";
    public static final String COMMAND_WORD = "reminder";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a reminder on the given task "
                                                   + " on the specified date. "
                                                   + "Parameters:\n" + "INDEX (must be a positive integer) "
                                                   + "    " + "[" + PREFIX_REMIND_DAY
                                                   + "DAYS TO REMIND (between 0 and 7)]\n"
                                                   + "    " + "[" + PREFIX_REMIND_HOUR
                                                   + "HOURS TO REMIND (between 0 and 24)]\n";
    public static final String MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT = "Switched to reminder panel.";

    private final Reminder reminder;
    private final Index index;

    public ReminderCommand(Index index, Reminder reminder) {
        requireAllNonNull(index, reminder);
        this.index = index;
        this.reminder = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ViewTaskList lastShownList = model.getLastShownList();
        if (model.hasReminder(reminder)) {
            //model.setReminder(reminder, reminder);
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }
        Task taskMarkedWithReminder = lastShownList.get(index.getZeroBased());
        String taskMarkedWithReminderDoneTimeString = taskMarkedWithReminder.getDoneTime().toString();
        Task taskToCompare = DoneCommand.createDoneTask(taskMarkedWithReminder, taskMarkedWithReminderDoneTimeString);
        if (taskMarkedWithReminder.equals(taskToCompare)) {
            throw new CommandException(MESSAGE_TASK_ALREADY_MARKED_COMPLETE);
        }
        model.addReminder(reminder);
        return new CommandResult(String.format(MESSAGE_SUCCESS, reminder.getIndex().getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof ReminderCommand // instanceof handles nulls
                           && index.equals(((ReminderCommand) other).index)); // state check
    }
}
