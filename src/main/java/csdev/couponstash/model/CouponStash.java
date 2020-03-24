package csdev.couponstash.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.UniqueCouponList;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the CouponStash level
 * Duplicates are not allowed (by .isSameCoupon comparison)
 */
public class CouponStash implements ReadOnlyCouponStash {

    private final UniqueCouponList coupons;

    // The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
    // between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
    //
    // note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
    // among constructors.

    {
        coupons = new UniqueCouponList();
    }

    public CouponStash() {
    }

    /**
     * Creates an CouponStash using the Coupons in the {@code toBeCopied}
     */
    public CouponStash(ReadOnlyCouponStash toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the coupon list with {@code coupons}.
     * {@code coupons} must not contain duplicate coupons.
     */
    public void setCoupons(List<Coupon> coupons) {
        this.coupons.setCoupons(coupons);
    }

    public void sortCoupons(Comparator<Coupon> comp) {
        List<Coupon> sorted =
                new ArrayList<>(copy().getCouponList());
        sorted.sort(comp);

        setCoupons(sorted);
    }

    /**
     * Resets the existing data of this {@code CouponStash} with {@code newData}.
     */
    public void resetData(ReadOnlyCouponStash newData) {
        requireNonNull(newData);

        setCoupons(newData.getCouponList());
    }

    //// coupon-level operations

    /**
     * Returns true if a coupon with the same identity as {@code coupon} exists in the CouponStash.
     */
    public boolean hasCoupon(Coupon coupon) {
        requireNonNull(coupon);
        return coupons.contains(coupon);
    }

    /**
     * Adds a coupon to the CouponStash.
     * The coupon must not already exist in the CouponStash.
     */
    public void addCoupon(Coupon p) {
        coupons.add(p);
    }

    /**
     * Replaces the given coupon {@code target} in the list with {@code editedCoupon}.
     * {@code target} must exist in the CouponStash.
     * The coupon identity of {@code editedCoupon} must not be the same as another existing coupon in the CouponStash.
     */
    public void setCoupon(Coupon target, Coupon editedCoupon) {
        requireNonNull(editedCoupon);

        coupons.setCoupon(target, editedCoupon);
    }

    /**
     * Removes {@code key} from this {@code CouponStash}.
     * {@code key} must exist in the CouponStash.
     */
    public void removeCoupon(Coupon key) {
        coupons.remove(key);
    }

    /**
     * Copies all the contents of this {@code CouponStash} to another {@code CouponStash}.
     *
     * @return A copied {@code CouponStash}
     */
    public CouponStash copy() {
        CouponStash copy = new CouponStash();

        for (Coupon coupon : coupons) {
            copy.addCoupon(coupon.copy());
        }

        return copy;
    }

    //// util methods

    @Override
    public String toString() {
        return coupons.asUnmodifiableObservableList().size() + " coupons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Coupon> getCouponList() {
        return coupons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CouponStash // instanceof handles nulls
                && coupons.equals(((CouponStash) other).coupons));
    }

    @Override
    public int hashCode() {
        return coupons.hashCode();
    }
}
