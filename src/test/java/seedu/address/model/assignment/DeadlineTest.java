package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDate = "2020-31-05 23:59";
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDate));

        String invalidTime = "2020-05-31 25:59";
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidTime));
    }

    @Test
    public void isValidDeadline() {
        // null deadline
        assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // invalid time
        assertFalse(Deadline.isValidDeadline("2020-03-31 24:59")); // Time does not exist

        // invalid date
        assertFalse(Deadline.isValidDeadline("2020-13-05 23:59")); // Date does not exist

        // invalid date
        assertFalse(Deadline.isValidDeadline("2020-31-03 23:59")); // Wrong date format

        // invalid time
        assertFalse(Deadline.isValidDeadline("2020-03-31 23-59")); // Wrong time format

        // valid date and time
        assertTrue(Deadline.isValidDeadline("2020-03-31 23:59"));
    }

    @Test
    public void hasDeadlinePassed() {
        // Deadline has not passed yet
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Singapore")).plusHours(2);
        assertFalse(Deadline.hasDeadlinePassed(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

        // Deadline is already over
        dateTime = LocalDateTime.now(ZoneId.of("Singapore")).minusHours(5);
        assertTrue(Deadline.hasDeadlinePassed(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }
}
