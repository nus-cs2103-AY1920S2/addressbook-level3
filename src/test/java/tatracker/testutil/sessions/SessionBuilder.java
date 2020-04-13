//@@author chuayijing
package tatracker.testutil.sessions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import tatracker.model.session.Session;
import tatracker.model.session.SessionType;


/**
 * A utility class to help with building Session objects.
 */
public class SessionBuilder {

    public static final LocalDate DEFAULT_DATE = LocalDate.of(2020, 5, 20);
    public static final LocalTime DEFAULT_START = LocalTime.of(17, 30);
    public static final LocalTime DEFAULT_END = LocalTime.of(19, 30);
    public static final String DEFAULT_MODULE = "CS2103T";
    public static final SessionType DEFAULT_TYPE = SessionType.TUTORIAL;
    public static final String DEFAULT_DESCRIPTION = "finishes his tutorial";
    public static final int DEFAULT_RECURRING = 2;
    public static final boolean DEFAULT_DONE = false;

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String moduleCode;
    private SessionType sessionType;
    private String description;
    private int recurringWeeks;
    private boolean isDone;

    public SessionBuilder() {
        date = DEFAULT_DATE;
        startTime = DEFAULT_START;
        endTime = DEFAULT_END;
        moduleCode = DEFAULT_MODULE;
        sessionType = DEFAULT_TYPE;
        description = DEFAULT_DESCRIPTION;
        recurringWeeks = DEFAULT_RECURRING;
        isDone = DEFAULT_DONE;
    }

    /**
     * Initializes the SessionBuilder with the data of {@code sessionToCopy}.
     */
    public SessionBuilder(Session sessionToCopy) {
        date = sessionToCopy.getDate();
        startTime = sessionToCopy.getStartDateTime().toLocalTime();
        endTime = sessionToCopy.getEndDateTime().toLocalTime();
        moduleCode = sessionToCopy.getModuleCode();
        sessionType = sessionToCopy.getSessionType();
        description = sessionToCopy.getDescription();
        recurringWeeks = sessionToCopy.getRecurring();
        isDone = sessionToCopy.getIsDone();
    }

    /**
     * Sets the {@code date} of the {@code Session} that we are building.
     */
    public SessionBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    /**
     * Sets the {@code startTime} of the {@code Session} that we are building.
     */
    public SessionBuilder withStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Session} that we are building.
     */
    public SessionBuilder withEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Sets the {@code ModuleCode} of the {@code Session} that we are building.
     */
    public SessionBuilder withModule(String module) {
        this.moduleCode = module;
        return this;
    }

    /**
     * Sets the {@code SessionType} of the {@code Session} that we are building.
     */
    public SessionBuilder withSessionType(String type) {
        this.sessionType = SessionType.getSessionType(type);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code Session} that we are building.
     */
    public SessionBuilder withDescription(String d) {
        this.description = d;
        return this;
    }

    /**
     * Sets the {@code isDone} of the {@code Session} that we are building.
     */
    public SessionBuilder withDone(boolean done) {
        this.isDone = done;
        return this;
    }

    /**
     * Sets the {@code Recurring Week} of the {@code Session} that we are building.
     */
    public SessionBuilder withRecurring(int weeks) {
        this.recurringWeeks = weeks;
        return this;
    }

    //@@author PotatoCombat
    /**
     * Builds the Session.
     */
    public Session build() {
        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

        return new Session(startDateTime, endDateTime, sessionType, recurringWeeks, moduleCode, description);
    }
}
