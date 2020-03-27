package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * STEPH_TODO_JAVADOC
 */
public class DateTime {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final String MESSAGE_CONSTRAINTS =
            String.format("DateTime must be in the format %1$s.", DATE_TIME_PATTERN);

    public final LocalDateTime dateTime;

    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        this.dateTime = LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
    }

    /**
     * STEPH_TODO_JAVADOC
     * @param dateTime STEPH_TODO_JAVADOC
     * @return STEPH_TODO_JAVADOC
     */
    public static Boolean isValidDateTime(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
