package csdev.couponstash.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * This class stores a common DateTimeFormatter to be
 * used on all dates in Coupon Stash. Ensures that all
 * dates have a consistent format that can be changed
 * easily if desired.
 */

public class DateUtil {
    // The standard DateTimeFormatter describing the consistent Coupon Stash Date Format
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-M-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final String MESSAGE_DATE_WRONG_FORMAT = "Date is in the"
            + " wrong format! Should be a valid date in the D-M-YYYY format";

    public static final String DATE_VALIDATION_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4}";


    public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("M-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final String MESSAGE_YEAR_MONTH_WRONG_FORMAT = "Year-Month is in the"
            + " wrong format! Should be a valid month-year the M-YYYY format";

    public static final String MONTH_YEAR_VALIDATION_REGEX = "\\d{1,2}-\\d{4}";

    public static final String YEAR_VALIDATION_REGEX = "\\d{4}";

    // for Calendar (and SavedCommand)
    public static final DateTimeFormatter DAY_MONTH_YEAR_FORMATTER_FOR_CALENDAR = DateTimeFormatter
            .ofPattern("d LLLL yyyy");
    public static final DateTimeFormatter MONTH_YEAR_FORMATTER_FOR_CALENDAR = DateTimeFormatter.ofPattern("LLLL yyyy");
    public static final DateTimeFormatter DAY_SHORT_MONTH_YEAR_FORMATTER = DateTimeFormatter
            .ofPattern("d LLL yyyy");

    public static final int MINIMUM_YEAR = 2000;
    public static final int MAXIMUM_YEAR = 2200;

    // for expiry & start date
    public static final DateTimeFormatter DATE_FORMATTER_STANDARD = DateTimeFormatter.ofPattern("dd-MM-uuuu");

    public static final String MESSAGE_START_DATE_EXPIRY_DATE_CONSTRAINT = "Start Date must be before Expiry Date!";
    public static final String MESSAGE_REMIND_DATE_EXCEED_EXPIRY_DATE = "Reminder date cannot be after Expiry Date!";

    /**
     * Returns true if a given string is a valid Date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            return false;
        }
        return test.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid YearMonth.
     */
    public static boolean isValidYearMonth(String test) {
        try {
            YearMonth.parse(test, YEAR_MONTH_FORMATTER);
        } catch (DateTimeParseException ex) {
            return false;
        }
        return test.matches(MONTH_YEAR_VALIDATION_REGEX);
    }

    /**
     * Returns true if the specified year is more than or equals to the current year and
     * less than or equals the maximum year.
     *
     * @param year The specified year.
     * @return True if the specified year is valid.
     */
    public static boolean isValidYear(int year) {
        return year >= MINIMUM_YEAR && year <= MAXIMUM_YEAR && String.valueOf(year).matches(YEAR_VALIDATION_REGEX);
    }

    /**
     * Returns the YearMonth of the date.
     * @return YearMonth of the Date.
     */
    public static YearMonth getYearMonthOfDate(LocalDate date) {
        int month = date.getMonthValue();
        int year = date.getYear();
        return YearMonth.of(year, month);
    }

    /**
     * Formats a certain LocalDate using the default
     * DateTimeFormatter specified in DateUtil,
     * resulting in a String described by that format.
     *
     * @param ld The LocalDate to be formatted to a String.
     * @return String representing LocalDate using the
     * format described in DateUtil.
     */
    public static String formatDateToString(LocalDate ld) {
        return ld.format(DateUtil.DATE_FORMATTER);
    }

    /**
     * Returns a formatted YearMonth string based on the specified YearMonth.
     *
     * @param yearMonth The specified YearMonth.
     * @return Formatted YearMonth string.
     */
    public static String formatYearMonthToString(YearMonth yearMonth) {
        requireNonNull(yearMonth);
        return yearMonth.format(YEAR_MONTH_FORMATTER);
    }

    /**
     * Returns a formatted YearMonth string based on the specified DateString.
     *
     * @param date The specified Date String.
     * @return Formatted YearMonth string.
     */
    public static String formatDateStringToYearMonthString(String date) {
        requireNonNull(date);
        YearMonth ym = getYearMonthOfDate(parseStringToDate(date));
        return formatYearMonthToString(ym);
    }

    /**
     * Parses a String into a LocalDate using the
     * format described in DateUtil. If this
     * String does not match the common format,
     * DateTimeParseException will be thrown
     *
     * @param str The String to be parsed into a LocalDate.
     * @return LocalDate containing temporal information.
     */
    public static LocalDate parseStringToDate(String str) throws DateTimeParseException {
        return LocalDate.parse(str, DATE_FORMATTER);
    }

    /**
     * Parses a String into a YearMonth using the format described in DateUtil. If this
     * String does not match the common format,DateTimeParseException will be thrown
     *
     * @param str The String to be parsed into a YearMonth.
     * @return YearMonth containing temporal information.
     */
    public static YearMonth parseStringToYearMonth(String str) throws DateTimeParseException {
        return YearMonth.parse(str, YEAR_MONTH_FORMATTER);
    }

    /**
     * Parses a String into a Date String with Standard Format of DD-MM-YYYY.
     * @param str The string to be parsed.
     * @return String of LocalDate in DD-MM-YYYY format.
     */
    public static String parseStringToStandardDateString(String str) {
        return parseStringToDate(str).format(DATE_FORMATTER_STANDARD);
    }
}
