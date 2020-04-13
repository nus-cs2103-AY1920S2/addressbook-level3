package seedu.address.logic.commands.mods;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_NAME;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;

/**
 * Adds a module to TAble. Due to naming conflicts, this is reflected as "Mod".
 */
public class AddModCommand extends Command {
    public static final String COMMAND_WORD = "addMod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a module to TAble.\n"
        + "Parameters: "
        + PREFIX_MODULE_CODE + "MODULE_CODE "
        + PREFIX_MODULE_NAME + "MODULE_NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2103 "
        + PREFIX_MODULE_NAME + "Software Engineering";

    public static final String MESSAGE_SUCCESS = "New module added!\n%1$s";
    public static final String MESSAGE_DUPLICATE_MOD = "This module already exists in TAble.";

    private final Mod toAdd;

    /**
     * Creates an AddModCommand to add the specified {@code modCode}
     */
    public AddModCommand(ModCode modCode, String modName) {
        requireNonNull(modCode);
        toAdd = new Mod(modCode, modName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasMod(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MOD);
        }

        model.addMod(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddModCommand // instanceof handles nulls
            && toAdd.equals(((AddModCommand) other).toAdd));
    }
}
