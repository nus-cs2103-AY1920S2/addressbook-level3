package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Command to show a calendar pop up display.
 */
public class ViewCalendarCommand extends Command {

    public static final String COMMAND_WORD = "viewCalendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows a calendar view of all tutorials and consults.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_CALENDAR_MESSAGE = "Opened Calendar window.";

    @Override
    public CommandResult execute(Model model) {
        return new
            CommandResult(SHOWING_CALENDAR_MESSAGE, true, false, false, false);
    }
}
