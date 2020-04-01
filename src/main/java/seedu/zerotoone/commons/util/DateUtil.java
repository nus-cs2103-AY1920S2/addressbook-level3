package seedu.zerotoone.commons.util;

import java.time.LocalDateTime;

import seedu.zerotoone.model.schedule.ScheduledWorkout;

public class DateUtil {
    public static String getFormattedDateTimeString(LocalDateTime dateTime) {
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
