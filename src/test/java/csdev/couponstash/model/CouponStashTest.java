package csdev.couponstash.model;

import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_LIMIT_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_START_DATE_BOB;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static csdev.couponstash.logic.commands.CommandTestUtil.VALID_USAGE_BOB;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static csdev.couponstash.testutil.TypicalCoupons.ALICE;
import static csdev.couponstash.testutil.TypicalCoupons.getTypicalCouponStash;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.exceptions.DuplicateCouponException;
import csdev.couponstash.testutil.CouponBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CouponStashTest {

    private final CouponStash couponStash = new CouponStash();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), couponStash.getCouponList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> couponStash.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyCouponStash_replacesData() {
        CouponStash newData = getTypicalCouponStash();
        couponStash.resetData(newData);
        assertEquals(newData, couponStash);
    }

    @Test
    public void resetData_withDuplicateCoupons_throwsDuplicateCouponException() {
        // Two coupons with the same identity fields
        Coupon editedAlice = new CouponBuilder(ALICE)
                .withStartDate(VALID_START_DATE_BOB)
                .withUsage(VALID_USAGE_BOB)
                .withLimit(VALID_LIMIT_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();

        List<Coupon> newCoupons = Arrays.asList(ALICE, editedAlice);
        CouponStashStub newData = new CouponStashStub(newCoupons);

        assertThrows(DuplicateCouponException.class, () -> couponStash.resetData(newData));
    }

    @Test
    public void hasCoupon_nullCoupon_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> couponStash.hasCoupon(null));
    }

    @Test
    public void hasCoupon_couponNotInCouponStash_returnsFalse() {
        assertFalse(couponStash.hasCoupon(ALICE));
    }

    @Test
    public void hasCoupon_couponInCouponStash_returnsTrue() {
        couponStash.addCoupon(ALICE);
        assertTrue(couponStash.hasCoupon(ALICE));
    }

    @Test
    public void hasCoupon_couponWithSameIdentityFieldsInCouponStash_returnsTrue() {
        couponStash.addCoupon(ALICE);
        Coupon editedAlice = new CouponBuilder(ALICE)
                .withStartDate(VALID_START_DATE_BOB)
                .withUsage(VALID_USAGE_BOB)
                .withLimit(VALID_LIMIT_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(couponStash.hasCoupon(editedAlice));
    }

    @Test
    public void getCouponList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> couponStash.getCouponList().remove(0));
    }

    /**
     * A stub ReadOnlyCouponStash whose coupons list can violate interface constraints.
     */
    private static class CouponStashStub implements ReadOnlyCouponStash {
        private final ObservableList<Coupon> coupons = FXCollections.observableArrayList();

        CouponStashStub(Collection<Coupon> coupons) {
            this.coupons.setAll(coupons);
        }

        @Override
        public ObservableList<Coupon> getCouponList() {
            return coupons;
        }
    }

}
