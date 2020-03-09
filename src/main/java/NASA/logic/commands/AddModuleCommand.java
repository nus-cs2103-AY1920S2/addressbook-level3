package NASA.logic.commands;

import static java.util.Objects.requireNonNull;
import static NASA.logic.parser.CliSyntax.PREFIX_MODULE;
import static NASA.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

import NASA.logic.commands.exceptions.CommandException;
import NASA.model.ModelNasa;
import NASA.model.module.Module;

public class AddModuleCommand extends Command {

    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to NASA. "
            + "Parameters:"
            + PREFIX_MODULE + "MODULE CODE"
            + PREFIX_MODULE_NAME + "MODULE NAME" + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MODULE + "CS3233"
            + PREFIX_MODULE_NAME + "Competitive Programming";

    public static final String MESSAGE_SUCCESS = "New module added!";
    public static final String MESSAGE_DUPLICATED_MODULE = "This module already exist!";

    private Module toAdd;

    public AddModuleCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(ModelNasa model) throws CommandException {
        requireNonNull(model);
        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATED_MODULE);
        }
        model.addModule(toAdd);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddModuleCommand // instanceof handles nulls
                && toAdd.equals(((AddModuleCommand) other).toAdd));
    }
}
