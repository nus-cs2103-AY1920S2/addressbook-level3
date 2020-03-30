package tatracker.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;

/**
 * Jackson-friendly version of {@link Session}.
 */
class JsonAdaptedSession {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Session's %s field is missing!";

    private static final String MESSAGE_INVALID_DATE = "Dates should be in yyyy-MM-dd format";
    private static final String MESSAGE_INVALID_TIME = "Times should be in HH:mm format";
    private static final String MESSAGE_INVALID_DATE_TIME =
            "Session's %s date time is invalid!\n"
            + MESSAGE_INVALID_DATE + "\n"
            + MESSAGE_INVALID_TIME;

    private String startDateTime;
    private String endDateTime;
    private String type;
    private String description;
    private String moduleId;
    private boolean isDone;
    private int recurring;

    /**
     * Constructs a {@code JsonAdaptedModule} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedSession(@JsonProperty("start") String startDateTime,
                              @JsonProperty("end") String endDateTime,
                              @JsonProperty("type") String type,
                              @JsonProperty("description") String description,
                              @JsonProperty("moduleId") String moduleId,
                              @JsonProperty("isDone") boolean isDone,
                              @JsonProperty("recurring") int recurring) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;

        this.type = type;
        this.description = description;
        this.moduleId = moduleId;

        this.isDone = isDone;
        this.recurring = recurring;
    }

    /**
     * Converts a given {@code Module} into this class for Jackson use.
     */
    public JsonAdaptedSession(Session source) {
        startDateTime = source.getStartDateTime().toString();
        endDateTime = source.getEndDateTime().toString();

        type = source.getSessionType().name();
        description = source.getDescription();
        moduleId = source.getModuleCode();

        isDone = source.getIsDone();
        recurring = source.getRecurring();
    }

    /**
     * Converts this Jackson-friendly adapted module object into the model's {@code Module} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted module.
     */
    public Session toModelType() throws IllegalValueException {
        // ==== Start Date Time ====
        if (startDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date time"));
        }

        final LocalDateTime modelStartDateTime;
        try {
            modelStartDateTime = LocalDateTime.parse(startDateTime);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_DATE_TIME, "start"));
        }

        // ==== End Date Time ====
        if (endDateTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end date time"));
        }

        final LocalDateTime modelEndDateTime;
        try {
            modelEndDateTime = LocalDateTime.parse(endDateTime);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_DATE_TIME, "end"));
        }

        // ==== Type ====
        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Session type"));
        }
        final SessionType modelSessionType = SessionType.valueOf(type);

        // ==== Description ====
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "description"));
        }

        // ==== Module Id ====
        if (moduleId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Module id"));
        }

        Session session = new Session(modelStartDateTime, modelEndDateTime, modelSessionType,
                recurring, moduleId, description);

        if (isDone) {
            session.done();
        }

        return session;
    }

}
