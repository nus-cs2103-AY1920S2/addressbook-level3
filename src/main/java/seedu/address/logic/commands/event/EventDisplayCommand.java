package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Displays the schedule of events.
 */
public class EventDisplayCommand extends EventCommand {

    public static final String MESSAGE_SUCCESS = "This is your schedule for the week";

    /**
     * Creates an EventDisplayCommand.
     */
    public EventDisplayCommand() {

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return true;
    }
}
