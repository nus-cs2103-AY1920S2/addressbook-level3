package seedu.address.logic.commands.taskcommand.donecommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calender.Task;

/**
 * Mark a deadline in calendar as done.
 */
public class DoneDeadlineCommand extends DoneCommand {

    public static final String MESSAGE_SUCCESS = "Deadline done: ";
    public static final String MESSAGE_FAIL = "No such deadline exists";

    private final Task deadlineDone;

    public DoneDeadlineCommand(Task deadlineDone) {
        requireNonNull(deadlineDone);
        this.deadlineDone = deadlineDone;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (Task.getDeadlineTaskList().size() < deadlineDone.getIndex()) {
            return new CommandResult(MESSAGE_FAIL);
        }

        List<Task> lastShownList = model.getDeadlineTaskList();
        Task completed = lastShownList.get(deadlineDone.getIndex() - 1);
        completed.markAsDone();

        model.sortTaskList();
        model.updateDeadlineTaskList(Model.PREDICATE_SHOW_ALL_TASK);

        return new CommandResult(MESSAGE_SUCCESS + completed);
    }

    public Task getDeadlineDone() {
        return deadlineDone;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneDeadlineCommand // instanceof handles nulls
                && deadlineDone.equals(((DoneDeadlineCommand) other).getDeadlineDone()));
    }
}
