package seedu.nova.logic.commands.sccommands;

import static java.util.Objects.requireNonNull;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;

/**
 * The view week command of schedule.
 */
public class ScViewWeekCommand extends ScViewCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " week : Views your schedule on a particular week. "
            + "Parameters: "
            + PREFIX_INDEX + "[week #]";

    public static final String MESSAGE_WEEK_OUT_OF_RANGE = "That week is not within the schedule";
    public static final String MESSAGE_WEEK_NO_EVENT = "You have no events on that week";

    private final int weekNumber;

    /**
     * Instantiates a new ScViewWeekCommand object.
     *
     * @param weekNumber the week number to view
     */
    public ScViewWeekCommand(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (!model.isWithinSem(weekNumber)) {
            throw new CommandException(MESSAGE_WEEK_OUT_OF_RANGE);
        }

        String message = model.viewSchedule(weekNumber);

        if (message.equals("")) {
            message = MESSAGE_WEEK_NO_EVENT;
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScViewWeekCommand // instanceof handles nulls
                && weekNumber == ((ScViewWeekCommand) other).weekNumber);
    }

}
