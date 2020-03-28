package tatracker.logic.commands;

import tatracker.model.Model;

/**
 * Switches between tabs
 */
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches to the specified tab.\n"
            + "Example: " + COMMAND_WORD + "Student";

    private static String tabName = "student"; //TODO: Parser and enum

    private static final String SHOWING_GOTO_MESSAGE = "Switched to " + tabName;

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_GOTO_MESSAGE, true, false, false);
    }
}
