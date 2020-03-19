package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandWords.DELETE_MODEL;
import static seedu.address.logic.commands.CommandWords.MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Deletes a module identified using it's module code.
 */
public class DeleteModuleCommand extends Command {

    public static final String COMMAND_WORD = MODULE + " " + DELETE_MODEL;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the module identified by the module code.\n"
            + "Parameters: " + PREFIX_MODULE + " MODULE_CODE\n"
            + "Example: " + COMMAND_WORD + " CS2013T";

    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_INVALID_MODULE_CODE = "There is no module with this module code.";

    private final Module module;

    public DeleteModuleCommand(Module module) {
        this.module = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasModule(module)) {
            throw new CommandException(MESSAGE_INVALID_MODULE_CODE);
        }

        Module moduleToDelete = model.getModule(module);
        model.deleteModule(moduleToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MODULE_SUCCESS, moduleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteModuleCommand // instanceof handles nulls
                && module.equals(((DeleteModuleCommand) other).module)); // state check
    }
}

