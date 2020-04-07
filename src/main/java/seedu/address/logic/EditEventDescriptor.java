package seedu.address.logic;

import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.event.Duration;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventTitle;
import seedu.address.model.event.Place;

/**
 * Stores the details to edit the event with. Each non-empty field value will replace the
 * corresponding field value of the event.
 */
public class EditEventDescriptor {
    private EventTitle eventTitle;
    private EventDate eventDate;
    private Duration duration;
    private Place place;

    public EditEventDescriptor() {}

    /**
     * Copy constructor.
     */
    public EditEventDescriptor(EditEventDescriptor toCopy) {
        setEventTitle(toCopy.eventTitle);
        setEventDate(toCopy.eventDate);
        setDuration(toCopy.duration);
        setPlace(toCopy.place);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(eventTitle, eventDate, duration, place);
    }

    public void setEventTitle(EventTitle eventTitle) {
        this.eventTitle = eventTitle;
    }

    public Optional<EventTitle> getEventTitle() {
        return Optional.ofNullable(eventTitle);
    }

    public void setEventDate(EventDate eventDate) {
        this.eventDate = eventDate;
    }

    public Optional<EventDate> getEventDate() {
        return Optional.ofNullable(eventDate);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Optional<Duration> getDuration() {
        return Optional.ofNullable(duration);
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public Optional<Place> getPlace() {
        return Optional.ofNullable(place);
    }

    /**
     * Creates and returns an {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    public Event createEditedEvent(Event eventToEdit) {
        assert eventToEdit != null;

        EventTitle updatedEventTitle = getEventTitle().orElse(eventToEdit.getEventTitle());
        EventDate updatedEventDate = getEventDate().orElse(eventToEdit.getEventDate());
        Duration updatedDuration = getDuration().orElse(eventToEdit.getDuration());
        Place updatedPlace = getPlace().orElse(eventToEdit.getPlace());

        return new Event(updatedEventTitle, updatedEventDate, updatedDuration, updatedPlace);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEventDescriptor)) {
            return false;
        }

        // state check
        EditEventDescriptor e = (EditEventDescriptor) other;

        return getEventTitle().equals(e.getEventTitle())
                && getEventDate().equals(e.getEventDate())
                && getDuration().equals(e.getDuration())
                && getPlace().equals(e.getPlace());
    }
}
