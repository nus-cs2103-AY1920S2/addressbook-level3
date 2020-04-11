package seedu.address.logic.commands.modulecommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Calculates current CAP based on NUS modules the user has taken.
 */
public class CapCommand extends Command {

    public static final String COMMAND_WORD = "cap";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculates your current CAP based on the modules";

    public static final String MESSAGE_SUCCESS = "CAP calculated";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        double result = model.getCap();
        String message = MESSAGE_SUCCESS + "\nCurrent CAP: " + result;
        return new CommandResult(message);
    }
}
