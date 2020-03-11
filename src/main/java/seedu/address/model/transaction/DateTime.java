package seedu.address.model.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the date time of a transaction.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateTime(String)}
 */
public class DateTime {

    public static final String MESSAGE_CONSTRAINTS = "DateTime should be in YYYY-mm-dd hh:mm format, and it should not be blank";
    public static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "(\\d{4}-\\d{2}-\\d{2})\\s+\\d{2}:\\d{2}";

    public final LocalDateTime value;

    /**
     * Constructs an {@code Sales}.
     *
     * @param dateTime A valid sales.
     */
    public DateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTime), MESSAGE_CONSTRAINTS);
        value = LocalDateTime.parse(dateTime, DATE_TIME_FORMAT);
    }

    /**
     * Returns true if a given string is a valid sales.
     */
    public static boolean isValidDateTime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try{
            LocalDateTime.parse(test, DATE_TIME_FORMAT);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value.format(DATE_TIME_FORMAT);
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
