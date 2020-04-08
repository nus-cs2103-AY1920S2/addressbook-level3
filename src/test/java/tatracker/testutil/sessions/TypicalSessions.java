package tatracker.testutil.sessions;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.session.Session;
import tatracker.model.student.Student;

/**
 * A utility class containing a list of {@code Sessions} objects to be used in tests.
 */
public class TypicalSessions {

    public static final Session S1;

    static {
        SessionBuilder sessionBuilder = new SessionBuilder();
        try {
            sessionBuilder.withStartTime("2020-05-29T17:00");
            sessionBuilder.withEndTime("2020-05-29T17:00");
            sessionBuilder.withSessionType("lab");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sessionBuilder.withModule("CS3243");
        sessionBuilder.withDescription("prepare notes");
        sessionBuilder.withRecurring(2);
        S1 = sessionBuilder
                    .build();
    }

    public static final Session S2;

    static {
        SessionBuilder sessionBuilder = new SessionBuilder();
        try {
            sessionBuilder.withStartTime("2020-06-10T12:00");
            sessionBuilder.withEndTime("2020-06-10T14:00");
            sessionBuilder.withSessionType("consultation");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sessionBuilder.withModule("CS2103T");
        sessionBuilder.withDescription("with Bob");
        sessionBuilder.withRecurring(0);
        S2 = sessionBuilder
                    .build();
    }

    public static final Session S3;

    static {
        SessionBuilder sessionBuilder = new SessionBuilder();
        try {
            sessionBuilder.withStartTime("2020-06-01T10:00");
            sessionBuilder.withEndTime("2020-06-01T11:00");
            sessionBuilder.withSessionType("Preparation");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sessionBuilder.withModule("CS2103T");
        sessionBuilder.withDescription("Before next lesson");
        sessionBuilder.withRecurring(1);
        S3 = sessionBuilder
                    .build();
    }

    public static final Session S4;

    static {
        SessionBuilder sessionBuilder = new SessionBuilder();
        try {
            sessionBuilder.withStartTime("2020-06-15T12:00");
            sessionBuilder.withEndTime("2020-06-15T14:00");
            sessionBuilder.withSessionType("gradings");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sessionBuilder.withModule("CS3243");
        sessionBuilder.withDescription("Finish everything");
        sessionBuilder.withRecurring(0);
        S4 = sessionBuilder
                    .build();
    }

    private TypicalSessions() {} // prevents instantiation

    public static List<Session> getTypicalSessions() {
        return new ArrayList<>(Arrays.asList(S1, S2, S3, S4));
    }
}
