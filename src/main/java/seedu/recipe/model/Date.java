package seedu.recipe.model;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a date in the recipes book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<seedu.recipe.model.Date> {


    public static final String MESSAGE_CONSTRAINTS =
            "Date should be written in the format YYYY-MM-DD";

    public static final String VALIDATION_REGEX = "^[0-9-]+";
    public static final DateTimeFormatter DAY_OF_WEEK = DateTimeFormatter.ofPattern("EEEE");
    public static final DateTimeFormatter DATE_AND_MONTH = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    private final LocalDate date;

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

    public Date(LocalDate date) {
        requireNonNull(date);
        this.date = date;
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

    public seedu.recipe.model.Date onFirstDayOfMonth() {
        return new seedu.recipe.model.Date(date.withDayOfMonth(1));
    }

    public seedu.recipe.model.Date onLastDayOfMonth() {
        return new seedu.recipe.model.Date(date.with(lastDayOfMonth()));
    }

    /**
     * Checks whether the current date falls within the range of {@code start} Date and {@code end} Date.
     * The start and end dates are non-inclusive.
     */
    public boolean isWithinRange(seedu.recipe.model.Date start, seedu.recipe.model.Date end) {
        return date.isAfter(start.date) && date.isBefore(end.date);
    }

    public String toStringForJson() {
        return date.toString();
    }

    @Override
    public int compareTo(seedu.recipe.model.Date other) {
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
                || (other instanceof seedu.recipe.model.Date // instanceof handles nulls
                && date.equals(((seedu.recipe.model.Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}

