package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASK;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.calender.Task;

/**
 * Adds a deadline.
 */

public class DeleteDeadlineCommand extends DeleteTaskCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete deadline. Format of input should be:"
            + " taskDelete index/{num} "
            + "Example: taskDelete index/2";

    public static final String MESSAGE_SUCCESS = "Deadline Deleted: ";
    public static final String MESSAGE_FAIL = "No such deadline exists";




    private final Task deadlineToDelete;

    public DeleteDeadlineCommand(Task deadlineToDelete) {
        requireNonNull(deadlineToDelete);
        this.deadlineToDelete = deadlineToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (Task.getDeadlineTaskList().size() < deadlineToDelete.getIndex()) {
            return new CommandResult(MESSAGE_FAIL);
        }

        Task removed = Task.getDeadlineTaskList().remove(deadlineToDelete.getIndex() - 1);
        Task.removeTaskPerDate(removed.getDate(), removed);
        System.out.println(Task.getDeadlineTaskHashMap());

        model.updateDeadlineTaskList(PREDICATE_SHOW_ALL_TASK);
        return new CommandResult(MESSAGE_SUCCESS + removed);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDeadlineCommand // instanceof handles nulls
                && deadlineToDelete == deadlineToDelete);
    }


}
