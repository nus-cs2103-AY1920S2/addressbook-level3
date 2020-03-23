package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Coupon's start date in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidStartDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Start Dates should be a date in the D-M-YYYY format.";
    public static final String VALIDATION_REGEX = "\\d{1,2}-\\d{1,2}-\\d{4}";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d-M-yyyy");
    public final LocalDate date;
    public final String value;

    /**
     * Constructs a {@code startDate}.
     *
     * @param startDate A valid start date.
     */
    public StartDate(String startDate) {
        requireNonNull(startDate);
        checkArgument(isValidStartDate(startDate), MESSAGE_CONSTRAINTS);
        if ("".equals(startDate)) {
            value = LocalDate.now().format(DATE_FORMATTER);
        } else {
            value = startDate;
        }
        date = getDate();
    }

    /**
     * Returns true if a given string is a valid start date.
     */
    public static boolean isValidStartDate(String test) {
        return ("").equals(test) || (test.matches(VALIDATION_REGEX));
    }

    /**
     * Returns the start date as a LocalDate.
     * @return Start Date as a LocalDate
     */
    public LocalDate getDate() {
        return LocalDate.parse(value, DATE_FORMATTER);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate// instanceof handles nulls
                && value.equals(((StartDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

