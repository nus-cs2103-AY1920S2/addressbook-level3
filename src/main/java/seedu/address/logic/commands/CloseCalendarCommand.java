package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Command to close a calendar pop up display.
 */
public class CloseCalendarCommand extends Command {

    public static final String COMMAND_WORD = "closeCalendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Closes the calendar view.\n"
        + "Example: " + COMMAND_WORD;

    public static final String CLOSE_CALENDAR_MESSAGE = "Closed calendar window.";

    @Override
    public CommandResult execute(Model model) {
        return new
            CommandResult(CLOSE_CALENDAR_MESSAGE, true);
    }
}
