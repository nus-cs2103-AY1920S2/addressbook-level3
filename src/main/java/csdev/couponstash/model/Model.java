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
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a coupon with the same identity as {@code coupon} exists in the address book.
     */
    boolean hasCoupon(Coupon coupon);

    /**
     * Deletes the given coupon.
     * The coupon must exist in the address book.
     */
    void deleteCoupon(Coupon target);

    /**
     * Adds the given coupon.
     * {@code coupon} must not already exist in the address book.
     */
    void addCoupon(Coupon coupon);

    /**
     * Replaces the given coupon {@code target} with {@code editedCoupon}.
     * {@code target} must exist in the address book.
     * The coupon identity of {@code editedCoupon} must not be the same as another existing coupon in the address book.
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
