package seedu.recipe.model.plan;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a date in the planned recipes book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {


    public static final String MESSAGE_CONSTRAINTS =
            "Date should be written in the format YYYY-MM-DD";

    public static final String VALIDATION_REGEX = "^[0-9-]+";
    public static final DateTimeFormatter DAY_OF_WEEK = DateTimeFormatter.ofPattern("EEEE");
    public static final DateTimeFormatter DATE_AND_MONTH = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = LocalDate.parse(date);
    }

    /**
     * Returns true if a given string is a valid date
     */
    public static boolean isValidDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
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
