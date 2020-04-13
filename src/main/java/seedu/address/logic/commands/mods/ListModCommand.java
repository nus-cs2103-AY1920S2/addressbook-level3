package seedu.address.logic.commands.mods;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODS;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all modules in TAble.
 */
public class ListModCommand extends Command {
    public static final String COMMAND_WORD = "listMod";

    public static final String MESSAGE_SUCCESS = COMMAND_WORD + ": Listed all modules\n";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredModList(PREDICATE_SHOW_ALL_MODS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, true, false);
    }
}
