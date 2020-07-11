package csdev.couponstash.model.coupon;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Coupon's promo code in the CouponStash.
 * Guarantees: immutable
 */
public class PromoCode {

    public static final int STRING_LENGTH_LIMIT = 40;

    public final String value;

    /**
     * Constructs a {@code PromoCode}.
     *
     * @param promoCode A promoCode.
     */
    public PromoCode(String promoCode) {
        requireNonNull(promoCode);
        value = promoCode;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PromoCode // instanceof handles nulls
                && value.equals(((PromoCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
