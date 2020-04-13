//@@author eedenong
package seedu.jelphabot.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.jelphabot.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.time.LocalDateTime;
import java.util.Set;

import seedu.jelphabot.commons.core.index.Index;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.productivity.Productivity;
import seedu.jelphabot.model.summary.Summary;
import seedu.jelphabot.model.tag.Tag;
import seedu.jelphabot.model.task.DateTime;
import seedu.jelphabot.model.task.Description;
import seedu.jelphabot.model.task.ModuleCode;
import seedu.jelphabot.model.task.Priority;
import seedu.jelphabot.model.task.Status;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.model.task.TimeSpent;
import seedu.jelphabot.model.task.tasklist.ViewTaskList;

/**
 * Marks the specified as done by updating it's status to COMPLETE.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Marks the task specified by the index number as completed.\n"
        + "Parameters: INDEX (must be a positive integer)\n" + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_TASK_COMPLETE_SUCCESS = "Marked task as completed: %1$s";
    public static final String MESSAGE_TASK_ALREADY_MARKED_COMPLETE = "The specified task has already "
                                                                          + "been marked as complete!";
    public static final String MESSAGE_STOP_TASK_FIRST = "Please stop timer for this task before marking it as done!";

    private final Index index;

    /**
     * @param index of the task in the filtered task list to mark done
     */
    public DoneCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ViewTaskList lastShownList = model.getLastShownList();

        if (index.getZeroBased() >= lastShownList.size() || index.getZeroBased() < 0) {
            throw new CommandException(MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToMarkDone = lastShownList.get(index.getZeroBased());

        if (taskToMarkDone.isBeingTimed()) {
            throw new CommandException(MESSAGE_STOP_TASK_FIRST);
        }

        Task doneTask = createDoneTask(taskToMarkDone);

        boolean isSameTask = taskToMarkDone.equals(doneTask);

        if (isSameTask) {
            throw new CommandException(MESSAGE_TASK_ALREADY_MARKED_COMPLETE);
        }

        model.setTask(taskToMarkDone, doneTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.setProductivity(new Productivity(model.getFilteredTaskList(), true, false, false));
        model.setSummary(new Summary(model.getFilteredTaskList()));
        return new CommandResult(String.format(MESSAGE_MARK_TASK_COMPLETE_SUCCESS, doneTask));
    }

    /**
     * Creates and returns a {@code Task} with the updated COMPLETE status, and updated DoneTime.
     * DoneTime is set according to the time that the task was marked done.
     * Creation of the {@code Task} is done by copying all the details of the given {@code Task}
     * and creating a new {@code Task} object.
     * @param task Task object to get the relevant details from.
     * @return Task object with it's status set as COMPLETE.
     */
    public static Task createDoneTask(Task task) {
        Description description = task.getDescription();
        ModuleCode moduleCode = task.getModuleCode();
        Set<Tag> tags = task.getTags();
        DateTime dateTime = task.getDateTime();
        Status status = Status.COMPLETE;
        String doneTimeString = LocalDateTime.now().format(DateTime.STANDARD_FORMATTER);
        LocalDateTime doneTime = LocalDateTime.parse(doneTimeString, DateTime.STANDARD_FORMATTER);
        Priority priority = task.getPriority();
        TimeSpent timeSpent = task.getTimeSpent();

        return new Task(description, status, dateTime, doneTime, moduleCode, priority, tags, timeSpent);
    }

    /**
     * Creates and returns a {@code Task} with the updated COMPLETE status, and updated DoneTime.
     * DoneTime is set according to the String representing the time the task was completed.
     * Creation of the {@code Task} is done by copying all the details of the given {@code Task}
     * and creating a new {@code Task} object.
     * @param task Task object to get the relevant details from.
     * @param doneTimeString String representing the time that the task was marked done.
     * @return Task object with it's status set as COMPLETE and it's doneTime set as the doneTimeString.
     */
    protected static Task createDoneTask(Task task, String doneTimeString) {
        Description description = task.getDescription();
        ModuleCode moduleCode = task.getModuleCode();
        Set<Tag> tags = task.getTags();
        DateTime dateTime = task.getDateTime();
        Status status = Status.COMPLETE;
        LocalDateTime doneTime = LocalDateTime.parse(doneTimeString);
        Priority priority = task.getPriority();
        TimeSpent timeSpent = task.getTimeSpent();

        return new Task(description, status, dateTime, doneTime, moduleCode, priority, tags, timeSpent);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DoneCommand)) {
            return false;
        }

        // state check
        DoneCommand d = (DoneCommand) other;
        return index.equals(d.index);
    }
}
