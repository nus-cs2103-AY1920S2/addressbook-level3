package seedu.address.model.session;

import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class InvalidStartEndTimeTest {

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        LocalTime startTime = LocalTime.of(14, 00, 00);
        LocalTime endTime = startTime.minus(1, ChronoUnit.HOURS);
        LocalDate startDate = LocalDate.of(2020, 01, 01);
        assertThrows(IllegalArgumentException.class, () -> new Session(startTime, endTime, startDate,
                Session.SessionType.SESSION_TYPE_OTHER, ""));
    }
}
