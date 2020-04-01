package tatracker.logic.commands.commons;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tatracker.logic.commands.Command;
import tatracker.logic.commands.CommandDetails;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.CommandResult.Action;
import tatracker.logic.commands.CommandWords;
import tatracker.model.Model;
import tatracker.model.TaTracker;

/**
 * Clears the TA-Tracker.
 */
public class ClearCommand extends Command {

    public static final CommandDetails DETAILS = new CommandDetails(
            CommandWords.CLEAR,
            "Clears all TA-Tracker stored data.",
            List.of(),
            List.of()
    );

    public static final String MESSAGE_SUCCESS = "TA-Tracker has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTaTracker(new TaTracker());
        return new CommandResult(MESSAGE_SUCCESS, Action.NONE);
    }
}
