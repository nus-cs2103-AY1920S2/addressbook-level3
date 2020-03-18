package seedu.address.model.session;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a session in TAT.
 * A session is any claimable duty that has a start and end time.
 * Guarantees: Date, Start Time and End Time are not null.
 */
public class Session {

    /**
     * Represents a session type. Session types follows the same specifications as the TSS.
     * Example session types include: Tutorial, Grading, Consultation, etc.
     */
    public enum SessionType {
        SESSION_TYPE_TUTORIAL,
        SESSION_TYPE_LAB,
        SESSION_TYPE_CONSULTATION,
        SESSION_TYPE_GRADING,
        SESSION_TYPE_PREPARATION,
        SESSION_TYPE_OTHER
    }

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private boolean isRecurring;
    private String moduleCode;
    private SessionType type;
    private String description;

    /**
     * Default Constructor for Session.
     * Creates a session object with default values.
     */
    public Session() {
        this.startDateTime = LocalDateTime.now();
        this.endDateTime = LocalDateTime.now();
        this.isRecurring = false;
        this.moduleCode = "";
        this.type = SessionType.SESSION_TYPE_OTHER;
        this.description = "Default Session";
    }

    /**
     * Constructs a Session object.
     * The session's end time should be strictly after the session's start time.
     */
    public Session(LocalDateTime start, LocalDateTime end, SessionType type, boolean isRecurring, String moduleCode,
                   String description) throws IllegalArgumentException {

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("[Session] Start time is set to after end time!");
        }

        this.startDateTime = start;
        this.endDateTime = end;
        this.isRecurring = isRecurring;
        this.moduleCode = moduleCode;
        this.type = type;
        this.description = description;
    }

    /**
     * Returns true if both sessions of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two sessions.
     */
    public boolean isSameSession(Session s) {
        return false;
    }

    /**
     * Returns the date when the session will take place.
     */
    public LocalDate getDate() {
        return this.startDateTime.toLocalDate();
    }

    /**
     * Returns the start time of the session.
     */
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * Sets the start time of the session.
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Returns the end time of the session.
     */
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    /**
     * Sets the end time of the session.
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Returns true if session will recur every week; false otherwise.
     */
    public boolean getIsRecurring() {
        return this.isRecurring;
    }

    /**
     * Sets whether the session is a recurring session.
     */
    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    /**
     * Returns the module code associated with this session.
     */
    public String getModuleCode() {
        return this.moduleCode;
    }

    /**
     * Sets the module code associated with this session.
     */
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * Returns the type of session.
     */
    public SessionType getSessionType() {
        return this.type;
    }

    /**
     * Sets the type of session.
     */
    public void setType(SessionType type) {
        this.type = type;
    }

    /**
     * Returns the description of the session.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the session.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the duration of the session.
     */
    public Duration getSessionDuration() {
        return Duration.between(this.startDateTime, this.endDateTime);
    }

    /**
     * Returns true if both sessions have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Session)) {
            return false;
        }

        Session otherSession = (Session) other;
        return isSameSession(otherSession);
    }
}
