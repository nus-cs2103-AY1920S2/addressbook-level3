package seedu.address.model.calender;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    private static final String VALID_DATE = "22-04-2020";

    @Test
    public void isValidDate() {

        // invalid date
        assertFalse(Deadline.isValidDate("2020-04-20")); //YYYY-MM-DD
        assertFalse(Deadline.isValidDate("2020-20-04")); //YYYY-DD-MM
        assertFalse(Deadline.isValidDate("04-20-2020")); //MM-DD-YYYY
        assertFalse(Deadline.isValidDate("not a date")); // not a date

        // valid date
        assertTrue(Deadline.isValidDate(VALID_DATE)); // Correct date format DD-MM-YYYY
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(1, null));
    }

    @Test
    public void constructor2_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline("test", "asd", "cat", null));
    }

}
