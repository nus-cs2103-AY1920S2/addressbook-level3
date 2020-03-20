package csdev.couponstash.commons.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class stores a common DateTimeFormatter to be
 * used on all dates in Coupon Stash. Ensures that all
 * dates have a consistent format that can be changed
 * easily if desired.
 */
public class DateFormatUtil {
    // The standard DateTimeFormatter describing the consistent Coupon Stash Date Format
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-M-yyyy");

    /**
     * Formats a certain LocalDate using the default
     * DateTimeFormatter specified in DateFormatUtil,
     * resulting in a String described by that format.
     * @param ld The LocalDate to be formatted to a String.
     * @return String representing LocalDate using the
     *     format described in DateFormatUtil.
     */
    public static String formatDate(LocalDate ld) {
        return ld.format(DateFormatUtil.DATE_FORMATTER);
    }

    /**
     * Parses a String into a LocalDate using the
     * format described in DateFormatUtil. If this
     * String does not match the common format,
     * DateTimeParseException will be thrown
     * @param str The String to be parsed into a LocalDate.
     * @return LocalDate containing temporal information.
     */
    public static LocalDate parseString(String str) {
        return LocalDate.parse(str, DATE_FORMATTER);
    }
}
