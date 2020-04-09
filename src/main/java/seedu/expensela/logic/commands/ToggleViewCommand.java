package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.model.Model;

/**
 * Toggle between viewing transaction list and chart analytics in the UI.
 */
public class ToggleViewCommand extends Command {

    public static final String COMMAND_WORD = "toggleview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles between viewing transaction list"
            + "and chart analytics.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "View Toggled.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.switchToggleView();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
