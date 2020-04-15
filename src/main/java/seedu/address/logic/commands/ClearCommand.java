package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All data has been cleared!";
    public static final String COMMAND_FUNCTION = "Clears all entries in the application.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + " Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.clear();
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, false, false,
                false, false, true, false, false);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}
