package seedu.address.model.session;

import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class InvalidStartEndTimeTest {

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Session(LocalTime.now(),
                LocalTime.now().minus(1, ChronoUnit.HOURS), LocalDate.now(),
                Session.SessionType.SESSION_TYPE_OTHER, ""));
    }
}
