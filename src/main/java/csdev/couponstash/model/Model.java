package csdev.couponstash.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.model.coupon.Coupon;

import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Coupon> PREDICATE_SHOW_ALL_COUPONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' CouponStash file path.
     */
    Path getCouponStashFilePath();

    /**
     * Sets the user prefs' CouponStash file path.
     */
    void setCouponStashFilePath(Path couponStashFilePath);

    /**
     * Replaces CouponStash data with the data in {@code couponStash}.
     */
    void setCouponStash(ReadOnlyCouponStash couponStash);

    /** Returns the CouponStash */
    ReadOnlyCouponStash getCouponStash();

    /**
     * Returns true if a coupon with the same identity as {@code coupon} exists in the CouponStash.
     */
    boolean hasCoupon(Coupon coupon);

    /**
     * Deletes the given coupon.
     * The coupon must exist in the CouponStash.
     */
    void deleteCoupon(Coupon target);

    /**
     * Adds the given coupon.
     * {@code coupon} must not already exist in the CouponStash.
     */
    void addCoupon(Coupon coupon);

    /**
     * Replaces the given coupon {@code target} with {@code editedCoupon}.
     * {@code target} must exist in the CouponStash.
     * The coupon identity of {@code editedCoupon} must not be the same as another existing coupon in the CouponStash.
     */
    void setCoupon(Coupon target, Coupon editedCoupon);

    /** Returns an unmodifiable view of the filtered coupon list */
    ObservableList<Coupon> getFilteredCouponList();

    /**
     * Updates the filter of the filtered coupon list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCouponList(Predicate<Coupon> predicate);
}
