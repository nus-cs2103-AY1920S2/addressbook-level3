package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.productivity.Productivity;
import seedu.jelphabot.model.summary.Summary;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * Deletes a task identified using it's displayed index from the task list.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
                                                   + ": Deletes the task identified by the index number used in the "
                                                   + "displayed task list.\n"
                                                   + "Parameters: INDEX (must be a positive integer)\n" + "Example: "
                                                   + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_DELETE_RUNNING_TASK = "Deleted Task with running timer: %1$s ";

    public static final String REMINDER_ASSOCIATED_WITH_TASK = DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS;

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ViewTaskList lastShownList = model.getLastShownList();

        if (targetIndex.getZeroBased() >= lastShownList.size() || targetIndex.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToDelete = lastShownList.get(targetIndex.getZeroBased());
        try {
            CommandResult fromDeleteReminderCommand = new DeleteReminderCommand(targetIndex).execute(model);
        } catch (CommandException ce) {
            logger.info(REMINDER_ASSOCIATED_WITH_TASK);
        }
        model.deleteTask(taskToDelete);
        model.updateDeletedReminders(targetIndex);
        model.setProductivity(new Productivity(model.getFilteredTaskList(), true, true, true));
        model.setSummary(new Summary(model.getFilteredTaskList()));

        if (taskToDelete.isBeingTimed()) {
            return new CommandResult(String.format(MESSAGE_DELETE_RUNNING_TASK, taskToDelete));
        } else {
            return new CommandResult(String.format(MESSAGE_DELETE_TASK_SUCCESS, taskToDelete));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                   || (other instanceof DeleteCommand // instanceof handles nulls
                           && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
