package seedu.eylah.diettracker.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.eylah.commons.logic.command.Command;
import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.diettracker.model.DietModel;

/**
 * Shows the user their various metrics like Height, Weight and Dieting Mode.
 */
public class MetricsCommand extends Command<DietModel> {

    public static final String COMMAND_WORD = "metrics";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows you user metrics like height, weight, and "
            + "dieting mode"
            + "Parameters: NONE";
    public static final String MESSAGE_SUCCESS = "Your metrics are shown.\n";

    /**
     * Creates a MetricsCommand to show all user metrics (Height, Weight, Mode).
     */
    public MetricsCommand() {}

    @Override
    public CommandResult execute(DietModel model) throws CommandException {
        requireNonNull(model);
        String output = model.printMetrics();
        return new CommandResult(output.concat(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MetricsCommand // instanceof handles nulls
            );
    }
}
