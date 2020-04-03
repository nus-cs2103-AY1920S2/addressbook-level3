package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a social event that a user wants to keep track of.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {
    // Identity field
    private final EventTitle eventTitle;

    // Data field
    private final EventDate eventDate;
    private final Duration duration;
    private final Place place;

    /**
     * Every field must be present and non-null.
     * @param eventTitle The description of the event to be attended.
     * @param eventDate The date and time of the event.
     * @param duration The duration of the event.
     * @param place The place at which the event will be held.
     */
    public Event(EventTitle eventTitle, EventDate eventDate, Duration duration, Place place) {
        requireAllNonNull(eventTitle, eventDate, duration, place);
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.duration = duration;
        this.place = place;
    }

    // Need for constructor to be used when loading saved events?

    public EventTitle getEventTitle() {
        return eventTitle;
    }

    public EventDate getEventDate() {
        return eventDate;
    }

    public Duration getDuration () {
        return duration;
    }

    public Place getPlace() {
        return place;
    }

    // NTS can change to start time & end time instead of duration?
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event: ")
                .append(getEventTitle())
                .append("\nDate of event: ")
                .append(getEventDate())
                .append("\nDuration of event: ")
                .append(getDuration())
                .append("\nLocation of event: ")
                .append(getPlace())
                .append("\n");
        return builder.toString();
    }

    /**
     * Returns true if both events are the same.
     * Two events are the same if they have the same title and date.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventDate().dateTime.compareTo(getEventDate().dateTime) == 0
                && (otherEvent.getEventTitle().equals(getEventTitle()));
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;

        return otherEvent.getEventTitle().equals(getEventTitle())
                && otherEvent.getDuration().equals(getDuration())
                && otherEvent.getPlace().equals(getPlace())
                && otherEvent.getEventDate().dateTime.compareTo(getEventDate().dateTime) == 0;
    }


}
