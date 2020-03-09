package seedu.address.model.session;

import java.time.LocalDate;
import java.time.LocalTime;

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
        SESSION_TYPE_INVALID
    }

    private SessionType sessionType;

    /**
     * Returns the date when the session will take place.
     */
    public LocalDate getDate() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Returns the start time of the session.
     */
    public LocalTime getStartTime() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Returns the end time of the session.
     */
    public LocalTime getEndTime() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Returns the type of session.
     */
    public SessionType getSessionType() {
        return sessionType;
    }
}
