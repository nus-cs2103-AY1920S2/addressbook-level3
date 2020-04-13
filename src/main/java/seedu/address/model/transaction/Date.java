package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a Date in Sharkie.
 * Guarantees: immutable; date is valid as declared in {@link #isValidDate(String)}
 */

public class Date implements Comparable<Date> {

    public static final String DATE_PATTERN = "dd/MM/uuuu";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String MESSAGE_CONSTRAINTS = "Dates should be in form: " + DATE_FORMAT;

    public static final String MONTH_MESSAGE_CONSTRAINTS = "Month should be an integer (between 1 to 12)";
    public static final String YEAR_MESSAGE_CONSTRAINTS = "Year should be a non-negative integer";

    private final LocalDate date;

    /**
     * Constructs a valid Date object.
     *
     * @param date A valid date.
     */
    public Date(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    /**
     * Returns true if a given String is a valid date format.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalDate.parse(test, DateTimeFormatter.ofPattern(DATE_PATTERN).withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeParseException | IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if a given String is a valid month.
     */
    public static boolean isValidMonth(String test) {
        try {
            int monthInt = Integer.parseInt(test);
            return monthInt >= 1 && monthInt <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if a given String is a valid year.
     */
    public static boolean isValidYear(String test) {
        try {
            int yearInt = Integer.parseInt(test);
            return yearInt >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns a default date set to today's date.
     */
    public static Date getDefault() {
        return new Date(LocalDate.now());
    }

    public LocalDate getLocalDate() {
        return date;
    }

    /**
     * Returns the String of the date in "dd/MM/yyyy" format.
     */
    public String getInputFormat() {
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public Month getMonth() {
        return date.getMonth();
    }

    public Year getYear() {
        return Year.of(date.getYear());
    }

    /**
     * Returns whether the Date is in the specified month.
     */
    public boolean inMonth(Month month, Year year) {
        return getMonth().equals(month) && getYear().equals(year);
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Date
                && date.toString().equals(((Date) other).toString()));
    }

    @Override
    public int compareTo(Date other) {
        return getLocalDate().compareTo(other.getLocalDate());
    }
}
