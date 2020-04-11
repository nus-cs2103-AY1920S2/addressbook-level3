package seedu.recipe.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_DATE_FUTURE;
import static seedu.recipe.logic.commands.CommandTestUtil.VALID_DATE_PAST;
import static seedu.recipe.model.Date.today;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalDates.DATE_IN_FUTURE;
import static seedu.recipe.testutil.TypicalDates.DATE_IN_PAST;
import static seedu.recipe.testutil.TypicalDates.DATE_TODAY;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

public class DateTest {

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
    public void execute_isDateInFuture() {
        Date olderDate = new Date("1000-01-01"); // date about 1000 years in the past
        assertFalse(olderDate.isDateInFuture());

        Date newerDate = new Date("5000-01-01"); // date about 3000 years in the future
        assertTrue(newerDate.isDateInFuture());
    }

    @Test
    public void execute_isWithinRange() {
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

    @Test
    public void execute_today_isCorrect() {
        ZoneId defaultId = ZoneId.of("Asia/Singapore");
        Date expectedDate = new Date(LocalDate.now(defaultId));
        Date dateToday = today();
        assertEquals(dateToday, today());
    }

    @Test
    public void execute_isOnDate() {
        Date future = new Date(VALID_DATE_FUTURE);

        // same object -> returns true
        assertTrue(future.isOnDate(future));

        // same values -> returns true
        Date futureCopy = new Date(VALID_DATE_FUTURE);
        assertTrue(future.isOnDate(futureCopy));

        // different values -> returns false
        Date past = new Date(VALID_DATE_PAST);
        assertFalse(future.isOnDate(past));
    }

    @Test
    public void execute_isAfter() {
        assertTrue(DATE_TODAY.isAfter(DATE_IN_PAST));
        assertTrue(DATE_IN_FUTURE.isAfter(DATE_TODAY));

        assertFalse(DATE_TODAY.isAfter(DATE_IN_FUTURE));
        assertFalse(DATE_IN_PAST.isAfter(DATE_TODAY));
    }

    @Test
    public void equals() {
        Date future = new Date(VALID_DATE_FUTURE);

        // same object -> returns true
        assertTrue(future.isOnDate(future));

        // same values -> returns true
        Date futureCopy = new Date(VALID_DATE_FUTURE);
        assertTrue(future.isOnDate(futureCopy));

        // different values -> returns false
        Date past = new Date(VALID_DATE_PAST);
        assertFalse(future.isOnDate(past));
    }

}
