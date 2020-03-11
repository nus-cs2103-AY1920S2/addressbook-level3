package csdev.couponstash.model;

import csdev.couponstash.model.coupon.Coupon;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a CouponStash
 */
public interface ReadOnlyCouponStash {

    /**
     * Returns an unmodifiable view of the coupons list.
     * This list will not contain any duplicate coupons.
     */
    ObservableList<Coupon> getCouponList();

}
