package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.DateUtil.YEAR_MONTH_FORMATTER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import csdev.couponstash.testutil.CouponBuilder;


public class DateIsInMonthYearPredicateTest {

    @Test
    public void equals() {
        String firstPredicateDate = "12-2020";
        String secondPredicateDate = "8-2020";

        DateIsInMonthYearPredicate firstPredicate =
                new DateIsInMonthYearPredicate(prepareYearMonth(firstPredicateDate));
        DateIsInMonthYearPredicate secondPredicate =
                new DateIsInMonthYearPredicate(prepareYearMonth(secondPredicateDate));

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateIsInMonthYearPredicate firstPredicateCopy =
                new DateIsInMonthYearPredicate(prepareYearMonth(firstPredicateDate));
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different coupon -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_expiryDateIsInMonthYear_returnsTrue() {
        // One keyword
        DateIsInMonthYearPredicate predicate = new DateIsInMonthYearPredicate(prepareYearMonth("6-2020"));
        assertTrue(predicate.test(new CouponBuilder().withExpiryDate("1-6-2020").build()));

        // Different date format
        predicate = new DateIsInMonthYearPredicate(prepareYearMonth("08-2020"));
        assertTrue(predicate.test(new CouponBuilder().withExpiryDate("1-8-2020").build()));
    }

    @Test
    public void test_expiryDateNotInMonthYear_returnsFalse() {
        //No coupons before expiry date
        DateIsInMonthYearPredicate predicate = new DateIsInMonthYearPredicate(prepareYearMonth("6-2020"));
        assertFalse(predicate.test(new CouponBuilder().withExpiryDate("30-8-2020").build()));
    }

    /**
     * Parses {@code userInput} into a {@code MonthYear}.
     */
    private YearMonth prepareYearMonth(String userInput) {
        return YearMonth.parse(userInput, YEAR_MONTH_FORMATTER);
    }
}
