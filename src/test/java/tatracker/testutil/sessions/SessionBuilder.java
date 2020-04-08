package tatracker.testutil.sessions;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import tatracker.logic.parser.ParserUtil;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.Session;
import tatracker.model.session.SessionType;


/**
 * A utility class to help with building Session objects.
 */
public class SessionBuilder {

    private static final LocalDateTime DEFAULT_START = LocalDateTime.of(2020, 05, 20, 17, 30);
    private static final LocalDateTime DEFAULT_END =  LocalDateTime.of(2020, 05, 20, 19, 30);
    private static final String DEFAULT_MODULE = "CS2103T";
    private static final SessionType DEFAULT_TYPE = SessionType.TUTORIAL;
    private static final String DEFAULT_DESCRIPTION = "finishes his tutorial";
    private static final int DEFAULT_RECURRING = 2;
    private static final boolean DEFAULT_DONE = false;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String moduleCode;
    private SessionType sessionType;
    private String description;
    private int recurringWeeks;
    private boolean isDone;

    public SessionBuilder() {
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
        startTime = sessionToCopy.getStartDateTime();
        endTime = sessionToCopy.getEndDateTime();
        moduleCode = sessionToCopy.getModuleCode();
        sessionType = sessionToCopy.getSessionType();
        description = sessionToCopy.getDescription();
        recurringWeeks = sessionToCopy.getRecurring();
        isDone = sessionToCopy.getIsDone();
    }

    /**
     * Sets the {@code startTime} of the {@code Session} that we are building.
     */
    public SessionBuilder withStartTime(String startTime) throws ParseException {
        LocalDate date = ParserUtil.parseDate(startTime);
        LocalTime time = ParserUtil.parseTime(startTime);
        this.startTime = LocalDateTime.of(date, time);
        return this;
    }

    /**
     * Sets the {@code endTime} of the {@code Session} that we are building.
     */
    public SessionBuilder withEndTime(String endTime) throws ParseException {
        LocalDate date = ParserUtil.parseDate(endTime);
        LocalTime time = ParserUtil.parseTime(endTime);
        this.endTime = LocalDateTime.of(date, time);
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
    public SessionBuilder withSessionType(String type) throws ParseException {
        this.sessionType = ParserUtil.parseSessionType(type);
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

    public Session build() {
        return new Session(startTime, endTime, sessionType, recurringWeeks, moduleCode,
                description);
    }

}
