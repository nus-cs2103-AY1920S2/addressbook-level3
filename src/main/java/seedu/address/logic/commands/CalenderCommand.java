package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class CalenderCommand extends Command {

    public static final String COMMAND_WORD = "calender";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows Calender tab.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SWITCHED_MESSAGE = "Switched to Calender Tab";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SWITCHED_MESSAGE, false, false, true);
    }
}
