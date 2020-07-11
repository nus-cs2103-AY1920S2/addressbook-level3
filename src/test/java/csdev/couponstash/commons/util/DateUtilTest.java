package csdev.couponstash.commons.util;

import static csdev.couponstash.commons.util.DateUtil.MAXIMUM_YEAR;
import static csdev.couponstash.commons.util.DateUtil.MINIMUM_YEAR;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class DateUtilTest {

    @Test
    public void isValidDate_validInputs_correctResult() {
        // null Expiry Date
        assertThrows(NullPointerException.class, () -> DateUtil.isValidDate(null));

        // invalid expiry dates
        assertFalse(DateUtil.isValidDate("")); // empty string
        assertFalse(DateUtil.isValidDate(" ")); // spaces only
        assertFalse(DateUtil.isValidDate("1-1-11")); // only 2 numbers for yyyy
        assertFalse(DateUtil.isValidDate("date")); // non-numeric
        assertFalse(DateUtil.isValidDate("1 - 30 - 2020")); // spaces within digits
        assertFalse(DateUtil.isValidDate("30/2/2020")); // wrong date format
        assertFalse(DateUtil.isValidDate("30-2-2020")); // non-existent dates

        // valid expiry dates
        assertTrue(DateUtil.isValidDate("01-08-2020"));
        assertTrue(DateUtil.isValidDate("1-8-2020"));
    }

    @Test
    public void isValidYearMonth_validInputs_correctResult() {
        // null YearMonth
        assertThrows(NullPointerException.class, () -> DateUtil.isValidYearMonth(null));

        // invalid YearMonth
        assertFalse(DateUtil.isValidYearMonth("")); // empty string
        assertFalse(DateUtil.isValidYearMonth(" ")); // spaces only
        assertFalse(DateUtil.isValidYearMonth("1-11")); // only 2 numbers for yyyy
        assertFalse(DateUtil.isValidYearMonth("yearMonth")); // non-numeric
        assertFalse(DateUtil.isValidYearMonth(" 30 - 2020")); // spaces within digits
        assertFalse(DateUtil.isValidYearMonth("2/2020")); // wrong MonthYear format
        assertFalse(DateUtil.isValidYearMonth("13-2020")); // non-existent dates

        // valid YearMonth
        assertTrue(DateUtil.isValidYearMonth("08-2020"));
        assertTrue(DateUtil.isValidYearMonth("8-2020"));
    }

    @Test
    public void isValidYear() {
        // invalid Year
        assertFalse(DateUtil.isValidYear(11)); // only 2 numbers for yyyy
        assertFalse(DateUtil.isValidYear(MAXIMUM_YEAR + 1)); // More than maximum year
        assertFalse(DateUtil.isValidYear(MINIMUM_YEAR - 1)); // Less than minimum year


        // valid Year
        assertTrue(DateUtil.isValidYear(MAXIMUM_YEAR));
        assertTrue(DateUtil.isValidYear(MAXIMUM_YEAR - 1));
        assertTrue(DateUtil.isValidYear(MINIMUM_YEAR));
        assertTrue(DateUtil.isValidYear(MINIMUM_YEAR + 1));
    }

    @Test
    public void getYearMonthOfDate() {
        LocalDate date = LocalDate.of(2020, 8, 30);
        YearMonth actual = DateUtil.getYearMonthOfDate(date);
        YearMonth expected = YearMonth.of(2020, 8);
        assertEquals(actual, expected);
    }

    @Test
    public void formatDateToString() {
        LocalDate date = LocalDate.of(2020, 8, 30);
        String actual = DateUtil.formatDateToString(date);
        String expected = date.format(DateUtil.DATE_FORMATTER);
        assertEquals(actual, expected);
    }

    @Test
    public void formatYearMonthToString() {
        YearMonth ym = YearMonth.of(2020, 8);
        String actual = DateUtil.formatYearMonthToString(ym);
        String expected = ym.format(DateUtil.YEAR_MONTH_FORMATTER);
        assertEquals(actual, expected);
    }

    @Test
    public void formatDateStringToYearMonthString() {
        LocalDate date = LocalDate.of(2020, 8, 30);
        String dateString = date.format(DateUtil.DATE_FORMATTER);

        String actual = DateUtil.formatDateStringToYearMonthString(dateString);

        YearMonth ym = YearMonth.of(date.getYear(), date.getMonthValue());
        String expected = ym.format(DateUtil.YEAR_MONTH_FORMATTER);
        assertEquals(actual, expected);
    }

    @Test
    public void parseStringToDate() {
        String dateString = "30-8-2020";
        LocalDate actual = DateUtil.parseStringToDate(dateString);
        LocalDate expected = LocalDate.of(2020, 8, 30);
        assertEquals(actual, expected);
    }

    @Test
    public void parseStringToDate_invalidString_throwsDateTimeParseException() {
        String dateString = "8-30-2020";
        assertThrows(DateTimeParseException.class, () -> DateUtil.parseStringToDate(dateString));
    }
    @Test
    public void parseStringToYearMonth () {
        String ymString = "8-2020";
        YearMonth actual = DateUtil.parseStringToYearMonth(ymString);
        YearMonth expected = YearMonth.of(2020, 8);
        assertEquals(actual, expected);
    }

    @Test
    public void parseStringToYearMonth_invalidString_throwsDateTimeParseException() {
        String ymString = "30-2020";
        assertThrows(DateTimeParseException.class, () -> DateUtil.parseStringToDate(ymString));
    }

    @Test
    public void equals() {

    }
}
