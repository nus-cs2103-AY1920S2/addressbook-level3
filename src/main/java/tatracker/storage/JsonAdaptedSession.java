//@@author potatocombat

package tatracker.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tatracker.commons.exceptions.IllegalValueException;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;

/**
 * Jackson-friendly version of {@link Session}.
 */
class JsonAdaptedSession {
    public static final String MESSAGE_INVALID_TIMING = "Session's timing is invalid"
            + " because the end date time is before its start date time";

    private static final String MESSAGE_INVALID_DATE_TIME = "\nDate Times should"
            + " start with a date in yyyy-MM-dd format,"
            + " followed by the letter T,"
            + " then the time in HH:mm format.";

    public static final String MESSAGE_INVALID_START_DATE_TIME = "Session's start date time is invalid."
            + MESSAGE_INVALID_DATE_TIME;
    public static final String MESSAGE_INVALID_END_DATE_TIME = "Session's end date time is invalid."
            + MESSAGE_INVALID_DATE_TIME;

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Session's %s field is missing!";
    public static final String MISSING_START_DATE_TIME = String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date time");
    public static final String MISSING_END_DATE_TIME = String.format(MISSING_FIELD_MESSAGE_FORMAT, "end date time");
    public static final String MISSING_SESSION_TYPE = String.format(MISSING_FIELD_MESSAGE_FORMAT, "type");
    public static final String MISSING_DESCRIPTION = String.format(MISSING_FIELD_MESSAGE_FORMAT, "description");
    public static final String MISSING_MODULE_ID = String.format(MISSING_FIELD_MESSAGE_FORMAT, "module");

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
            throw new IllegalValueException(MISSING_START_DATE_TIME);
        }

        final LocalDateTime modelStartDateTime;
        try {
            modelStartDateTime = LocalDateTime.parse(startDateTime);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_INVALID_START_DATE_TIME);
        }

        // ==== End Date Time ====
        if (endDateTime == null) {
            throw new IllegalValueException(MISSING_END_DATE_TIME);
        }

        final LocalDateTime modelEndDateTime;
        try {
            modelEndDateTime = LocalDateTime.parse(endDateTime);
        } catch (DateTimeParseException dtpe) {
            throw new IllegalValueException(MESSAGE_INVALID_END_DATE_TIME);
        }

        // ==== Time Constraints ====
        if (modelEndDateTime.isBefore(modelStartDateTime)) {
            throw new IllegalValueException(MESSAGE_INVALID_TIMING);
        }

        // ==== Type ====
        if (type == null) {
            throw new IllegalValueException(MISSING_SESSION_TYPE);
        }
        if (!SessionType.isValidSessionType(type)) {
            throw new IllegalValueException(SessionType.MESSAGE_CONSTRAINTS);
        }
        final SessionType modelSessionType = SessionType.getSessionType(type);

        // ==== Description ====
        if (description == null) {
            throw new IllegalValueException(MISSING_DESCRIPTION);
        }

        // ==== Module Id ====
        if (moduleId == null) {
            throw new IllegalValueException(MISSING_MODULE_ID);
        }
        if (moduleId.isBlank()) {
            throw new IllegalValueException(Module.CONSTRAINTS_MODULE_CODE);
        }

        // ==== Module Id ====
        if (recurring < 0) {
            throw new IllegalValueException(Session.CONSTRAINTS_RECURRING_WEEKS);
        }

        Session session = new Session(modelStartDateTime, modelEndDateTime, modelSessionType,
                recurring, moduleId, description);

        if (isDone) {
            session.done();
        }
        return session;
    }
}
