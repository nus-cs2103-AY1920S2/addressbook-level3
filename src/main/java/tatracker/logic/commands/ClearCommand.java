package tatracker.logic.commands;

import static java.util.Objects.requireNonNull;

import tatracker.model.Model;
import tatracker.model.TaTracker;

/**
 * Clears the TA-Tracker.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "TA-Tracker has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setTaTracker(new TaTracker());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
