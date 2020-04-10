package seedu.address.model.graph;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class StartDateTest {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final LocalDate DATE_NOW = LocalDate.now();


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StartDate(null));
    }

    @Test
    public void isValidStartDate() {
        // null birthday
        assertThrows(NullPointerException.class, () -> StartDate.isValidStartDate(null));

        // invalid birthdays
        assertFalse(StartDate.isValidStartDate("")); // empty string
        assertFalse(StartDate.isValidStartDate(" ")); // spaces only
        assertFalse(StartDate.isValidStartDate("91")); // random numbers
        assertFalse(StartDate.isValidStartDate("not a birthday")); // non-numeric
        assertFalse(StartDate.isValidStartDate("not a birthday1")); // non-numeric
        assertFalse(StartDate.isValidStartDate("not a birthday2")); // non-numeric
        assertFalse(StartDate.isValidStartDate("not a birthday3")); // non-numeric
        assertFalse(StartDate.isValidStartDate("not a birthday4")); // non-numeric
        assertFalse(StartDate.isValidStartDate("11-e33-1998")); // erroneous alphanumeric
        assertFalse(StartDate.isValidStartDate("11/12/1213")); // incorrect delimiter
        assertFalse(StartDate.isValidStartDate("11-13-1213")); // months more than 12
        assertFalse(StartDate.isValidStartDate("32-11-1213")); // dates more than 31
        assertFalse(StartDate.isValidStartDate("9-11-12131")); // years more than 4 digits
        assertFalse(StartDate.isValidStartDate(
            DATE_NOW.plusDays(1).format(DATE_TIME_FORMATTER))); // 1 day after the current day
        assertFalse(StartDate.isValidStartDate(
            DATE_NOW.minusYears(1).minusDays(1).format(DATE_TIME_FORMATTER))); // 1 year and 1 day before current date
        assertFalse(StartDate.isValidStartDate(
            DATE_NOW.minusYears(2).format(DATE_TIME_FORMATTER))); // 2 years before current date

        // valid date
        assertTrue(StartDate.isValidStartDate(
            DATE_NOW.format(DATE_TIME_FORMATTER))); // current date
        assertTrue(StartDate.isValidStartDate(
            DATE_NOW.minusMonths(4).format(DATE_TIME_FORMATTER))); // 4 months before current date
        assertTrue(StartDate.isValidStartDate(
            DATE_NOW.minusYears(1).format(DATE_TIME_FORMATTER))); // 1 year before current date

    }
}
