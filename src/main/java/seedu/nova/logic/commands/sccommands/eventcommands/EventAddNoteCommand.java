package seedu.nova.logic.commands.sccommands.eventcommands;

import static java.util.Objects.requireNonNull;
import static seedu.nova.logic.parser.CliSyntax.PREFIX_DESC;
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
 * Adds a note into an Event using its date and index in the list.
 */
public class EventAddNoteCommand extends Command {
    public static final String COMMAND_WORD = "note";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to an event. \n"
            + "Parameters: "
            + PREFIX_DESC + "[description] "
            + PREFIX_TIME + "[YYYY-MM-DD] "
            + PREFIX_INDEX + "[index]"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESC + "Remember to bring charger! "
            + PREFIX_TIME + "2020-03-10 "
            + PREFIX_INDEX + "2";

    public static final String MESSAGE_SUCCESS = "New note has been added: \n%1$s\n";
    public static final String MESSAGE_NO_EVENT = "Invalid date - you have no events that week.";
    public static final String MESSAGE_INVALID_INDEX_DATE = "Invalid date or index - that event does not exist.";
    public static final String MESSAGE_INVALID_DATE = "That date does not fall within the semester.";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private String desc;
    private LocalDate date;
    private Index index;

    public EventAddNoteCommand(String desc, LocalDate date, Index index) {
        requireNonNull(desc);
        requireNonNull(date);
        requireNonNull(index);

        this.desc = desc;
        this.date = date;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("executing add note command for event at: \n" + "date: " + date + ", index: " + index);

        requireNonNull(model);

        if (!model.isWithinSem(date)) {
            throw new CommandException(MESSAGE_INVALID_DATE);
        }

        try {
            int i = index.getZeroBased();
            String response = model.addNote(desc, date, i);
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
                || (other instanceof EventAddNoteCommand // instanceof handles nulls
                && index.equals(((EventAddNoteCommand) other).index)
                && date.equals(((EventAddNoteCommand) other).date)
                && desc.equals(((EventAddNoteCommand) other).desc)); // state check
    }

}
