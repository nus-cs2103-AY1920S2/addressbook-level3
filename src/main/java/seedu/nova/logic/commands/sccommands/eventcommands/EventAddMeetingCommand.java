package seedu.nova.logic.commands.sccommands.eventcommands;

import static java.util.Objects.requireNonNull;

import static seedu.nova.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.logging.Logger;

import seedu.nova.commons.core.LogsCenter;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;
import seedu.nova.model.Model;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.exceptions.TimeOverlapException;

/**
 * Adds a Meeting into the Schedule.
 */
public class EventAddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "meeting";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the schedule. \n"
            + "Parameters: "
            + PREFIX_DESC + "[description] "
            + PREFIX_VENUE + "[venue] "
            + PREFIX_TIME + "[YYYY-MM-DD] [Start time (HH:MM)] [End time (HH:MM)] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "CS2103T GUI refactoring "
            + PREFIX_VENUE + "COM1 Basement "
            + PREFIX_TIME + "2020-03-10 14:00 16:00 ";

    public static final String MESSAGE_SUCCESS = "New meeting has been added: \n%1$s\n";
    public static final String MESSAGE_TIME_OVERLAP = "You already have an event within that time frame.";
    public static final String MESSAGE_INVALID_DATE = "That date does not fall within the semester.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Event toAdd;

    /**
     * Creates an EventAddMeetingCommand to add the specified {@code Event}
     */
    public EventAddMeetingCommand(Event meeting) {
        requireNonNull(meeting);
        this.toAdd = meeting;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing add meeting command for: \n" + toAdd);

        requireNonNull(model);

        if (!model.isWithinSem(toAdd.getDate())) {
            throw new CommandException(MESSAGE_INVALID_DATE);
        }

        try {
            model.addEvent(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (TimeOverlapException e) {
            throw new CommandException(MESSAGE_TIME_OVERLAP);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventAddMeetingCommand // instanceof handles nulls
                && toAdd.equals(((EventAddMeetingCommand) other).toAdd));
    }
}
