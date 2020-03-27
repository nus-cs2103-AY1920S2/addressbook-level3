package csdev.couponstash.model.coupon;

import java.time.LocalDate;
import java.util.function.Predicate;

import csdev.couponstash.commons.util.DateUtil;

/**
 * Tests that a {@code Coupon}'s {@code ExpiryDate} is before the specified date.
 */
public class DateIsBeforePredicate implements Predicate<Coupon> {
    private final String date;

    public DateIsBeforePredicate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    @Override
    public boolean test(Coupon coupon) {
        LocalDate ed = coupon.getExpiryDate().getDate();
        LocalDate givenDate = DateUtil.parseString(date);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return ed.isBefore(givenDate) && ed.isAfter(yesterday);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateIsBeforePredicate // instanceof handles nulls
                && date.equals(((DateIsBeforePredicate) other).date)); // state check
    }
}


