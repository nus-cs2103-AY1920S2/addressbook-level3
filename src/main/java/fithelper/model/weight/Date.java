package fithelper.model.weight;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static fithelper.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

/**
 * Represents a Weight's recording time in one weight record.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS = "Date should be in format: yyyy-mm-dd";

    public static final DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public final LocalTime value;
    /**
     * Constructs an {@code Time}.
     *
     * @param date A valid time.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = LocalTime.parse(date, parseFormat);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        try {
            LocalTime.parse(test, parseFormat);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
