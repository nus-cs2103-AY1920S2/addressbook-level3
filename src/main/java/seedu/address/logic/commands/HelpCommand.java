package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_CATEGORY = "help";

    public static final String MESSAGE_USAGE = "\n" + COMMAND_CATEGORY + ": shows program usage instructions.\n"
            + "Example: " + COMMAND_CATEGORY;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HelpCommand); // instanceof handles nulls
    }
}
