package csdev.couponstash.model.coupon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import csdev.couponstash.testutil.CouponBuilder;

public class DateIsEqualsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateDate = "31-12-2020";
        String secondPredicateDate = "30-8-2020";

        DateIsEqualsPredicate firstPredicate = new DateIsEqualsPredicate(firstPredicateDate);
        DateIsEqualsPredicate secondPredicate = new DateIsEqualsPredicate(secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateIsEqualsPredicate firstPredicateCopy = new DateIsEqualsPredicate(firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different coupon -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_expiryDateIsEquals_returnsTrue() {
        // One keyword
        DateIsEqualsPredicate predicate = new DateIsEqualsPredicate("1-6-2020");
        assertTrue(predicate.test(new CouponBuilder().withExpiryDate("1-6-2020").build()));

        // Different date format
        predicate = new DateIsEqualsPredicate("30-08-2020");
        assertTrue(predicate.test(new CouponBuilder().withExpiryDate("30-8-2020").build()));
    }

    @Test
    public void test_expiryDateNotEquals_returnsFalse() {
        //No coupons before expiry date
        DateIsEqualsPredicate predicate = new DateIsEqualsPredicate("1-6-2020");
        assertFalse(predicate.test(new CouponBuilder().withExpiryDate("30-8-2020").build()));
    }
}
