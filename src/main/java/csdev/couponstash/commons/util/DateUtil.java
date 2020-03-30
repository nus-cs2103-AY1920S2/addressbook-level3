package csdev.couponstash.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class stores a common DateTimeFormatter to be
 * used on all dates in Coupon Stash. Ensures that all
 * dates have a consistent format that can be changed
 * easily if desired.
 */

public class DateUtil {
    // The standard DateTimeFormatter describing the consistent Coupon Stash Date Format
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-M-yyyy");
    public static final String MESSAGE_DATE_WRONG_FORMAT = "Date is in the"
            + " wrong format! Should be D-M-YYYY";

    public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("M-yyyy");
    public static final String MESSAGE_YEAR_MONTH_WRONG_FORMAT = "Year-Month is in the"
            + " wrong format! Should be M-YYYY";

    //For Calendar
    public static final DateTimeFormatter DATE_TIME_FORMATTER_FOR_CALENDAR = DateTimeFormatter
            .ofPattern("EEEE, d LLLL yyyy");
    public static final DateTimeFormatter DAY_MONTH_YEAR_FORMATTER_FOR_CALENDAR = DateTimeFormatter
            .ofPattern("d LLLL yyyy");
    public static final DateTimeFormatter MONTH_YEAR_FORMATTER_FOR_CALENDAR = DateTimeFormatter.ofPattern("LLLL yyyy");

    private static final int MINIMUM_YEAR = 2000;
    private static final int MAXIMUM_YEAR = 2200;

    /**
     * Returns a formatted date string based on the specified date.
     *
     * @param date The specified date.
     * @return Formatted date string.
     */
    public static String getFormattedDateString(LocalDate date, DateTimeFormatter formatter) {
        requireNonNull(date);
        return date.format(formatter);
    }

    /**
     * Returns true if the specified year is more than or equals to the current year and
     * less than or equals the maximum year.
     *
     * @param year The specified year.
     * @return True if the specified year is valid.
     */
    public static boolean isValidYear(int year) {
        return year >= MINIMUM_YEAR && year <= MAXIMUM_YEAR;
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
    public static String formatDate(LocalDate ld) {
        return ld.format(DateUtil.DATE_FORMATTER);
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
    public static LocalDate parseString(String str) {
        return LocalDate.parse(str, DATE_FORMATTER);
    }
}
