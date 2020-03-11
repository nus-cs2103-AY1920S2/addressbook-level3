package csdev.couponstash.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.UniqueCouponList;

import javafx.collections.ObservableList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCoupon comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueCouponList coupons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        coupons = new UniqueCouponList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Coupons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
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

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setCoupons(newData.getCouponList());
    }

    //// coupon-level operations

    /**
     * Returns true if a coupon with the same identity as {@code coupon} exists in the address book.
     */
    public boolean hasCoupon(Coupon coupon) {
        requireNonNull(coupon);
        return coupons.contains(coupon);
    }

    /**
     * Adds a coupon to the address book.
     * The coupon must not already exist in the address book.
     */
    public void addCoupon(Coupon p) {
        coupons.add(p);
    }

    /**
     * Replaces the given coupon {@code target} in the list with {@code editedCoupon}.
     * {@code target} must exist in the address book.
     * The coupon identity of {@code editedCoupon} must not be the same as another existing coupon in the address book.
     */
    public void setCoupon(Coupon target, Coupon editedCoupon) {
        requireNonNull(editedCoupon);

        coupons.setCoupon(target, editedCoupon);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeCoupon(Coupon key) {
        coupons.remove(key);
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
                || (other instanceof AddressBook // instanceof handles nulls
                && coupons.equals(((AddressBook) other).coupons));
    }

    @Override
    public int hashCode() {
        return coupons.hashCode();
    }
}
