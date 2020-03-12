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
            + "CS2030 CS1231 CS1010S";

    public static final String MESSAGE_SUCCESS = " are deleted successfully!";

    public static final String MESSAGE_FAILURE = "Modules indicated all does not exist!";

    private final ArrayList<Module> modulesToDelete;

    public DeleteModuleCommand(ArrayList<Module> modules) {
        requireNonNull(modules);
        modulesToDelete = modules;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        StringBuilder msgToUser = new StringBuilder();
        for (Module module : modulesToDelete) {
            if (model.hasModule(module)) {
                msgToUser.append(module.getModuleCode().toString() + " ");
                model.deleteModule(module);
            }
        }
        if (msgToUser.length() == 0) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(msgToUser.toString().trim() + MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && modulesToDelete.equals(((DeleteModuleCommand) other).modulesToDelete));
    }
}
