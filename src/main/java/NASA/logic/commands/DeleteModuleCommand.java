package NASA.logic.commands;

import static java.util.Objects.requireNonNull;
import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;

import NASA.logic.commands.exceptions.CommandException;
import NASA.model.Model;
import NASA.model.module.Module;

import java.util.ArrayList;

public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = "Mdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":Deletes the modules specified in the NASA application" +
            ".\n "
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
