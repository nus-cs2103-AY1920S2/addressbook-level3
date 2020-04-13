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
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.schedule.event.exceptions.TimeOverlapException;


/**
 * Adds a Lesson into the Schedule.
 */
public class EventAddLessonCommand extends Command {
    public static final String COMMAND_WORD = "lesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the schedule. \n"
            + "Parameters: "
            + PREFIX_DESC + "[description] "
            + PREFIX_VENUE + "[venue] "
            + PREFIX_TIME + "[day] [Start time (HH:MM)] [End time (HH:MM)] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "CS2103T Tutorial "
            + PREFIX_VENUE + "COM1 B1-03 "
            + PREFIX_TIME + "Friday 10:00 11:00 ";

    public static final String MESSAGE_SUCCESS = "New lesson has been added: \n%1$s\n";
    public static final String MESSAGE_TIME_OVERLAP = "You already have an event within that time frame.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Event toAdd;

    /**
     * Creates an EventAddLessonCommand to add the specified {@code Event}
     */
    public EventAddLessonCommand(Event lesson) {
        requireNonNull(lesson);
        this.toAdd = lesson;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing add lesson command for: \n" + toAdd);

        requireNonNull(model);

        try {
            model.addAllLessons((Lesson) toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (TimeOverlapException e) {
            throw new CommandException(MESSAGE_TIME_OVERLAP);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventAddLessonCommand // instanceof handles nulls
                && toAdd.equals(((EventAddLessonCommand) other).toAdd));
    }


}
