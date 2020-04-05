package cookbuddy.logic.commands;

import cookbuddy.model.Model;
import cookbuddy.ui.UiManager;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    private static String commandDescription = "";

    public HelpCommand(String commandWord) {
        commandDescription = commandWord;
    }


    @Override
    public CommandResult execute(Model model) {
        UiManager.setCommandDescription(commandDescription);
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }

    public String getCommandWord () {
        return commandDescription;
    }
}
