package csdev.couponstash.model.coupon;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import csdev.couponstash.testutil.CouponBuilder;

public class DateIsBeforePredicateTest {

    @Test
    public void equals() {
        String firstPredicateDate = "31-12-2020";
        String secondPredicateDate = "30-8-2020";

        DateIsBeforePredicate firstPredicate = new DateIsBeforePredicate(firstPredicateDate);
        DateIsBeforePredicate secondPredicate = new DateIsBeforePredicate(secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateIsBeforePredicate firstPredicateCopy = new DateIsBeforePredicate(firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different coupon -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_expiryDateIsBefore_returnsTrue() {
        // One keyword
        DateIsBeforePredicate predicate = new DateIsBeforePredicate("2-6-2020");
        assertTrue(predicate.test(new CouponBuilder().withExpiryDate("1-6-2020").build()));

        // Different date format
        predicate = new DateIsBeforePredicate("30-08-2020");
        assertTrue(predicate.test(new CouponBuilder().withExpiryDate("1-8-2020").build()));
    }

    @Test
    public void test_expiryDateNotBefore_returnsFalse() {
        //No coupons before expiry date
        DateIsBeforePredicate predicate = new DateIsBeforePredicate("1-6-2020");
        assertFalse(predicate.test(new CouponBuilder().withExpiryDate("30-8-2020").build()));
    }
}
