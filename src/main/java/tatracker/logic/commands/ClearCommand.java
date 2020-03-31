package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tatracker.model.Model;
import tatracker.model.TaTracker;

/**
 * Clears the TA-Tracker.
 */
public class ClearCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            "clear",
            "Clears all TA-Tracker stored data.",
            List.of(),
            List.of()
    );

    public static final String COMMAND_WORD = "clear";

    public static final String INFO = "Clears all TA-Tracker stored data.";

    public static final String MESSAGE_SUCCESS = "TA-Tracker has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTaTracker(new TaTracker());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
