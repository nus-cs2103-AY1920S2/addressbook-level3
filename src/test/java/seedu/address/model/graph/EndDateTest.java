package seedu.address.model.graph;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class EndDateTest {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final LocalDate DATE_NOW = LocalDate.now();


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EndDate(null));
    }

    @Test
    public void isValidEndDate() {
        // null birthday
        assertThrows(NullPointerException.class, () -> EndDate.isValidEndDate(null));

        // invalid birthdays
        assertFalse(EndDate.isValidEndDate("")); // empty string
        assertFalse(EndDate.isValidEndDate(" ")); // spaces only
        assertFalse(EndDate.isValidEndDate("91")); // random numbers
        assertFalse(EndDate.isValidEndDate("not a birthday")); // non-numeric
        assertFalse(EndDate.isValidEndDate("not a birthday1")); // non-numeric
        assertFalse(EndDate.isValidEndDate("not a birthday2")); // non-numeric
        assertFalse(EndDate.isValidEndDate("not a birthday3")); // non-numeric
        assertFalse(EndDate.isValidEndDate("not a birthday4")); // non-numeric
        assertFalse(EndDate.isValidEndDate("11-e33-1998")); // erroneous alphanumeric
        assertFalse(EndDate.isValidEndDate("11/12/1213")); // incorrect delimiter
        assertFalse(EndDate.isValidEndDate("11-13-1213")); // months more than 12
        assertFalse(EndDate.isValidEndDate("32-11-1213")); // dates more than 31
        assertFalse(EndDate.isValidEndDate("9-11-12131")); // years more than 4 digits
        assertFalse(EndDate.isValidEndDate(
            DATE_NOW.plusDays(1).format(DATE_TIME_FORMATTER))); // 1 day after the current day
        assertFalse(EndDate.isValidEndDate(
            DATE_NOW.minusYears(1).minusDays(1).format(DATE_TIME_FORMATTER))); // 1 year and 1 day before current date
        assertFalse(EndDate.isValidEndDate(
            DATE_NOW.minusYears(2).format(DATE_TIME_FORMATTER))); // 2 years before current date

        // valid date
        assertTrue(EndDate.isValidEndDate(
            DATE_NOW.format(DATE_TIME_FORMATTER))); // current date
        assertTrue(EndDate.isValidEndDate(
            DATE_NOW.minusMonths(4).format(DATE_TIME_FORMATTER))); // 4 months before current date
        assertTrue(EndDate.isValidEndDate(
            DATE_NOW.minusYears(1).format(DATE_TIME_FORMATTER))); // 1 year before current date

    }
}
