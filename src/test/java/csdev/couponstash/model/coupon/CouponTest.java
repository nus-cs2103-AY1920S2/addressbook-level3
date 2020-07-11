package csdev.couponstash.model.coupon;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.CommandTestUtil;
import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.TypicalCoupons;

public class CouponTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Coupon coupon = new CouponBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> coupon.getTags().remove(0));
    }

    @Test
    public void increaseUsageByOne_anyCoupon_returnsTrue() {
        Coupon coupon = new CouponBuilder().build();
        Usage currentUsage = coupon.getUsage();
        Coupon usedCoupon = coupon.increaseUsageByOne();
        Usage newUsage = usedCoupon.getUsage();
        int difference = newUsage.value - currentUsage.value;
        assertTrue(difference == 1);
    }

    @Test
    public void setArchivalState_true_returnsTrue() {
        Coupon coupon = new CouponBuilder().build();
        boolean stateOfArchival = Boolean.valueOf(coupon.archive().getArchived().state);
        assertTrue(stateOfArchival);
    }

    @Test
    public void setArchivalState_false_returnsTrue() {
        Coupon coupon = new CouponBuilder().build();
        boolean stateOfArchival = Boolean.valueOf(coupon.unarchive().getArchived().state);
        assertFalse(stateOfArchival);
    }

    @Test
    public void isSameCoupon() {
        // same object -> returns true
        assertTrue(TypicalCoupons.ALICE.isSameCoupon(TypicalCoupons.ALICE));

        // null -> returns false
        assertFalse(TypicalCoupons.ALICE.isSameCoupon(null));

        // different name -> returns false
        Coupon editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(TypicalCoupons.ALICE.isSameCoupon(editedAlice));

        //different promoCode -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE)
                .withPromoCode(CommandTestUtil.VALID_PROMO_CODE_BOB).build();
        assertFalse(TypicalCoupons.ALICE.isSameCoupon(editedAlice));

        //different expiry date -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withExpiryDate(CommandTestUtil.VALID_EXPIRY_DATE_BOB)
                .build();
        assertFalse(TypicalCoupons.ALICE.isSameCoupon(editedAlice));

        //different savings -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withSavings(CommandTestUtil.VALID_SAVINGS_BOB).build();
        assertFalse(TypicalCoupons.ALICE.isSameCoupon(editedAlice));

        //different tags -> returns true
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE)
                .withUsage(CommandTestUtil.VALID_USAGE_AMY)
                .withLimit(CommandTestUtil.VALID_USAGE_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(TypicalCoupons.ALICE.isSameCoupon(editedAlice));

        // different state of archival -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE)
                .withArchived(true)
                .build();
        assertFalse(TypicalCoupons.ALICE.isSameCoupon(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Coupon aliceCopy = new CouponBuilder(TypicalCoupons.ALICE).build();
        assertTrue(TypicalCoupons.ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(TypicalCoupons.ALICE.equals(TypicalCoupons.ALICE));

        // null -> returns false
        assertFalse(TypicalCoupons.ALICE.equals(null));

        // different type -> returns false
        assertFalse(TypicalCoupons.ALICE.equals(5));

        // different coupon -> returns false
        assertFalse(TypicalCoupons.ALICE.equals(TypicalCoupons.BOB));

        // different name -> returns false
        Coupon editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        assertFalse(TypicalCoupons.ALICE.equals(editedAlice));

        // different promoCode -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE)
                .withPromoCode(CommandTestUtil.VALID_PROMO_CODE_BOB).build();
        assertFalse(TypicalCoupons.ALICE.equals(editedAlice));

        // different usage -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE)
                .withUsage(TypicalCoupons.ALICE.getUsage().increaseUsageByOne().value).build();
        assertFalse(TypicalCoupons.ALICE.equals(editedAlice));

        // different limit -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withLimit(CommandTestUtil.VALID_LIMIT_BOB).build();
        assertFalse(TypicalCoupons.ALICE.equals(editedAlice));

        // different expiry date -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withExpiryDate(CommandTestUtil.VALID_EXPIRY_DATE_BOB)
                .build();
        assertFalse(TypicalCoupons.ALICE.equals(editedAlice));

        // different start date -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withStartDate(CommandTestUtil.VALID_START_DATE_BOB)
                .build();
        assertFalse(TypicalCoupons.ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertFalse(TypicalCoupons.ALICE.equals(editedAlice));

        // different state of archival -> returns false
        editedAlice = new CouponBuilder(TypicalCoupons.ALICE).withArchived(true)
                .build();
        assertFalse(TypicalCoupons.ALICE.equals(editedAlice));
    }
}
