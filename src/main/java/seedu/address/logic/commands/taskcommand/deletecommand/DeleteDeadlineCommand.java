package seedu.address.logic.commands.taskcommand.deletecommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASK;

import java.util.List;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleTask;


/**
 * Adds a deadline.
 */

public class DeleteDeadlineCommand extends DeleteTaskCommand {

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

        List<Task> lastShownList = model.getDeadlineTaskList();
        Task removed = lastShownList.get(deadlineToDelete.getIndex() - 1);
        model.deleteTask(removed);

        if (removed instanceof ModuleTask) {
            model.getModuleBook().removeModuleTask((ModuleTask) removed);
        }
        model.sortTaskList();
        model.updateDeadlineTaskList(PREDICATE_SHOW_ALL_TASK);

        return new CommandResult(MESSAGE_SUCCESS + removed);
    }

    public Task getDeadlineToDelete() {
        return this.deadlineToDelete;
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteDeadlineCommand // instanceof handles nulls
                && deadlineToDelete.equals(((DeleteDeadlineCommand) other).getDeadlineToDelete()));
    }


}
