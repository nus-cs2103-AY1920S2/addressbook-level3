package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.model.ModManager;
import seedu.address.model.Model;

/**
 * Clears the Mod Manager.
 */
public class ClearCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Mod Manager has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setModManager(new ModManager());
        model.updateModule(Optional.empty());
        return new CommandResult(MESSAGE_SUCCESS, CommandType.CLEAR);
    }
}
