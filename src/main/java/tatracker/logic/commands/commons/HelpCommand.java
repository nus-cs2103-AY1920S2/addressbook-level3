package tatracker.logic.commands.commons;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    // @@author potatocombat

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.HELP,
            "Shows the help window",
            List.of(),
            List.of()
    );

    // @@author

    public static final String MESSAGE_SHOWING_HELP = "Opened help window";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SHOWING_HELP, Action.HELP);
    }
}
