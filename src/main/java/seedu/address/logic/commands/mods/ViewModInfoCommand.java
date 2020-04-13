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
 * Lists all modules in TAble.
 */
public class ViewModInfoCommand extends Command {
    public static final String COMMAND_WORD = "viewModInfo";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views more detailed information of a module"
        + " identified by the module code. Module must exist in TAble.\n"
        + "Parameters: " + PREFIX_MODULE_CODE + "MODULE_CODE\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_MODULE_CODE + "CS2103 ";

    public static final String MESSAGE_VIEW_MOD_SUCCESS = "Updating view to module: %1$s";

    private final ModCode modCode;

    /**
     * Creates a ViewModInfoCommand object with necessary information.
     * @param modCode module in the filtered module list to edit
     */
    public ViewModInfoCommand(ModCode modCode) {
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
        Mod mod = optionalMod.get();

        model.setViewedMod(mod);
        model.updateFilteredModList(PREDICATE_SHOW_ALL_MODS);
        return new CommandResult(String.format(MESSAGE_VIEW_MOD_SUCCESS, mod),
            false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewModInfoCommand)) {
            return false;
        }

        // state check
        ViewModInfoCommand e = (ViewModInfoCommand) other;
        return modCode.equals(e.modCode);
    }
}
