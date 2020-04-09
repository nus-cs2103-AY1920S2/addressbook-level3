package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import csdev.couponstash.commons.util.DateUtil;

/**
 * Represents a Coupon's expiry date in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link DateUtil#isValidDate(String)}
 */
public class ExpiryDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Expiry Dates should not be a date before the start date (in the D-M-YYYY format).";
    public final LocalDate date;
    public final String value;

    /**
     * Constructs a {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiry date.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        checkArgument(DateUtil.isValidDate(expiryDate), MESSAGE_CONSTRAINTS);
        value = DateUtil.parseStringToStandardDateString(expiryDate);
        date = DateUtil.parseStringToDate(expiryDate);
    }

    /**
     * Returns true if the {@StartDate} is after or equals to the {@ExpiryDate}.
     * @param sd The StartDate
     * @return True if date is after or equals to the expiry date
     */
    public boolean isAfterOrEqual(StartDate sd) {
        return date.isAfter(sd.getDate()) || date.isEqual(sd.getDate());
    }

    /**
     * Returns true if the {@ExpiryDate} is after today.
     * @return True if date is after today.
     */
    public boolean hasExpired() {
        return date.isBefore(LocalDate.now());
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
                && date.equals(((ExpiryDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

