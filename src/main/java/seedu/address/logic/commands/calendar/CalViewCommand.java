package seedu.address.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.model.Model;
import seedu.address.model.calendar.Calendar;

/**
 * Show calendar view to user.
 */
public class CalViewCommand extends CalCommand {

    public static final String MESSAGE_USAGE = COMMAND_GROUP_CAL + " " + COMMAND_WORD_VIEW
            + ": Views the calendar for a week.\n"
            + "Parameters: "
            + PREFIX_WEEK + " this OR "
            + PREFIX_WEEK + " next";

    public static final String MESSAGE_SUCCESS = "Calendar can be viewed";

    private Calendar calendar;

    /**
     * Creates a CalViewCommand to view the specified week.
     */
    public CalViewCommand(Calendar calendar) {
        requireNonNull(calendar);
        this.calendar = calendar;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateCalendar(calendar);
        return new CommandResult(MESSAGE_SUCCESS, CommandType.CALENDAR);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CalViewCommand // instanceof handles nulls
                && calendar.equals(((CalViewCommand) other).calendar));
    }
}
