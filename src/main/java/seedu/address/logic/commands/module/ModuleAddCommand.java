package seedu.address.logic.commands.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.util.action.DoableActionType;
import seedu.address.model.util.action.ModuleAction;

/**
 * Adds a module to Mod Manager.
 */
public class ModuleAddCommand extends ModuleCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_MOD + " " + COMMAND_WORD_ADD
            + ": Adds a module to Mod Manager. \n"
            + "Parameters: "
            + PREFIX_MODULE_CODE + " MOD_CODE "
            + "[" + PREFIX_DESCRIPTION + " DESCRIPTION] \n"
            + "Example: " + COMMAND_GROUP_MOD + " " + COMMAND_WORD_ADD + " "
            + PREFIX_MODULE_CODE + " CS2103T "
            + PREFIX_DESCRIPTION + " Software Engineering ";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in Mod Manager.";

    private final Module toAdd;

    /**
     * Creates a ModuleAddCommand to add the specified {@code module}.
     */
    public ModuleAddCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasModule(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.addModule(toAdd);

        ModuleAction addModAction = new ModuleAction(toAdd, DoableActionType.ADD);
        model.addAction(addModAction);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), CommandType.MODULE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleAddCommand // instanceof handles nulls
                && toAdd.equals(((ModuleAddCommand) other).toAdd));
    }
}
