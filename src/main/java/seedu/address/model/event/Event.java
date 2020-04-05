package seedu.address.model.event;

import static seedu.address.commons.core.Messages.MESSAGE_MISSING_EVENT_NAME;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.EventUtil.validateDateTime;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an event of the scheduler
 */
public class Event {
    public static final String COLOR_CATEGORY_VALIDATION_REGEX = "group[0-2][0-9]";
    public static final String INVALID_COLOR_CATEGORY = "Invalid color category.";

    private String eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String colorCode;
    private String uniqueIdentifier;
    private RecurrenceType recurrenceType;

    /**
     * Event constructor
     * @param eventName represents the eventName
     * @param startDateTime represents the startDateTime
     * @param endDateTime represents the endDateTime
     * @param colorCode represents the color code of the event
     * @param uniqueIdentifier represents the uniqueid
     * @param recurrenceType represents the recurrence type daily none or weekly
     */
    public Event(String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime, String colorCode,
                 String uniqueIdentifier, RecurrenceType recurrenceType) {
        requireAllNonNull(eventName, startDateTime, endDateTime, colorCode, uniqueIdentifier, recurrenceType);
        checkArgument(validateDateTime(startDateTime, endDateTime));
        checkArgument(isValidEventName(eventName), MESSAGE_MISSING_EVENT_NAME);
        checkArgument(isValidColorCode(colorCode), INVALID_COLOR_CATEGORY);
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.colorCode = colorCode;
        this.uniqueIdentifier = uniqueIdentifier;
        this.recurrenceType = recurrenceType;
    }

    public Event() {

    }

    /**
     * Validates the event name
     */
    public boolean isValidEventName(String eventName) {
        if (eventName.isBlank()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates the color codesss
     */
    public boolean isValidColorCode(String colorCode) {
        if (!colorCode.matches(COLOR_CATEGORY_VALIDATION_REGEX)) {
            return false;
        } else {
            return true;
        }
    }

    public RecurrenceType getRecurrenceType() {
        return recurrenceType;
    }

    public void setRecurrenceType(RecurrenceType recurrenceType) {
        this.recurrenceType = recurrenceType;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getEventName() {
        return eventName;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setStartTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    public void setEndTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
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
        return otherEvent.getEventName().equals(getEventName())
                && otherEvent.getUniqueIdentifier().equals(getUniqueIdentifier())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime())
                && otherEvent.getColorCode().equals(getColorCode())
                && otherEvent.getRecurrenceType().equals(getRecurrenceType());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, startDateTime, endDateTime, colorCode, uniqueIdentifier, recurrenceType);
    }
}
