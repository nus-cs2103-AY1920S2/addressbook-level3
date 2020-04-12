package nasa.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import nasa.logic.commands.Command;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.module.ModuleCode;

/**
 * Deletes a module identified using it's code from NASA.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "del";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module specified by the code in NASA.\n"
            + "Parameters: "
            + PREFIX_MODULE
            + ".....\n"
            + "Example: "
            + COMMAND_WORD
            + " "
            + PREFIX_MODULE
            + "CS2030";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "%1$s is deleted successfully!";

    public static final String MESSAGE_FAILURE = "Module indicated does not exist!";

    private final ModuleCode moduleToDelete;

    /**
     * Creates a DeleteModuleCommand to delete the specified {@code module}.
     * @param moduleCode Module to be deleted
     */
    public DeleteModuleCommand(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        moduleToDelete = moduleCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasModule(moduleToDelete)) {
            model.deleteModule(moduleToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && moduleToDelete.equals(((DeleteModuleCommand) other).moduleToDelete));
    }
}
