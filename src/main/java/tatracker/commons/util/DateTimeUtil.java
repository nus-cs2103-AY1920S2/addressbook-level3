// @@author potatocombat

package tatracker.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Helper functions for handling dates and times.
 */
public class DateTimeUtil {

    public static final String PATTERN_DATE = "uuuu-MM-dd"; // yyyy does not work with the strict resolving mode
    public static final String PATTERN_TIME = "HH:mm";

    public static final DateTimeFormatter FORMAT_DATE = DateTimeFormatter
            .ofPattern(PATTERN_DATE)
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter FORMAT_TIME = DateTimeFormatter
            .ofPattern(PATTERN_TIME)
            .withResolverStyle(ResolverStyle.STRICT);

    public static final String CONSTRAINTS_DATE = "Dates must be valid and in yyyy-MM-dd format";
    public static final String CONSTRAINTS_TIME = String.format("Times must be valid and in %s format", PATTERN_TIME);

    /**
     * Returns true if {@code String date} represents a {@code LocalDate}
     * in yyyy-MM-dd format.
     */
    public static boolean isDate(String date) {
        requireNonNull(date);
        try {
            LocalDate.parse(date, FORMAT_DATE);
            return true;
        } catch (DateTimeParseException dtpe) {
            return false;
        }
    }

    /**
     * Returns true if {@code String date} represents a {@code LocalDate}
     * in HH:mm format.
     */
    public static boolean isTime(String time) {
        requireNonNull(time);
        try {
            LocalTime.parse(time, FORMAT_TIME);
            return true;
        } catch (DateTimeParseException dtpe) {
            return false;
        }
    }
}
