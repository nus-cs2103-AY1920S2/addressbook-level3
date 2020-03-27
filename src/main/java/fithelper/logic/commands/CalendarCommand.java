package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.CALENDAR;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_MODE;
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
            + PREFIX_MODE + "MODE "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "tmr"
            + PREFIX_MODE + "list";

    public static final String MESSAGE_SUCCESS = "Now you are at Calendar Page ~";
    private String dateToSet;
    private String mode;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (dateToSet != null) {
            model.setCalendarDate(dateToSet);
        }
        if (mode != null) {
            model.setCalendarMode(mode);
        }
        String mode;
        if (model.getCalendarMode().equals("ls")) {
            mode = "list";
        } else {
            mode = "timetable";
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS + "Reference Date: "
                + model.getCalendarDate().toLocalDate())
                + "\nMode: " + mode, CALENDAR, false);
    }

    public void setDate(String date) {
        requireNonNull(date);
        this.dateToSet = date;
    }

    public void setMode(String mode) {
        requireNonNull(mode);
        this.mode = mode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarCommand);
    }
}
