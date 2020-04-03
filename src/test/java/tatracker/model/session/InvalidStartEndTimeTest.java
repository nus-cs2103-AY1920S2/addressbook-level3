package tatracker.model.session;

import static tatracker.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class InvalidStartEndTimeTest {

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        LocalDateTime startTime = LocalDateTime.of(2020, 01, 01, 14, 00, 00);
        LocalDateTime endTime = startTime.minus(1, ChronoUnit.HOURS);
        assertThrows(IllegalArgumentException.class, () -> new Session(startTime, endTime,
                SessionType.OTHER, 1, "CS2103/T", "Description"));
    }
}
