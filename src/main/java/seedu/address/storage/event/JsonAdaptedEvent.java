package seedu.address.storage.event;

import static seedu.address.commons.util.EventUtil.validateDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.RecurrenceType;


/**
 * Jackson-friendly version of {@link seedu.address.model.event.Event}.
 */
class JsonAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "event's %s field is missing!";
    public static final String INVALID_DATE_FORMAT = "Invalid date format!";
    public static final String INVALID_DATE_RANGE = "Invalid date range! Start date must be before end date";
    public static final String INVALID_RECUR_TYPE = "Invalid recurrence type!";

    private String eventName;
    private String startDateTime;
    private String endDateTime;
    private String uniqueIdentifier;
    private String recurrenceType;
    private String colorCode;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventName") String eventName,
                            @JsonProperty("startDateTime") String startDateTime,
                            @JsonProperty("endDateTime") String endDateTime,
                            @JsonProperty("uniqueIdentifier") String uniqueIdentifier,
                            @JsonProperty("recurrenceType") String recurrenceType,
                            @JsonProperty("colorCode") String colorCode) {

        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.uniqueIdentifier = uniqueIdentifier;
        this.recurrenceType = recurrenceType;
        this.colorCode = colorCode;
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        this.eventName = source.getEventName();
        this.startDateTime = source.getStartDateTime().toString();
        this.endDateTime = source.getEndDateTime().toString();
        this.uniqueIdentifier = source.getUniqueIdentifier();
        this.recurrenceType = source.getRecurrenceType().name();
        this.colorCode = source.getColorCode();
    }

    /**
     * Converts this Jackson-friendly adapted event object into the model's {@code Event} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event.
     */
    public Event toModelType() throws IllegalValueException {
        if (eventName.isEmpty() || eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "EVENT NAME"));
        }

        if (startDateTime.isEmpty() || startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "START DATE TIME"));
        }

        if (endDateTime.isEmpty() || endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "END DATE TIME"));
        }

        if (uniqueIdentifier.isEmpty() || uniqueIdentifier == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "UNIQUE IDENTIFIER"));
        }

        if (colorCode.isEmpty() || colorCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "COLOR CODE"));
        }

        RecurrenceType recurrenceTypeToAdd;
        if (recurrenceType.isEmpty() || recurrenceType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "RECURRENCE TYPE"));
        } else if (recurrenceType.equalsIgnoreCase(RecurrenceType.NONE.name())) {
            recurrenceTypeToAdd = RecurrenceType.NONE;
        } else if (recurrenceType.equalsIgnoreCase(RecurrenceType.DAILY.name())) {
            recurrenceTypeToAdd = RecurrenceType.DAILY;
        } else if (recurrenceType.equalsIgnoreCase(RecurrenceType.WEEKLY.name())) {
            recurrenceTypeToAdd = RecurrenceType.WEEKLY;
        } else {
            throw new IllegalValueException(INVALID_RECUR_TYPE);
        }
        if (colorCode.isEmpty() || colorCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "COLOR CODE"));
        }

        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            startDateTime = LocalDateTime.parse(this.startDateTime);
            endDateTime = LocalDateTime.parse(this.endDateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(INVALID_DATE_FORMAT);
        }
        if (!validateDateTime(startDateTime, endDateTime)) {
            throw new IllegalValueException(INVALID_DATE_RANGE);
        }

        return new Event(eventName, startDateTime, endDateTime, colorCode, uniqueIdentifier, recurrenceTypeToAdd);

    }

}
