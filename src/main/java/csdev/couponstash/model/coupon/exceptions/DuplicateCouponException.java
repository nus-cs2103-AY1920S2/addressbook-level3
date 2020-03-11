package csdev.couponstash.model.coupon.exceptions;

/**
 * Signals that the operation will result in duplicate Coupons (Coupons are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCouponException extends RuntimeException {
    public DuplicateCouponException() {
        super("Operation would result in duplicate coupons");
    }
}
