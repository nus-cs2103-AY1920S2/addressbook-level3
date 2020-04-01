package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import csdev.couponstash.commons.util.DateUtil;

/**
 * Represents a Coupon's expiry date in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)}
 */
public class ExpiryDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Expiry Dates should be a date in the D-M-YYYY format.";
    public final LocalDate date;
    public final String value;

    /**
     * Constructs a {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiry date.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
        value = expiryDate;
        date = DateUtil.parseStringToDate(value);
    }

    /**
     * Returns true if a given string is a valid expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        try {
            DateUtil.parseStringToDate(test);
        } catch (DateTimeParseException ex) {
            return false;
        }
        return test.matches(DateUtil.DATE_VALIDATION_REGEX);
    }

    /**
     * Returns the expiry date as a LocalDate.
     * @return Expiry Date as a LocalDate
     */
    public LocalDate getDate() {
        return this.date;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpiryDate// instanceof handles nulls
                && value.equals(((ExpiryDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

