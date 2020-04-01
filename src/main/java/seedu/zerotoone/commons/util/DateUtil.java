package seedu.zerotoone.commons.util;

import java.time.LocalDateTime;

/**
 * This utility class helps consolidate all date helper functions,
 */
public class DateUtil {
    /**
     * Gets pretty date time string.
     *
     * @param dateTime the date time
     * @return the formatted date time string
     */
    public static String getPrettyDateTimeString(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();
        String month = capitalize(dateTime.getMonth().toString());
        String dayOfWeek = capitalize(dateTime.getDayOfWeek().toString().substring(0, 3));
        return String.format("%s %s %s, %02d:%02d, %s", day, month, year, hour, minute, dayOfWeek);
    }

    private static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
