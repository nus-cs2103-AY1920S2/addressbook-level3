package seedu.zerotoone.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DateTimeTest {

    @Test
    void isValidDateTime() {
        assertTrue(DateTime.isValidDateTime("2015-08-12 12:24"));

        assertFalse(DateTime.isValidDateTime("2020-02-02 2200"));
        assertFalse(DateTime.isValidDateTime("2020-02-31 12:00"));
    }
}
