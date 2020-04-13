package seedu.zerotoone.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.zerotoone.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a DateTime.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class DateTime implements Comparable<DateTime> {

    public static final String DATE_TIME_PATTERN = "uuuu-MM-dd HH:mm";
    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).withResolverStyle(ResolverStyle.STRICT);

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Datetime must be valid and in the format %1$s.", DATE_TIME_PATTERN);

    private final LocalDateTime localDateTime;

    public DateTime(String localDateTime) {
        requireNonNull(localDateTime);
        checkArgument(isValidDateTime(localDateTime), MESSAGE_CONSTRAINTS);
        this.localDateTime = LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    /**
     * Returns true if a given string is a valid date time that confronts to the specified format.
     */
    public static Boolean isValidDateTime(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    public static DateTime now() {
        return new DateTime(LocalDateTime.now().format(DATE_TIME_FORMATTER));
    }

    /**
     * Returns true if the date of a {@Code DateTime} is later than today's date.
     */
    public static Boolean isDateEqualOrLaterThanToday(DateTime dateTime) {
        DateTime now = now();
        return dateTime.isDateEqualOrLaterThan(now);
    }

    /**
     * Returns true if the date of a {@Code DateTime} is later than this {@Code DateTime}.
     */
    public Boolean isDateEqualOrLaterThan(DateTime other) {
        return this.localDateTime.toLocalDate().compareTo(other.localDateTime.toLocalDate()) >= 0;
    }

    @Override
    public String toString() {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateTime)) {
            return false;
        }

        DateTime otherDateTime = (DateTime) other;
        return this.localDateTime.equals(otherDateTime.localDateTime);
    }

    @Override
    public int compareTo(DateTime other) {
        return localDateTime.compareTo(other.localDateTime);
    }
}
