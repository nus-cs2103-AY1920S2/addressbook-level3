package seedu.address.model.transaction;

import org.junit.jupiter.api.Test;
import seedu.address.model.util.Description;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class DateTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DateTime((String) null));
    }

    @Test
    public void constructor_invalidDateTimeString_throwsArgumentException() {
        //invalid format.
        assertThrows(IllegalArgumentException.class, () -> new DateTime(""));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2012"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("12345"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-02"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-0210:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-02 10"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("02-02-2020 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020/02/02 10:00"));

        //invalid month value.
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-13-02 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-00-02 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-20-02 10:00"));

        //invalid date value.
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2019-02-29 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-02-30 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-04-31 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-03-32 10:00"));
        assertThrows(IllegalArgumentException.class, () -> new DateTime("2020-03-00 10:00"));
    }
}
