package csdev.couponstash.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A container for date specific utility functions
 */
public class DateUtil {
    public static final DateTimeFormatter DATE_TIME_FORMATTER_FOR_CALENDAR = DateTimeFormatter
            .ofPattern("EEEE, d LLLL yyyy");
    public static final DateTimeFormatter DAY_MONTH_YEAR_FORMATTER_FOR_CALENDAR = DateTimeFormatter
            .ofPattern("d LLLL yyyy");
    public static final DateTimeFormatter MONTH_YEAR_FORMATTER_FOR_CALENDAR = DateTimeFormatter.ofPattern("LLLL yyyy");

    private static final int MINIMUM_YEAR = LocalDate.now().getYear();
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

}
