package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String MESSAGE_USAGE = COMMAND_WORD_HELP + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD_HELP;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, CommandType.HELP);
    }
}
