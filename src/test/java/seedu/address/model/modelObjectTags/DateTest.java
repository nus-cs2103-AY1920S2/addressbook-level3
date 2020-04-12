package seedu.address.model.modelObjectTags;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class DateTest {
    @Test
    public void isValidDate() {
        // null deadline
        assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // invalid deadline
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline("   ")); // spaces only
        assertFalse(Deadline.isValidDeadline("2020|01|30")); // wrong formatting of yyyy|mm|dd
        assertFalse(Deadline.isValidDeadline("20-13-30")); // wrong date where month > 12
        assertFalse(Deadline.isValidDeadline("20-01-32")); // wrong day where day > 31
        assertFalse(Deadline.isValidDeadline("2019-02-29")); // 29th feb on non leap year


        // valid deadlines
        assertTrue(Deadline.isValidDeadline("2020-01-30"));
        assertTrue(Deadline.isValidDeadline("2020-02-29")); // 29th feb on leap year
    }
}
