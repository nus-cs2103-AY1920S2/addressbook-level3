package tatracker.logic.commands.commons;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    // @@author potatocombat

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.EXIT,
            "Exits TA-Tracker",
            List.of(),
            List.of()
    );

    // @@author

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TA-Tracker as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, Action.EXIT);
    }

}
