package seedu.address.testutil.event;

import java.time.LocalDateTime;

import seedu.address.model.event.Event;
import seedu.address.model.event.RecurrenceType;

/**
 * Utility class for making event objects
 */
public class EventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Event";
    public static final LocalDateTime DEFAULT_EVENT_START_TIME = LocalDateTime.now();
    public static final LocalDateTime DEFAULT_EVENT_END_TIME = LocalDateTime.now().plusHours(3);
    public static final String DEFAULT_COLOR_CODE = "group01";
    public static final String DEFAULT_UNIQUE_IDENTIFIER = "uniqueIdentifier" + LocalDateTime.now().toString();
    public static final RecurrenceType DEFAULT_RECUR_TYPE = RecurrenceType.NONE;

    private String eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String colorCode;
    private String uniqueIdentifer;
    private RecurrenceType recurrenceType;

    /**
     * Constructs the event builder
     */
    public EventBuilder() {
        eventName = DEFAULT_EVENT_NAME;
        startDateTime = DEFAULT_EVENT_START_TIME;
        endDateTime = DEFAULT_EVENT_END_TIME;
        colorCode = DEFAULT_COLOR_CODE;
        uniqueIdentifer = DEFAULT_UNIQUE_IDENTIFIER;
        recurrenceType = DEFAULT_RECUR_TYPE;
    }

    /**
     * Constructs the event builder with {@code eventName}
     */
    public EventBuilder(Event event) {
        eventName = event.getEventName();
        startDateTime = event.getStartDateTime();
        endDateTime = event.getEndDateTime();
        colorCode = event.getColorCode();
        uniqueIdentifer = event.getUniqueIdentifier();
        recurrenceType = event.getRecurrenceType();
    }

    /**
     * The builder sets {@code Event} with {@code eventName}
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    /**
     * The builder sets {@code Event} with {@code startDateTime}
     */
    public EventBuilder withStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }

    /**
     * The builder sets {@code Event} with {@code endDateTime}
     */
    public EventBuilder withEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
        return this;
    }

    /**
     * The builder sets {@code Event} with {@code colorCode}
     */
    public EventBuilder withColorCode(String colorCode) {
        this.colorCode = colorCode;
        return this;
    }

    /**
     * The builder sets {@code Event} with {@code uniqueIdentifier}
     */
    public EventBuilder withUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifer = uniqueIdentifier;
        return this;
    }

    /**
     * The builder sets {@code Event} with {@code recurrenceType}
     */
    public EventBuilder withRecurrenceType(RecurrenceType recurrenceType) {
        this.recurrenceType = recurrenceType;
        return this;
    }

    /**
     * Builds the event
     */
    public Event build() {
        return new Event(eventName, startDateTime, endDateTime, colorCode, uniqueIdentifer, recurrenceType);
    }
}
