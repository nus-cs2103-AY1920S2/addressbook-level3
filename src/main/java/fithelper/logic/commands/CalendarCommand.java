package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.CALENDAR;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static java.util.Objects.requireNonNull;

import fithelper.model.Model;

/**
 * Adds a entry to FitHelper.
 */
public class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the calendar view. "
            + "Parameters (Optional): "
            + PREFIX_DATE + "DATE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "tmr";

    public static final String MESSAGE_SUCCESS = "Now you are at Calendar Page ~";
    private String dateToSet;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (dateToSet != null) {
            model.setCalendarDate(dateToSet);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS + "Reference Date:" + model.getCalendarDate().toLocalDate()), CALENDAR, false);
    }

    public void setDate(String date) {
        requireNonNull(date);
        this.dateToSet = date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarCommand);
    }
}
