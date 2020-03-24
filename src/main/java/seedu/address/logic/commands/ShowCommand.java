package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Show the courier his/her earnings for this week
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Show earnings.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOW_MESSAGE = "Showing your earnings now";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOW_MESSAGE, false, false, false, true);
    }
}
