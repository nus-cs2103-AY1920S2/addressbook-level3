//@@author Clouddoggo

package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.jelphabot.commons.core.Messages;
import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.productivity.Productivity;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * Starts a timer for a task.
 */
public class StopTimerCommand extends Command {
    public static final String COMMAND_WORD = "stop";
    public static final String MESSAGE_USAGE = COMMAND_WORD
                                                   + ": Stops the timer for the task specified by the index number.\n"
                                                   + "Parameters: INDEX (must be a positive integer)\n" + "Example: "
                                                   + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = "Stopped timer for task %d. %s %s.\n"
                                                     + "Total time spent on this task: %s.";
    public static final String MESSAGE_NO_TIMER_TO_STOP = "No timers were started.";

    private Index targetIndex;

    public StopTimerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ViewTaskList lastShownList = model.getLastShownList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToStop = lastShownList.get(targetIndex.getZeroBased());
        Task dummy = taskToStop;

        if (!taskToStop.isBeingTimed()) {
            throw new CommandException(MESSAGE_NO_TIMER_TO_STOP);
        }

        taskToStop.stopTimer();
        model.setTask(dummy, taskToStop);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.setProductivity(new Productivity(model.getFilteredTaskList(), false, false, true));
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased(),
            taskToStop.getModuleCode().toString(), taskToStop.getDescription().toString(),
            taskToStop.getTimeSpent().toString()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StopTimerCommand)) {
            return false;
        }

        // state check
        StopTimerCommand d = (StopTimerCommand) other;
        return targetIndex.equals(d.targetIndex);
    }
}
