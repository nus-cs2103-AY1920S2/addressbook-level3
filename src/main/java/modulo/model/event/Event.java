package modulo.model.event;

import static java.util.Objects.requireNonNull;
import static modulo.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import modulo.model.Name;
import modulo.model.deadline.Deadline;
import modulo.model.displayable.Displayable;
import modulo.model.module.Module;

/**
 * Represents an event in Modulo. Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Displayable {

    // Identity Fields
    private final Name name;
    private final EventType eventType;
    private final LocalDateTime eventStart;
    private final LocalDateTime eventEnd;
    private final Module parentModule;
    private final Location location;
    private Slot slot = null;

    // Data Fields
    private final List<Deadline> deadlines;

    public Event(Name name, EventType eventType, LocalDateTime eventStart,
                 LocalDateTime eventEnd, Module parentModule, Location location) {
        requireAllNonNull(name, eventType, eventStart, eventEnd, parentModule, location);
        this.name = name;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.parentModule = parentModule;
        this.location = location;
        this.deadlines = new ArrayList<>();
        this.deadlines.add(new Deadline(new Name(this.eventType.getDefaultDeadlineDescription()), this));
    }

    public Event(Name name, EventType eventType, LocalDateTime eventStart,
                 LocalDateTime eventEnd, Module parentModule, Location location, Slot slot) {
        requireAllNonNull(name, eventType, eventStart, eventEnd, parentModule, location);
        this.name = name;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.parentModule = parentModule;
        this.location = location;
        this.slot = slot;
        this.deadlines = new ArrayList<>();
        this.deadlines.add(new Deadline(new Name(this.eventType.getDefaultDeadlineDescription()), this));
    }

    public Event(Name name, EventType eventType, LocalDateTime eventStart,
                 LocalDateTime eventEnd, Module parentModule, Location location, List<Deadline> deadlines) {
        requireAllNonNull(name, eventType, eventStart, eventEnd, parentModule, deadlines);
        this.name = name;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.parentModule = parentModule;
        this.location = location;
        this.deadlines = deadlines;
    }

    public Event(Name name, EventType eventType, LocalDateTime eventStart, LocalDateTime eventEnd,
                 Module parentModule, Location location, List<Deadline> deadlines, Slot slot) {
        requireAllNonNull(name, eventType, eventStart, eventEnd, parentModule, deadlines);
        this.name = name;
        this.eventType = eventType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.parentModule = parentModule;
        this.location = location;
        this.deadlines = deadlines;
        this.slot = slot;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Name getName() {
        return name;
    }

    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public Module getParentModule() {
        return parentModule;
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    public Location getLocation() {
        return location;
    }

    public Slot getSlot() {
        return slot;
    }

    // Data operations

    /**
     * Adds a deadline to the list of deadlines.
     *
     * @param deadline Deadline to add.
     */
    public void addDeadline(Deadline deadline) {
        requireNonNull(deadline);
        deadlines.add(deadline);
    }

    /**
     * Removes a deadline to the list of deadlines.
     *
     * @param deadline Deadline to remove.
     */
    public void removeDeadline(Deadline deadline) {
        requireNonNull(deadline);
        deadlines.remove(deadline);
    }

    /**
     * Removes all deadlines from the list of deadlines.
     */
    public void removeAllDeadlines() {
        deadlines.clear();
    }


    /**
     * Checks if a deadline has already been added.
     *
     * @param deadline Deadline to check.
     * @return Boolean that represents if the deadline already exists.
     */
    public boolean containsDeadline(Deadline deadline) {
        requireNonNull(deadline);
        return this.deadlines.stream().anyMatch(d -> d.isSameDeadline(deadline));
    }

    /**
     * Returns true if both events have the same identity.
     *
     * @param otherEvent The event to compare to.
     * @return boolean value denoting whether the two events are the same event.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().toString().toLowerCase().equals(getName().toString().toLowerCase())
                && otherEvent.getParentModule().getModuleCode().equals(getParentModule().getModuleCode())
                && otherEvent.getParentModule().getAcademicYear().equals(getParentModule().getAcademicYear());
    }

    /**
     * Returns true if both events are of the same category e.g. both tutorials of the same module.
     *
     * @param otherEvent The event to compare to.
     * @return boolean value denoting whether the two events are the same category of events.
     */
    public boolean isSameCategoryOfEvents(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent.getEventType().equals(getEventType())
                && otherEvent.getParentModule().getModuleCode().equals(getParentModule().getModuleCode())
                && otherEvent.getParentModule().getAcademicYear().equals(getParentModule().getAcademicYear());
    }

    /**
     * Returns true if both events have the same identity and data fields. This defines a stronger notion of equality
     * between two events.
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
        return otherEvent.getName().equals(getName())
                && otherEvent.getParentModule().getModuleCode().equals(getParentModule().getModuleCode())
                && otherEvent.getParentModule().getAcademicYear().equals(getParentModule().getAcademicYear())
                && otherEvent.getDeadlines().equals(getDeadlines())
                && otherEvent.getEventEnd().equals(getEventEnd())
                && otherEvent.getEventStart().equals(getEventStart())
                && otherEvent.getEventType().equals(getEventType());
    }

    /**
     * Returns a boolean value to check if this event is after another event.
     *
     * @param otherEvent The other event to compare with.
     * @return boolean value depending if the current event is after the other event.
     */
    public boolean isAfterEvent(Event otherEvent) {
        return this.getEventStart().isAfter(otherEvent.getEventEnd());
    }

    @Override
    public String toString() {
        return getParentModule().getModuleCode().toString() + " | " + name.toString();
    }
}

