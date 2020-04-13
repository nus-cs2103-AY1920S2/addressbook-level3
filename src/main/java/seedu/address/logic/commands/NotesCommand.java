package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class NotesCommand extends Command {

    public static final String COMMAND_WORD = "notes";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows notes tab.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SWITCHED_MESSAGE = "Switched to notes Tab";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SWITCHED_MESSAGE, false, false, true);
    }
}
