package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.nusmodule.ModuleCode;

/**
 * Mark a task of certain module identified using it's displayed index from the module book as done.
 */
public class DoneModuleTaskCommand extends DoneCommand {

    public static final String MESSAGE_SUCCESS = "Task done: ";

    private final Index targetIndex;
    private final ModuleCode targetModule;

    public DoneModuleTaskCommand(ModuleCode moduleCode, Index targetIndex) {
        requireNonNull(targetIndex);
        requireNonNull(moduleCode);
        this.targetIndex = targetIndex;
        this.targetModule = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModule)) {
            throw new CommandException("Module specified does not exist!");
        }

        if (targetIndex.getZeroBased() >= model.getSizeOfModuleTaskList(targetModule)) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_TASK_INDEX);
        }

        model.doneModuleTask(targetModule, targetIndex);
        return new CommandResult(MESSAGE_SUCCESS + " " + targetModule + " task number "
                + targetIndex.getOneBased());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneModuleTaskCommand // instanceof handles nulls
                && targetModule.equals(((DoneModuleTaskCommand) other).targetModule)
                && targetIndex.equals(((DoneModuleTaskCommand) other).targetIndex));
    }
}
