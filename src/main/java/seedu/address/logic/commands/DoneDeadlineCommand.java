package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.todolist.Task;

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

        Task.getDeadlineTaskList().get(deadlineDone.getIndex() - 1).markAsDone();

        Task done = Task.getDeadlineTaskList().get(deadlineDone.getIndex() - 1);

        return new CommandResult(MESSAGE_SUCCESS + done);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneDeadlineCommand // instanceof handles nulls
                && deadlineDone == deadlineDone);
    }
}
