package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;

import java.util.ArrayList;

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.module.Module;

public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "Mdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Deletes the modules specified in the NASA application"
            + ".\n "
            + "Parameters:"
            + PREFIX_MODULE + "....." + "\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS2030";

    public static final String MESSAGE_SUCCESS = " is deleted successfully!";

    public static final String MESSAGE_FAILURE = "Module indicated all does not exist!";

    private final Module moduleToDelete;

    public DeleteModuleCommand(Module module) {
        requireNonNull(module);
        moduleToDelete = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasModule(moduleToDelete)) {
            model.deleteModule(moduleToDelete);
            return new CommandResult(moduleToDelete.getModuleCode().toString() + MESSAGE_SUCCESS);
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
