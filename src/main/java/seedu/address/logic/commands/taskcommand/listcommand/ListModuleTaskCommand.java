package seedu.address.logic.commands.taskcommand.listcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.nusmodule.ModuleCode;

/**
 * Find tasks in module book by specific module code.
 */
public class ListModuleTaskCommand extends Command {
    public static final String MESSAGE_SUCCESS = "List of tasks for %s\n%s";
    public static final String COMMAND_WORD = "listModuleTasks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show the tasks related to certain module "
            + "Parameters: "
            + "MODULE CODE "
            + "Example: " + COMMAND_WORD + " CS2103T ";

    private final ModuleCode targetModuleCode;

    /**
     * Creates an AddModuleCommand to add the specified {@code NusModule}
     */
    public ListModuleTaskCommand(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        targetModuleCode = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(targetModuleCode)) {
            throw new CommandException(Messages.MESSAGE_NO_SUCH_MODULE);
        }
        String tasksInfo = model.getModuleTaskInfo(targetModuleCode);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetModuleCode, tasksInfo));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListModuleTaskCommand // instanceof handles nulls
                && targetModuleCode.equals(((ListModuleTaskCommand) other).targetModuleCode));
    }

}
