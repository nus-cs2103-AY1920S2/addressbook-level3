package csdev.couponstash.testutil;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.coupon.Coupon;

/**
 * A utility class to help with building CouponStash objects.
 * Example usage: <br>
 *     {@code CouponStash ab = new CouponStashBuilder().withCoupon("John", "Doe").build();}
 */
public class CouponStashBuilder {

    private CouponStash couponStash;

    public CouponStashBuilder() {
        couponStash = new CouponStash();
    }

    public CouponStashBuilder(CouponStash couponStash) {
        this.couponStash = couponStash;
    }

    /**
     * Adds a new {@code Coupon} to the {@code CouponStash} that we are building.
     */
    public CouponStashBuilder withCoupon(Coupon coupon) {
        couponStash.addCoupon(coupon);
        return this;
    }

    public CouponStash build() {
        return couponStash;
    }
}
