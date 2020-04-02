package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Adds an Event to the schedule.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "event";
    public static final String COMMAND_FUNCTION = "Adds an event to your schedule. ";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: "
            + PREFIX_EVENTTITLE + "EVENT TITLE "
            + PREFIX_EVENTDATE + "EVENT DATE "
            + PREFIX_DURATION + "DURATION "
            + PREFIX_PLACE + "LOCATION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EVENTTITLE + "Facebook job interview "
            + PREFIX_EVENTDATE + "2020-04-18 10:00 "
            + PREFIX_DURATION + "3 " // hours
            + PREFIX_PLACE + "Facebook APAC HQ";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event has already been recorded. "
            + "Stop going out so much, now got coronavirus! >:(";

    private final Event toAdd;

    /**
     * Creates an AddEventCommand to add the specified {@code Event}
     */
    public AddEventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd), false, false,
                false, false, true, false, false, false);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddEventCommand // instanceof handles nulls
                && toAdd.equals(((AddEventCommand) other).toAdd));
    }
}

