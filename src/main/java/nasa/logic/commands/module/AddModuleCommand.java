package nasa.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE;
import static nasa.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

import nasa.logic.commands.Command;
import nasa.logic.commands.CommandResult;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.Model;
import nasa.model.module.Module;

/**
 * Adds a module to NASA.
 */
public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to NASA. "
            + "Parameters: "
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_MODULE_NAME + "MODULE NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS3233 "
            + PREFIX_MODULE_NAME + "Competitive Programming";

    public static final String MESSAGE_SUCCESS = "New module %s added!";
    public static final String MESSAGE_DUPLICATED_MODULE = "This module already exist!";

    private Module toAdd;

    /**
     * Creates an AddModuleCommand to add the specified {@code Module}
     * @param module Module to be added
     */
    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd.getModuleCode())) {
            throw new CommandException(MESSAGE_DUPLICATED_MODULE);
        }

        model.addModule(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}
