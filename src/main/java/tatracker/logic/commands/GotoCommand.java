package tatracker.logic.commands;

import tatracker.model.Model;

/**
 * Switches between tabs
 */
public class GotoCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches to the specified tab.\n"
            + "Example: " + COMMAND_WORD + " Student";

    private static String tabName;

    public GotoCommand(String tabName) {
        this.tabName = tabName;
    }

    private static final String SHOWING_GOTO_MESSAGE = "Switched to %s ";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(String.format(SHOWING_GOTO_MESSAGE, tabName), true, false, false);
    }
}
