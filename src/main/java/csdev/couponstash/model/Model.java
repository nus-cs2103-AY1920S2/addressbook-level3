package csdev.couponstash.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.commons.core.StashSettings;
import csdev.couponstash.logic.parser.Prefix;
import csdev.couponstash.model.coupon.Coupon;

import javafx.collections.ObservableList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Coupon> PREDICATE_SHOW_ALL_ACTIVE_COUPONS = coupon ->
            !(Boolean.valueOf(coupon.getArchived().toString()));

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
     * Returns the user prefs' StashSettings, which
     * are application settings set by the user
     */
    StashSettings getStashSettings();

    /**
     * Sets the user prefs' StashSettings, which
     * are application settings set by the user
     */
    void setStashSettings(StashSettings stashSettings);

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
    void setCouponStash(ReadOnlyCouponStash couponStash, String commandText);

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
    void deleteCoupon(Coupon target, String commandText);

    /**
     * Adds the given coupon.
     * {@code coupon} must not already exist in the CouponStash.
     */
    void addCoupon(Coupon coupon, String commandText);

    /**
     * Replaces the given coupon {@code target} with {@code editedCoupon}.
     * {@code target} must exist in the CouponStash.
     * The coupon identity of {@code editedCoupon} must not be the same as another existing coupon in the CouponStash.
     */
    void setCoupon(Coupon target, Coupon editedCoupon, String commandText);

    /** Returns an unmodifiable view of the filtered coupon list */
    ObservableList<Coupon> getFilteredCouponList();

    /**
     * Updates the filter of the filtered coupon list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCouponList(Predicate<Coupon> predicate);

    /**
     * Saves current coupon stash state in its history.
     * @param command The command that triggered the commit. (i.e. add, edit, delete, or clear)
     */
    void commitCouponStash(String command);

    /**
     * Restores the previous coupon statsh state from its history.
     * @return Command undone to
     */
    String undoCouponStash();

    /**
     * Restores a previously undone coupon stash state from its history.
     * @return Command redone to
     */
    String redoCouponStash();

    /**
     * Check if there is a coupon stash state to undo to.
     * @return true if there is a state to undo to, false otherwise
     */
    boolean canUndoCouponStash();

    /**
     * Check if there is a coupon stash state to redo to.
     * @return true if there is a state to redo to, false otherwise
     */
    boolean canRedoCouponStash();

    /**
     * Sorts coupons in the coupon stash according to the field specified
     * by the prefix.
     */
    void sortCoupons(Prefix prefixToSortBy, String commandText);
}
