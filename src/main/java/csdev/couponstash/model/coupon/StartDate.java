package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import csdev.couponstash.commons.util.DateUtil;

/**
 * Represents a Coupon's start date in the CouponStash.
 * Guarantees: immutable; is valid as declared in {@link DateUtil#isValidDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Start Dates should not be after the Expiry Date (in the D-M-YYYY format).";
    public final LocalDate date;
    public final String value;

    /**
     * Constructs a {@code startDate}.
     *
     * @param startDate A valid start date.
     */
    public StartDate(String startDate) {
        requireNonNull(startDate);
        checkArgument(DateUtil.isValidDate(startDate), MESSAGE_CONSTRAINTS);
        value = DateUtil.parseStringToStandardDateString(startDate);
        date = DateUtil.parseStringToDate(startDate);
    }

    /**
     * Returns the start date as a {@LocalDate}.
     * @return Start Date as a {@LocalDate}
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
                || (other instanceof StartDate// instanceof handles nulls
                && date.equals(((StartDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

