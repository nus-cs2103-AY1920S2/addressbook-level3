package seedu.address.logic.commands.taskcommand.deletecommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASK;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calender.Task;
import seedu.address.model.nusmodule.ModuleCode;



/**
 * Deletes a task of certain module identified using it's displayed index from the module book.
 */
public class DeleteModuleTaskCommand extends DeleteTaskCommand {

    public static final String MESSAGE_SUCCESS = "Delete task as required successfully\nList of tasks for %s\n%s";

    private final Index targetIndex;
    private final ModuleCode targetModule;

    public DeleteModuleTaskCommand(ModuleCode moduleCode, Index targetIndex) {
        requireNonNull(targetIndex);
        requireNonNull(moduleCode);
        this.targetIndex = targetIndex;
        this.targetModule = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_MODULE);
        }

        if (targetIndex.getZeroBased() >= model.getSizeOfModuleTaskList(targetModule)) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_TASK_INDEX);
        }

        List<Task> lastShownList = model.getDeadlineTaskList();
        Task removed = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTask(removed);
        model.deleteModuleTask(targetModule, targetIndex);
        model.sortTaskList();
        model.updateDeadlineTaskList(PREDICATE_SHOW_ALL_TASK);

        String tasksInfo = model.getModuleTaskInfo(targetModule);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetModule, tasksInfo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleTaskCommand // instanceof handles nulls
                && targetModule.equals(((DeleteModuleTaskCommand) other).targetModule)
                && targetIndex.equals(((DeleteModuleTaskCommand) other).targetIndex));
    }
}

