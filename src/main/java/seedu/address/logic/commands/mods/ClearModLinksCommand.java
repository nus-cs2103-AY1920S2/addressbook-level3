package seedu.address.logic.commands.mods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_MOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODS;

import java.util.Optional;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.ModCode;

/**
 * Removes all Pairs of String, ModLink of a Mod in TAble.
 */
public class ClearModLinksCommand extends Command {
    public static final String COMMAND_WORD = "clearModLink";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all module links of a module in TAble.\n"
        + "Parameters: "
        + PREFIX_MODULE_CODE + "MODULE_CODE\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_MODULE_CODE + "CS2103";

    public static final String MESSAGE_SUCCESS = "All links are cleared from module\n%1$s";

    private final ModCode modCode;

    /**
     * Creates an AddModCommand to add the specified {@code mod}
     */
    public ClearModLinksCommand(ModCode modCode) {
        requireNonNull(modCode);
        this.modCode = modCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasMod(new Mod(modCode, ""))) {
            throw new CommandException(MESSAGE_MISSING_MOD);
        }

        Optional<Mod> optionalMod = model.findMod(modCode);
        assert optionalMod.isPresent();
        Mod modToEdit = optionalMod.get();

        Mod editedMod = modToEdit.clearLinks();
        model.setMod(modToEdit, editedMod);
        model.updateFilteredModList(PREDICATE_SHOW_ALL_MODS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedMod));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ClearModLinksCommand) // instanceof handles nulls
            && modCode.equals(((ClearModLinksCommand) other).modCode);
    }
}
