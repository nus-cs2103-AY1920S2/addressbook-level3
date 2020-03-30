package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Displays user's expected workload for the next 7 days (including today) based on stored assignments, their deadlines
 * and estimated work hours per assignment.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "(st)schedule";
    public static final String COMMAND_FUNCTION = "Calculates and displays the estimated workload for the next 7 days"
        + "(including today) based on stored assignments, their deadlines and estimated work hours per assignment.";
    public static final String MESSAGE_SUCCESS = "Your expected workload can be found in the panel on the right!\n"
        + "Note that this only takes into account your stored assignments and nothing else...";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION;

    public ScheduleCommand() {}

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, false, false, false, true);
    }
}
