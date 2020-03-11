package csdev.couponstash.model.coupon;

import static csdev.couponstash.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import csdev.couponstash.model.coupon.exceptions.CouponNotFoundException;
import csdev.couponstash.model.coupon.exceptions.DuplicateCouponException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of coupons that enforces uniqueness between its elements and does not allow nulls.
 * A coupon is considered unique by comparing using {@code Coupon#isSameCoupon(Coupon)}. As such, adding and updating of
 * coupons uses Coupon#isSameCoupon(Coupon) for equality so as to ensure that the coupon being added or updated is
 * unique in terms of identity in the UniqueCouponList. However, the removal of a coupon uses Coupon#equals(Object) so
 * as to ensure that the coupon with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Coupon#isSameCoupon(Coupon)
 */
public class UniqueCouponList implements Iterable<Coupon> {

    private final ObservableList<Coupon> internalList = FXCollections.observableArrayList();
    private final ObservableList<Coupon> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent coupon as the given argument.
     */
    public boolean contains(Coupon toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCoupon);
    }

    /**
     * Adds a coupon to the list.
     * The coupon must not already exist in the list.
     */
    public void add(Coupon toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCouponException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the coupon {@code target} in the list with {@code editedCoupon}.
     * {@code target} must exist in the list.
     * The coupon identity of {@code editedCoupon} must not be the same as another existing coupon in the list.
     */
    public void setCoupon(Coupon target, Coupon editedCoupon) {
        requireAllNonNull(target, editedCoupon);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CouponNotFoundException();
        }

        if (!target.isSameCoupon(editedCoupon) && contains(editedCoupon)) {
            throw new DuplicateCouponException();
        }

        internalList.set(index, editedCoupon);
    }

    /**
     * Removes the equivalent coupon from the list.
     * The coupon must exist in the list.
     */
    public void remove(Coupon toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CouponNotFoundException();
        }
    }

    public void setCoupons(UniqueCouponList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code coupons}.
     * {@code coupons} must not contain duplicate coupons.
     */
    public void setCoupons(List<Coupon> coupons) {
        requireAllNonNull(coupons);
        if (!couponsAreUnique(coupons)) {
            throw new DuplicateCouponException();
        }

        internalList.setAll(coupons);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Coupon> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Coupon> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCouponList // instanceof handles nulls
                        && internalList.equals(((UniqueCouponList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code coupons} contains only unique coupons.
     */
    private boolean couponsAreUnique(List<Coupon> coupons) {
        for (int i = 0; i < coupons.size() - 1; i++) {
            for (int j = i + 1; j < coupons.size(); j++) {
                if (coupons.get(i).isSameCoupon(coupons.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
