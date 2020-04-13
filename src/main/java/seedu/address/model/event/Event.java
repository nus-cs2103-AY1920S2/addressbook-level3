package seedu.address.model.event;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.person.Time;

/**
 * Container class for adding activity, time spent and place to a person or a group
 */
public class Event {

    /**
     * Represents the eventID for the next created group
     */
    private static int events = 1;

    private int eventId;
    private String activity;
    private String place;
    private Optional<Integer> withPerson;
    private Optional<Integer> withGroup;
    private Time time;

    public Event(String activity, String place, Time time) {
        this.eventId = events;
        this.activity = activity;
        this.place = place;
        this.time = time;
        this.withPerson = Optional.empty();
        this.withGroup = Optional.empty();
        events += 1;
    }

    public String getActivity() {
        return this.activity;
    }

    public String getPlace() {
        return this.place;
    }

    public Optional<Integer> getWithGroup() {
        return this.withGroup;
    }

    public Optional<Integer> getWithPerson() {
        return this.withPerson;
    }

    public Time getTime() {
        return this.time;
    }

    public void setWithGroup(int groupId) {
        this.withGroup = Optional.of(groupId);
    }

    public void setWithPerson(int personId) {
        this.withPerson = Optional.of(personId);
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return this.eventId;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    /**
     * Checks that this event is the same event and has the same
     * event ID as the event parameter.
     *
     * @param other Event to be compared against.
     * @return True if the two events are the same, false otherwise.
     */
    public boolean isSameEvent(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return (this.getEventId() == otherEvent.getEventId()
                && this.getActivity().equalsIgnoreCase(otherEvent.getActivity())
                && this.getPlace().equalsIgnoreCase(otherEvent.getPlace())
                && this.getTime().equals(otherEvent.getTime())
                && this.getWithGroup().equals(otherEvent.getWithGroup())
                && this.getWithPerson().equals(otherEvent.getWithPerson()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return this.getEventId() == otherEvent.getEventId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, activity, place, withGroup, withPerson, time);
    }

    @Override
    public String toString() {
        return "Event: " + activity + " place: " + place + " for " + time;
    }
}
