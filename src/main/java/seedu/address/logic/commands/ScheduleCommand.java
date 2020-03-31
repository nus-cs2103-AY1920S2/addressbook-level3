package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NUM_DAYS;

import seedu.address.model.Model;

/**
 * Displays user's expected workload for the next n days (including today) based on stored assignments, their deadlines
 * and estimated work hours per assignment.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "(st)schedule";
    public static final String COMMAND_FUNCTION = "Calculates and displays the estimated workload for the next "
        + "NUM_DAYS days (including today) based on stored assignments, their deadlines and estimated work hours "
        + "per assignment.";
    public static final String MESSAGE_SUCCESS = "Your expected workload can be found in the panel on the right!\n"
        + "Note that this only takes into account your stored assignments and nothing else...";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
        + "Parameters: "
        + PREFIX_NUM_DAYS + "NUM_DAYS "
        + "Example: "
        + COMMAND_WORD + " " + PREFIX_NUM_DAYS + "5";

    private final int numDays;

    public ScheduleCommand(int numDays) {
        this.numDays = numDays;
    }

    @Override
    public CommandResult execute(Model model) {
        model.calculateScheduleIntensity(numDays);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, false, false, false, true);
    }
}
