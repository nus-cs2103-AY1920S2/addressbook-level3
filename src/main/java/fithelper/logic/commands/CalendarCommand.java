package fithelper.logic.commands;

import static fithelper.logic.commands.CommandResult.DisplayedPage.CALENDAR;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_DATE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_MODE;
import static fithelper.logic.parser.CliSyntaxUtil.PREFIX_SHOW;
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
            + PREFIX_SHOW + "SHOW "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-04-20"
            + PREFIX_MODE + "list"
            + PREFIX_SHOW + "2020-04-20";

    public static final String MESSAGE_SUCCESS = "Now you are at Calendar Page ~";

    private String dateToSet;
    private String mode;
    private String show;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (dateToSet != null) {
            model.setCalendarDate(dateToSet);
        }
        if (mode != null) {
            model.setCalendarMode(mode);
        }
        if (show != null) {
            model.setCalendarShow(show);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS + "Reference Date: "
                + model.getCalendarDate()), CALENDAR, false);
    }

    public void setDate(String date) {
        requireNonNull(date);
        this.dateToSet = date;
    }

    public void setMode(String mode) {
        requireNonNull(mode);
        this.mode = mode;
    }
    public void setShow(String show) {
        this.show = show;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalendarCommand);
    }
}
