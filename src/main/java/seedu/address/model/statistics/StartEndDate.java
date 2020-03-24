package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents the date time of a transaction.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartEndDate(String)}
 */
public class StartEndDate {

    public static final String MESSAGE_CONSTRAINTS = "Start and end dates should be in YYYY-mm-dd format, "
            + "and it should not be blank";
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /*
     * There must be one or more digits entered.
     */
    public static final String VALIDATION_REGEX = "(\\d{4}-\\d{2}-\\d{2})";

    public final Date value;

    /**
     * Constructs an {@code DateTime}.
     *
     * @param startEndDate A valid sales.
     */
    public StartEndDate(String startEndDate) {
        requireNonNull(startEndDate);
        checkArgument(isValidStartEndDate(startEndDate), MESSAGE_CONSTRAINTS);

        Date date;
        try {
            date = DATE_TIME_FORMAT.parse(startEndDate);
        } catch (ParseException e) {
            date = new Date();
        }
        value = date;
    }

    /**
     * Returns true if a given string is a valid sales.
     */
    public static boolean isValidStartEndDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        try {
            DATE_TIME_FORMAT.parse(test);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return DATE_TIME_FORMAT.format(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartEndDate // instanceof handles nulls
                && value.equals(((StartEndDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
