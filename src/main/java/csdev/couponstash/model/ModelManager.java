package csdev.couponstash.model;

import static csdev.couponstash.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import csdev.couponstash.commons.core.GuiSettings;
import csdev.couponstash.commons.core.LogsCenter;
import csdev.couponstash.commons.core.StashSettings;
import csdev.couponstash.model.coupon.Coupon;

import csdev.couponstash.model.undoredo.HistoryManager;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the CouponStash data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CouponStash couponStash;
    private final UserPrefs userPrefs;
    private final FilteredList<Coupon> filteredCoupons;
    private HistoryManager history;

    /**
     * Initializes a ModelManager with the given couponStash and userPrefs.
     */
    public ModelManager(ReadOnlyCouponStash couponStash, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(couponStash, userPrefs);

        logger.fine("Initializing with CouponStash: " + couponStash + " and user prefs " + userPrefs);

        this.couponStash = new CouponStash(couponStash);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCoupons = new FilteredList<>(this.couponStash.getCouponList());
        history = new HistoryManager(this.couponStash.copy());
    }

    public ModelManager() {
        this(new CouponStash(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public StashSettings getStashSettings() {
        return userPrefs.getStashSettings();
    }

    @Override
    public void setStashSettings(StashSettings ss) {
        requireNonNull(ss);
        userPrefs.setStashSettings(ss);
    }

    @Override
    public Path getCouponStashFilePath() {
        return userPrefs.getCouponStashFilePath();
    }

    @Override
    public void setCouponStashFilePath(Path couponStashFilePath) {
        requireNonNull(couponStashFilePath);
        userPrefs.setCouponStashFilePath(couponStashFilePath);
    }

    //=========== CouponStash ================================================================================
    // Please run commitCouponStash() every time couponsStash is mutated.

    @Override
    public void setCouponStash(ReadOnlyCouponStash couponStash) {
        this.couponStash.resetData(couponStash);
        commitCouponStash();
    }

    @Override
    public ReadOnlyCouponStash getCouponStash() {
        return couponStash;
    }

    @Override
    public boolean hasCoupon(Coupon coupon) {
        requireNonNull(coupon);
        return couponStash.hasCoupon(coupon);
    }

    @Override
    public void deleteCoupon(Coupon target) {
        couponStash.removeCoupon(target);
        commitCouponStash();
    }

    @Override
    public void addCoupon(Coupon coupon) {
        couponStash.addCoupon(coupon);
        updateFilteredCouponList(PREDICATE_SHOW_ALL_COUPONS);
        commitCouponStash();
    }

    @Override
    public void setCoupon(Coupon target, Coupon editedCoupon) {
        requireAllNonNull(target, editedCoupon);

        couponStash.setCoupon(target, editedCoupon);
        commitCouponStash();
    }

    //=========== Filtered Coupon List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Coupon} backed by the internal list of
     * {@code versionedCouponStash}
     */
    @Override
    public ObservableList<Coupon> getFilteredCouponList() {
        return filteredCoupons;
    }

    @Override
    public void updateFilteredCouponList(Predicate<Coupon> predicate) {
        requireNonNull(predicate);
        filteredCoupons.setPredicate(predicate);
    }

    //=========== Undo/Redo functionality =============================================================
    @Override
    public void commitCouponStash() {
        history.commitState(couponStash.copy());
    }

    @Override
    public void undoCouponStash() {
        couponStash.resetData(history.undo());
    }

    @Override
    public void redoCouponStash() {
        couponStash.resetData(history.redo());
    }

    @Override
    public boolean canUndoCouponStash() {
        return history.canUndo();
    }

    @Override
    public boolean canRedoCouponStash() {
        return history.canRedo();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return couponStash.equals(other.couponStash)
                && userPrefs.equals(other.userPrefs)
                && filteredCoupons.equals(other.filteredCoupons);
    }

}
