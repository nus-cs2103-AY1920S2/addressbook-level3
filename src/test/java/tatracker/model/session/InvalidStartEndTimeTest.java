package tatracker.model.session;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

import tatracker.testutil.Assert;

public class InvalidStartEndTimeTest {

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        LocalDateTime startTime = LocalDateTime.of(2020, 01, 01, 14, 00, 00);
        LocalDateTime endTime = startTime.minus(1, ChronoUnit.HOURS);
        Assert.assertThrows(IllegalArgumentException.class, () -> new Session(startTime, endTime,
                Session.SessionType.OTHER, false, "CS2103/T", "Description"));
    }
}
