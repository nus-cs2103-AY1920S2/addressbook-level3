package modulo.testutil.event;

import static modulo.testutil.module.TypicalModules.CS2103;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import modulo.model.Name;
import modulo.model.event.Event;
import modulo.model.event.EventType;
import modulo.model.event.Location;
import modulo.model.event.Slot;
import modulo.model.module.Module;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static final String DEFAULT_EVENT_NAME = "Tutorial 1";
    public static final EventType DEFAULT_EVENT_TYPE = EventType.TUTORIAL;
    public static final LocalDateTime DEFAULT_EVENT_START = LocalDateTime.parse("2020-01-15 09:00", DATETIME_FORMAT);
    public static final LocalDateTime DEFAULT_EVENT_END = LocalDateTime.parse("2020-01-15 10:00", DATETIME_FORMAT);
    public static final Module DEFAULT_MODULE = CS2103;
    public static final String DEFAULT_MODULE_VENUE = "COM1-B103";
    public static final String DEFAULT_SLOT = "T04";

    private Name name;
    private EventType eventType;
    private LocalDateTime eventStart;
    private LocalDateTime eventEnd;
    private Module parentModule;
    private Location location;
    private Slot slot;

    public EventBuilder() {
        name = new Name(DEFAULT_EVENT_NAME);
        eventType = DEFAULT_EVENT_TYPE;
        eventStart = DEFAULT_EVENT_START;
        eventEnd = DEFAULT_EVENT_END;
        parentModule = DEFAULT_MODULE;
        location = new Location(DEFAULT_MODULE_VENUE);
        slot = new Slot(DEFAULT_SLOT);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        name = eventToCopy.getName();
        eventType = eventToCopy.getEventType();
        eventStart = eventToCopy.getEventStart();
        eventEnd = eventToCopy.getEventEnd();
        parentModule = eventToCopy.getParentModule();
        location = eventToCopy.getLocation();
        slot = eventToCopy.getSlot();
    }

    /**
     * Sets the {@code Name} of the event that we are building.
     */
    public EventBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code EventType} of the event that we are building.
     */
    public EventBuilder withEventType(String eventType) {
        this.eventType = EventType.parseEventType(eventType);
        return this;
    }

    /**
     * Sets the {@code eventStart} of the event that we are building.
     */
    public EventBuilder withEventStart(String eventStart) {
        this.eventStart = LocalDateTime.parse(eventStart, DATETIME_FORMAT);
        return this;
    }

    /**
     * Sets the {@code eventEnd} of the person that we are building.
     */
    public EventBuilder withEventEnd(String eventEnd) {
        this.eventEnd = LocalDateTime.parse(eventEnd, DATETIME_FORMAT);
        return this;
    }

    /**
     * Sets the {@code Location} of the event that we are building.
     */
    public EventBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }


    /**
     * Sets the {@code parentModule} of the event that we are building.
     */
    public EventBuilder withParentModule(Module parentModule) {
        this.parentModule = parentModule;
        return this;
    }

    /**
     * Sets the {@code slot} of the event that we are building.
     */
    public EventBuilder withSlot(String slot) {
        this.slot = new Slot(slot);
        return this;
    }

    public Event build() {
        return new Event(name, eventType, eventStart, eventEnd, parentModule, location, slot);
    }
}
