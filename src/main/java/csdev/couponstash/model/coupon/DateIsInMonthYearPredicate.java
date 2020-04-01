package csdev.couponstash.model.coupon;

import java.time.YearMonth;
import java.util.function.Predicate;

import csdev.couponstash.commons.util.DateUtil;

/**
 * Tests that a {@code Coupon}'s {@code ExpiryDate} is in the specified YearMonth.
 */
public class DateIsInMonthYearPredicate implements Predicate<Coupon> {
    private final YearMonth date;

    public DateIsInMonthYearPredicate (YearMonth date) {
        this.date = date;
    }

    public String getDate() {
        return this.date.format(DateUtil.YEAR_MONTH_FORMATTER);
    }

    @Override
    public boolean test(Coupon coupon) {
        int month = coupon.getExpiryDate().getDate().getMonthValue();
        int year = coupon.getExpiryDate().getDate().getYear();
        YearMonth ym = YearMonth.of(year, month);
        return date.equals(ym);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateIsInMonthYearPredicate // instanceof handles nulls
                && date.equals(((DateIsInMonthYearPredicate) other).date)); // state check
    }
}


