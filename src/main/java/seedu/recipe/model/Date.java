package seedu.recipe.model;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 * Represents a date in the recipes book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {


    public static final String MESSAGE_CONSTRAINTS =
            "Date should be written in the format YYYY-MM-DD";

    public static final String VALIDATION_REGEX = "^[0-9-]+";
    public static final DateTimeFormatter DAY_OF_WEEK = DateTimeFormatter.ofPattern("EEEE");
    public static final DateTimeFormatter DATE_AND_MONTH = DateTimeFormatter.ofPattern("dd MMMM yyyy");
    public static final ZoneId ZONE_ID = ZoneId.of("Asia/Singapore");
    public static final Locale SINGAPORE_LOCALE = new Locale("en", "SGP");

    private final LocalDate date;

    /**
     * Constructs a {@code Date}.
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date);
    }

    public Date(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    public Date() {
        this.date = LocalDate.now();
    }

    /**
     * Returns true if a given string is a valid date
     */
    public static boolean isValidDate(String test) {
        if (test.isBlank() || !test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            // DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MM YYYY");
            // todo: be able to parse other formats as well
            LocalDate.parse(test);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the Date of today, according to the input timezone.
     */
    public static Date today() {
        return new Date(LocalDate.now(ZONE_ID));
    }

    /**
     * Returns true if the date is older than today's date, according to the input timezone.
     */
    public boolean isDateInFuture() {
        Date yesterday = new Date(LocalDate.now(ZONE_ID).minusDays(1));
        return isAfter(yesterday);
    }

    /**
     * Checks whether the current date falls within the range of {@code start} Date and {@code end} Date.
     * The start and end dates are non-inclusive.
     */
    public boolean isWithinRange(Date start, Date end) {
        if (!start.isAfter(end)) {
            return date.isAfter(start.date) && date.isBefore(end.date);
        }
        return false;
    }

    /**
     * Returns true if the current date is after {@code otherDate}.
     */
    public boolean isAfter(Date otherDate) {
        return date.isAfter(otherDate.date);
    }

    public String getMonthName() {
        return date.getMonth().getDisplayName(TextStyle.FULL, SINGAPORE_LOCALE);
    }

    public String getDayOfWeek() {
        return "" + date.getDayOfWeek();
    }

    public String getWeekOfMonth() {
        WeekFields weekFields = WeekFields.of(SINGAPORE_LOCALE.getDefault());
        return "" + date.get(weekFields.weekOfMonth());
    }

    public String toStringForJson() {
        return date.toString();
    }

    @Override
    public int compareTo(Date other) {
        LocalDate otherLocalDate = other.date;
        if (date.isBefore(otherLocalDate)) {
            return -1;
        } else if (date.isAfter(otherLocalDate)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return date.format(DAY_OF_WEEK) + ": " + date.format(DATE_AND_MONTH);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}

