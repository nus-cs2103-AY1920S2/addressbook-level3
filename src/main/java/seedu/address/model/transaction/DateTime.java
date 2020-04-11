package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the date time of a transaction.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "DateTime should be in yyyy-mm-dd hh:mm format, "
            + "and it should not be in future";
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HH:mm")
            .withResolverStyle(ResolverStyle.STRICT);

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "(\\d{4}-\\d{2}-\\d{2})\\s+\\d{2}:\\d{2}";
    public static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Singapore");
    public static final LocalDateTime DEFAULT_VALUE = LocalDateTime.now(DEFAULT_ZONE);

    public final LocalDateTime value;

    /**
     * Constructs an {@code DateTime}.
     *
     * @param dateTime A valid dateTime string.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        value = LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
    }

    /**
     * Constructs an {@code DateTime}.
     *
     * @param dateTime A valid LocalDateTime format.
     */
    public DateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        value = dateTime;
    }

    /**
     * Returns true if a given string is a valid sales.
     */
    public static boolean isValidDateTime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            LocalDateTime dateTime = LocalDateTime.parse(test, DATE_TIME_FORMAT);
            if (dateTime.compareTo(DEFAULT_VALUE) > 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the current date time is before the other date time.
     */
    public boolean isBefore(DateTime other) {
        requireNonNull(other);
        return value.isBefore(other.value);
    }

    /**
     * Returns true if the current date time is after the other date time.
     */
    public boolean isAfter(DateTime other) {
        requireNonNull(other);
        LocalDate a = value.toLocalDate();
        a.atStartOfDay();
        return value.isAfter(other.value);
    }

    /**
     * Returns true if the current date time is on the same day with other date time.
     */
    public boolean isOnSameDay(DateTime other) {
        return value.toLocalDate().equals(other.value.toLocalDate());
    }

    /**
     * Generate a range of dates.
     * @param startDateTime the start date.
     * @param endDateTime the end date.
     * @return a list of dates between start and end.
     */
    public static List<DateTime> populateDates(DateTime startDateTime, DateTime endDateTime) {
        List<DateTime> dateTimes = new ArrayList<>();
        LocalDate localDate = startDateTime.value.toLocalDate();
        while (localDate.isBefore(endDateTime.value.toLocalDate().plusDays(1))) {
            dateTimes.add(new DateTime(localDate.atStartOfDay()));
            localDate = localDate.plusDays(1);
        }
        return dateTimes;
    }

    @Override
    public String toString() {
        return value.format(DATE_TIME_FORMAT);
    }

    /**
     * Returns the string representation of only year and date.
     */
    public String toDateString() {
        return value.toLocalDate().toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
