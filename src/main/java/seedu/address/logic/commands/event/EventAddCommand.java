package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_EVENT;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Class to add new events
 */
public class EventAddCommand extends EventCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new event to the scheduler. \n"
            + "Parameters:\n"
            + "eventName/[EVENTNAME]\n"
            + "startDateTime/[STARTDATETIME]\n"
            + "endDateTime/[ENDDATETIME]\n"
            + "recur/[DAILY/WEEKLY/NONE]\n"
            + "color/[0-23]\n"
            + "Example: schedule eventName/Consultation startDateTime/2020-03-30T08:00 endDateTime/2020-03-30T10:00 "
            + "recur/none color/3";
    public static final String MESSAGE_SUCCESS = "Added event: %1$s";

    private final VEvent vEventToAdd;

    /**
     * Constructor for add event command
     * @param vEventToAdd event to be added
     */
    public EventAddCommand(VEvent vEventToAdd) {
        requireNonNull(vEventToAdd);
        this.vEventToAdd = vEventToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.hasVEvent(vEventToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } else {
            model.addVEvent(vEventToAdd);
            String successMessage = makeSuccessMessage(vEventToAdd);
            return new CommandResult(successMessage);
        }
    }

    /**
     * Success message builder
     */
    public String makeSuccessMessage(VEvent vEvent) {
        return String.format(MESSAGE_SUCCESS, vEvent.getSummary().getValue());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventAddCommand)) {
            return false;
        }

        EventAddCommand eventAddCommand = (EventAddCommand) other;
        return vEventToAdd.equals(eventAddCommand.vEventToAdd);
    }
}
