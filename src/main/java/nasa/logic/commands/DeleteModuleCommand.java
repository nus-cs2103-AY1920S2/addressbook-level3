package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
<<<<<<< HEAD:src/main/java/nasa/logic/commands/DeleteModuleCommand.java
=======

import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.module.Module;
>>>>>>> cf98652144e57734b46653f44f8db289a6e30a49:src/main/java/NASA/logic/commands/DeleteModuleCommand.java

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

    public static final String MESSAGE_SUCCESS = "Activities are deleted successfully!";

    private final ArrayList<Module> modulesToDelete;

    public DeleteModuleCommand(ArrayList<Module> modules) {
        requireNonNull(modules);
        modulesToDelete = modules;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // TODO add the necessary implementation once model is done
        return new CommandResult("");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && modulesToDelete.equals(((DeleteModuleCommand) other).modulesToDelete));
    }
}
