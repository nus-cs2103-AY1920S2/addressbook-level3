package tatracker.logic.commands;

import java.util.List;

import tatracker.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            "help",
            "Shows program usage instructions.",
            List.of(),
            List.of()
    );

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
