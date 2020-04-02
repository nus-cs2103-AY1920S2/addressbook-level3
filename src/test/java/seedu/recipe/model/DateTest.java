package seedu.recipe.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class DateTest {

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidBlankDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidBlankDate));

        String invalidCharacterDate = "2020!09/02";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidCharacterDate));

        String invalidFormatDate = "02/09/2020";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidFormatDate));

        String invalidMonthDate = "2020/35/01";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidMonthDate));

        String invalidDayDate = "2020/01/32";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDayDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // only spaces
        assertFalse(Date.isValidDate("!")); // only non-numeric or - character
        assertFalse(Date.isValidDate("20200202")); // not in yyyy-mm-dd format
        assertFalse(Date.isValidDate("2020-13-02")); // invalid month
        assertFalse(Date.isValidDate("2020-01-32")); // invalid day

        // valid date
        assertTrue(Date.isValidDate("1000-02-02")); // date in the past
        assertTrue(Date.isValidDate("2020-02-02")); // date in current year
        assertTrue(Date.isValidDate("5000-02-02")); // date in the future
    }

    @Test
    public void isDateInFuture() {
        Date olderDate = new Date("1000-01-01"); // date about 1000 years in the past
        assertFalse(olderDate.isDateInFuture());

        Date newerDate = new Date("5000-01-01"); // date about 3000 years in the future
        assertTrue(newerDate.isDateInFuture());
    }

    @Test
    public void isWithinRange() {
        Date pastDate = new Date("1000-01-01"); // date about 1000 years in the past
        Date recentDate = new Date("2000-01-01"); // date in this millennium
        Date futureDate = new Date("5000-01-01"); // date about 3000 years in the future

        // recent date is within start and end date
        assertTrue(recentDate.isWithinRange(pastDate, futureDate));

        // start date is later than end date
        assertFalse(recentDate.isWithinRange(futureDate, pastDate));

        // start and end date are the same
        assertFalse(recentDate.isWithinRange(pastDate, pastDate));
        assertFalse(recentDate.isWithinRange(futureDate, futureDate));
    }
}
