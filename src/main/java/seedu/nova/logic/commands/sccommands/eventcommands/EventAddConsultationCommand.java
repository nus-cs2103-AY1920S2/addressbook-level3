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
 * Adds a Consultation into the Schedule.
 */
public class EventAddConsultationCommand extends Command {
    public static final String COMMAND_WORD = "consultation";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a consultation to the schedule. \n"
            + "Parameters: "
            + PREFIX_DESC + "[description] "
            + PREFIX_VENUE + "[venue] "
            + PREFIX_TIME + "[YYYY-MM-DD] [Start time (HH:MM)] [End time (HH:MM)] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "clarify UML "
            + PREFIX_VENUE + "COM1 "
            + PREFIX_TIME + "2020-03-20 14:00 16:00 ";

    public static final String MESSAGE_SUCCESS = "New consultation has been added: \n%1$s\n";
    public static final String MESSAGE_TIME_OVERLAP = "You already have an event within that time frame.";
    public static final String MESSAGE_INVALID_DATE = "That date does not fall within the semester.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Event toAdd;

    /**
     * Creates an EventAddConsultationCommand to add the specified {@code Event}
     */
    public EventAddConsultationCommand(Event consultation) {
        requireNonNull(consultation);
        this.toAdd = consultation;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing add consultation command for: \n" + toAdd);

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
                || (other instanceof EventAddConsultationCommand // instanceof handles nulls
                && toAdd.equals(((EventAddConsultationCommand) other).toAdd));
    }
}
