package csdev.couponstash.model.coupon;

import java.time.LocalDate;
import java.util.function.Predicate;

import csdev.couponstash.commons.util.DateUtil;

/**
 * Tests that a {@code Coupon}'s {@code ExpiryDate} is equals the specified date.
 */
public class DateIsEqualsPredicate implements Predicate<Coupon> {
    private final String date;

    public DateIsEqualsPredicate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public boolean test(Coupon coupon) {
        LocalDate ed = coupon.getExpiryDate().getDate();
        LocalDate givenDate = DateUtil.parseStringToDate(date);
        return ed.isEqual(givenDate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateIsEqualsPredicate // instanceof handles nulls
                && date.equals(((DateIsEqualsPredicate) other).date)); // state check
    }
}


