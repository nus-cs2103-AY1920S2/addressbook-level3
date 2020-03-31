package tatracker.logic.commands;

import java.util.List;

import tatracker.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.EXIT,
            "Exits TA-Tracker.",
            List.of(),
            List.of()
    );

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting TA-Tracker as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}
