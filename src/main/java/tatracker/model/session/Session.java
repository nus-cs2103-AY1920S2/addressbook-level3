package tatracker.model.session;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a session in TAT.
 * A session is any claimable duty that has a start and end time.
 * Guarantees: Date, Start Time and End Time are not null.
 */
public class Session implements Comparable<Session> {

    public static final String CONSTRAINTS_RECURRING_WEEKS = "Recurring weeks must be an unsigned number";

    /** For converting date times to strings. Example: "2020-03-03 14:00" */
    private static final DateTimeFormatter FORMAT_DATE_TIME = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mma");

    /** For formatting sessions with minimal notation. */
    private static final String FORMAT_MIN_DESCRIPTION = "%s (%s)\nStart: %s\nEnd: %s";

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String moduleCode;
    private SessionType type;
    private String description;
    private int recurring;
    private boolean isDone;

    /**
     * Default Constructor for Session.
     * Creates a session object with default values.
     */
    public Session() {
        this.startDateTime = LocalDateTime.now();
        this.endDateTime = LocalDateTime.now();
        this.recurring = 0;
        this.moduleCode = "";
        this.type = SessionType.OTHER;
        this.description = "Default Session";
        this.isDone = false;
    }

    /**
     * Constructs a Session object.
     * The session's end time should be strictly after the session's start time.
     */
    public Session(LocalDateTime start, LocalDateTime end, SessionType type, int recurring, String moduleCode,
                   String description) throws IllegalArgumentException {

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("The start time of a session cannot be after the end time!");
        }

        this.startDateTime = start;
        this.endDateTime = end;
        this.recurring = recurring;
        this.moduleCode = moduleCode;
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns true if both sessions have the same date, timing, module, and type.
     * This defines a weaker notion of equality between two sessions.
     */
    public boolean isSameSession(Session s) {
        return startDateTime.equals(s.startDateTime)
                && endDateTime.equals(s.endDateTime)
                && moduleCode.equals(s.moduleCode)
                && type.equals(s.type);
    }

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
     * Returns a value that states how long a session will occur. weekly basis.
     */
    public int getRecurring() {
        return this.recurring;
    }

    /**
     * Returns true if session is already completed; false otherwise.
     */
    public boolean getIsDone() {
        return this.isDone;
    }

    /**
     * Marks the session as done.
     */
    public void done() {
        this.isDone = true;
    }

    /**
     * Sets whether the session's recurring value.
     */
    public void setRecurring(int recurring) {
        this.recurring = recurring;
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
     * Returns the duration of the session to the nearest hour.
     */
    public Duration getDurationToNearestHour() {
        Duration duration = Duration.between(this.startDateTime, this.endDateTime);

        long hours = duration.toHours();
        int minutesPart = duration.toMinutesPart();

        if (minutesPart > 0) {
            hours += 1;
        }
        return Duration.ofHours(hours);
    }

    public String getStartDateTimeDescription() {
        return startDateTime.format(FORMAT_DATE_TIME);
    }

    public String getEndDateTimeDescription() {
        return endDateTime.format(FORMAT_DATE_TIME);
    }

    public String getMinimalDescription() {
        return String.format(FORMAT_MIN_DESCRIPTION, type, moduleCode,
                getStartDateTimeDescription(), getEndDateTimeDescription());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Type: ").append(type)
                .append(" Start: ").append(getStartDateTimeDescription())
                .append(" End: ").append(getEndDateTimeDescription())
                .append(" Module Code: ").append(moduleCode)
                .append(" Description: ").append(description)
                .append(" Recurs: ").append(recurring);
        return builder.toString();
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

        return isSameSession(otherSession)
                && description.equals(otherSession.description)
                && recurring == otherSession.recurring
                && isDone == otherSession.isDone;
    }

    /**
     * Compare Sessions based on the session that will occur first.
     */
    @Override
    public int compareTo(Session other) {
        return getDate().compareTo(other.getDate());
    }
}
