package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTTITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLACE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.EditEventDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;

/**
 * Edits the details of an existing event in the EventSchedule.
 */
public class EditEventCommand extends Command {

    public static final String COMMAND_WORD = "(ev)edit";
    public static final String COMMAND_FUNCTION = "Edits the estimated event identified "
            + "by the index number used in the displayed event list. "
            + "Existing values will be overwritten by the input values.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": " + COMMAND_FUNCTION + "\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EVENTTITLE + "EVENT_TITLE] "
            + "[" + PREFIX_EVENTDATE + "EVENT_DATE] "
            + "[" + PREFIX_DURATION + "DURATION] "
            + "[" + PREFIX_PLACE + "PLACE]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_EVENTTITLE + "Class outing "
            + PREFIX_EVENTDATE + "2020-11-12 23:59 "
            + PREFIX_DURATION + "5 "
            + PREFIX_PLACE + "Gardens by the Bay";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the event schedule.";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered Event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EditEventCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = lastShownList.get(index.getZeroBased());
        Event editedEvent = editEventDescriptor.createEditedEvent(eventToEdit);

        if (!eventToEdit.isSameEvent(editedEvent) && model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.setEvent(eventToEdit, editedEvent);
        model.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, eventToEdit),
                false, false, false, false, true, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventCommand)) {
            return false;
        }

        // state check
        EditEventCommand e = (EditEventCommand) other;
        return index.equals(e.index)
                && editEventDescriptor.equals(e.editEventDescriptor);
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }

}
