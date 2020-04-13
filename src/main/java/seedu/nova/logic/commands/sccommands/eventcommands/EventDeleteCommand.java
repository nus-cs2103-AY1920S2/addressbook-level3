package seedu.nova.logic.commands.sccommands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDate;
import java.util.logging.Logger;

import seedu.nova.commons.core.LogsCenter;
import seedu.nova.commons.core.index.Index;
import seedu.nova.logic.commands.Command;
import seedu.nova.logic.commands.CommandResult;
import seedu.nova.logic.commands.exceptions.CommandException;

import seedu.nova.model.Model;
import seedu.nova.model.schedule.event.exceptions.DateNotFoundException;
import seedu.nova.model.schedule.event.exceptions.EventNotFoundException;

/**
 * Deletes an Event from the Schedule using its date and index in the list.
 */
public class EventDeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an event from the schedule. \n"
            + "Parameters: "
            + PREFIX_TIME + "[YYYY-MM-DD] "
            + PREFIX_INDEX + "[index] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TIME + "2020-03-20 "
            + PREFIX_INDEX + "2 ";

    public static final String MESSAGE_SUCCESS = "Event has been deleted: \n%1$s\n";
    public static final String MESSAGE_NO_EVENT = "Invalid date - you have no events that week.";
    public static final String MESSAGE_INVALID_INDEX_DATE = "Invalid date or index - that event does not exist.";
    public static final String MESSAGE_INVALID_DATE = "That date does not fall within the semester.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private LocalDate date;
    private Index index;

    public EventDeleteCommand(LocalDate date, Index index) {
        requireNonNull(date);
        requireNonNull(index);

        this.date = date;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing delete command for event at: \n" + "date: " + date + ", index: " + index);

        requireNonNull(model);

        if (!model.isWithinSem(date)) {
            throw new CommandException(MESSAGE_INVALID_DATE);
        }

        try {
            int i = index.getZeroBased();
            String response = model.deleteEvent(date, i);
            return new CommandResult(String.format(MESSAGE_SUCCESS, response));

        } catch (DateNotFoundException e) {
            throw new CommandException(MESSAGE_NO_EVENT);

        } catch (EventNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_INDEX_DATE);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDeleteCommand // instanceof handles nulls
                && index.equals(((EventDeleteCommand) other).index)
                && date.equals(((EventDeleteCommand) other).date)); // state check
    }

}
