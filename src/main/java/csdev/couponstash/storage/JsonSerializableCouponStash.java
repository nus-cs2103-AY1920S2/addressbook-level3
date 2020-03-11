package csdev.couponstash.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import csdev.couponstash.commons.exceptions.IllegalValueException;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.ReadOnlyCouponStash;
import csdev.couponstash.model.coupon.Coupon;

/**
 * An Immutable CouponStash that is serializable to JSON format.
 */
@JsonRootName(value = "couponstash")
class JsonSerializableCouponStash {

    public static final String MESSAGE_DUPLICATE_COUPON = "Coupons list contains duplicate coupon(s).";

    private final List<JsonAdaptedCoupon> coupons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCouponStash} with the given coupons.
     */
    @JsonCreator
    public JsonSerializableCouponStash(@JsonProperty("coupons") List<JsonAdaptedCoupon> coupons) {
        this.coupons.addAll(coupons);
    }

    /**
     * Converts a given {@code ReadOnlyCouponStash} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCouponStash}.
     */
    public JsonSerializableCouponStash(ReadOnlyCouponStash source) {
        coupons.addAll(source.getCouponList().stream().map(JsonAdaptedCoupon::new).collect(Collectors.toList()));
    }

    /**
     * Converts this CouponStash into the model's {@code CouponStash} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CouponStash toModelType() throws IllegalValueException {
        CouponStash couponStash = new CouponStash();
        for (JsonAdaptedCoupon jsonAdaptedCoupon : coupons) {
            Coupon coupon = jsonAdaptedCoupon.toModelType();
            if (couponStash.hasCoupon(coupon)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COUPON);
            }
            couponStash.addCoupon(coupon);
        }
        return couponStash;
    }

}
