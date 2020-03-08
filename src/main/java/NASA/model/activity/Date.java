package NASA.model.activity;

import static java.util.Objects.requireNonNull;
import static NASA.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;

/**
 * Represents Date of an Activity.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}.
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Dates should only be in the format DD-MM-YYYY HH:MM, and it should not be blank";

    public final LocalDateTime date;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = constructDateTime(date);
    }

    private static LocalDateTime constructDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(date, formatter);
    }

    /**
     * Checks if a given string is a valid date.
     *
     * @return true if the string matches date format.
     */
    public static boolean isValidDate(String test) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            formatter.setLenient(false);
            formatter.parse(test);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Returns date as a string with format MMM d yyyy.
     *
     * @return date as a string with format MMM d yyyy.
     */
    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
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

