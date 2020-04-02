package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Lists all Events in the Events Schedule to the user.
 */
public class ListEventCommand extends Command {
    public static final String COMMAND_WORD = "eventlist";
    public static final String COMMAND_FUNCTION = "Shows a list of all your events which "
            + "Naggy Joel is keeping track for you "
            + "sorted by the chronological order based on event date. "
            + "Naggy Joel can only sort events one way.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION;

    public static final String MESSAGE_SUCCESS = "Here are all your events. "
        + "Stop going out so much now, got coronavirus you know!";

    private final Comparator<Event> comparator;

    /**
     * @param comparator to be sorted in the filtered assignment list
     */
    public ListEventCommand(Comparator<Event> comparator) {
        requireNonNull(comparator);
        this.comparator = comparator;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortEvent(comparator);
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, true, false, false, false);
    }

}
