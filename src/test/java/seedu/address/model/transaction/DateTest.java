package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import org.junit.jupiter.api.Test;

class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid dates
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("91")); // not a date
        assertFalse(Date.isValidDate("phone")); // not a date
        assertFalse(Date.isValidDate("2020-02-02")); // Wrong format
        assertFalse(Date.isValidDate("2020-02-31")); // No such date

        // valid date
        assertTrue(Date.isValidDate("02/02/2020"));

    }

    @Test
    public void inMonth() {
        Date date = new Date(LocalDate.parse("2020-02-02"));

        // correct year and month -> returns true
        assertTrue(date.inMonth(Month.FEBRUARY, Year.of(2020)));

        // wrong month -> returns false
        assertFalse(date.inMonth(Month.APRIL, Year.of(2020)));

        // wrong year -> returns false
        assertFalse(date.inMonth(Month.FEBRUARY, Year.of(2019)));
    }
}
