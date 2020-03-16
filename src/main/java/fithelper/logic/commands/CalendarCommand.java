package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.CALENDAR;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the calendar view. ";

    public static final String MESSAGE_SUCCESS = "Now you are at Calendar Page ~";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS), CALENDAR, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarCommand);
    }
}
