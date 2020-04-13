//@@author Chuayijing
package tatracker.testutil.sessions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tatracker.model.session.Session;

/**
 * A utility class containing a list of {@code Sessions} objects to be used in tests.
 */
public class TypicalSessions {

    public static final Session SESSION_1;
    public static final Session SESSION_2;
    public static final Session SESSION_3;
    public static final Session SESSION_4;

    static {
        SESSION_1 = new SessionBuilder()
                .withDate(LocalDate.of(2020, 5, 29))
                .withStartTime(LocalTime.of(13, 0))
                .withEndTime(LocalTime.of(13, 0))
                .withSessionType("lab")
                .withModule("CS3243")
                .withDescription("prepare notes")
                .withRecurring(2)
                .build();

        SESSION_2 = new SessionBuilder()
                .withDate(LocalDate.of(2020, 6, 10))
                .withStartTime(LocalTime.of(10, 0))
                .withEndTime(LocalTime.of(12, 0))
                .withSessionType("consultation")
                .withModule("CS2103T")
                .withDescription("with Bob")
                .withRecurring(0)
                .build();

        SESSION_3 = new SessionBuilder()
                .withDate(LocalDate.of(2020, 6, 1))
                .withStartTime(LocalTime.of(10, 0))
                .withEndTime(LocalTime.of(11, 0))
                .withSessionType("preparation")
                .withModule("CS2103T")
                .withDescription("Before next lesson")
                .withRecurring(1)
                .build();

        SESSION_4 = new SessionBuilder()
                .withDate(LocalDate.of(2020, 6, 15))
                .withStartTime(LocalTime.of(12, 30))
                .withEndTime(LocalTime.of(14, 0))
                .withSessionType("grading")
                .withModule("CS3243")
                .withDescription("Finish everything")
                .withRecurring(0)
                .build();
    }

    private TypicalSessions() {} // prevents instantiation

    public static List<Session> getTypicalSessions() {
        return new ArrayList<>(Arrays.asList(SESSION_1, SESSION_2, SESSION_3, SESSION_4));
    }
}
