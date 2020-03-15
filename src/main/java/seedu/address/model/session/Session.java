package seedu.address.model.session;

import java.time.Duration;
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
    private SessionType type;
    private String description;

    /**
     * Constructs a Session object.
     * The session's end time should be strictly after the session's start time.
     */
    public Session(LocalDateTime start, LocalDateTime end, SessionType type,
                   String description) throws IllegalArgumentException {

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("[Session] Start time is set to after end time!");
        }

        this.startDateTime = start;
        this.endDateTime = end;
        this.type = type;
        this.description = description;
    }

    /**
     * Returns the start time of the session.
     */
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * Returns the end time of the session.
     */
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    /**
     * Returns the type of session.
     */
    public SessionType getSessionType() {
        return this.type;
    }

    /**
     * Returns the description of the session.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the duration of the session.
     */
    public Duration getSessionDuration() {
        return Duration.between(this.startDateTime, this.endDateTime);
    }
}
