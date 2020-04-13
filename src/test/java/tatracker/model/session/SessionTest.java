package tatracker.model.session;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import tatracker.testutil.sessions.SessionBuilder;

class SessionTest {

    //@@author Eclmist
    // @Test
    // public void constructor_invalidTime_throwsIllegalArgumentException() {
    //     LocalDateTime startTime = LocalDateTime.of(2020, 1, 1, 14, 0, 0);
    //     LocalDateTime endTime = startTime.minus(1, ChronoUnit.HOURS);
    //     assertThrows(IllegalArgumentException.class, () -> new Session(startTime, endTime,
    //             SessionType.OTHER, 1, "CS2103/T", "Description"));
    // }

    //@@author potatocombat
    @Test
    public void hasTimingClash() {
        Session session1 = new SessionBuilder()
                .withStartTime(LocalTime.of(14, 0))
                .withEndTime(LocalTime.of(15, 0))
                .build();

        Session session2 = new SessionBuilder()
                .withStartTime(LocalTime.of(14, 0))
                .withEndTime(LocalTime.of(15, 0))
                .build();

        assertTrue(session1.hasTimingClash(session2));
    }
}
