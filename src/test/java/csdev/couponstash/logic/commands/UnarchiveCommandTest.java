package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandFailure;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.core.Messages;
import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.testutil.TypicalCoupons;
import csdev.couponstash.testutil.TypicalIndexes;
import javafx.collections.ObservableList;

public class UnarchiveCommandTest {
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void execute_validIndexFilteredList_success() {
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ARCHIVED_COUPONS);
        ObservableList<Coupon> archivedCouponList = model.getFilteredCouponList();

        Coupon couponToUnarchive = archivedCouponList.get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_COUPON_SUCCESS,
                couponToUnarchive.getName());
        Coupon expectedCoupon = couponToUnarchive.unarchive();
        ModelManager expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
        expectedModel.setCoupon(couponToUnarchive, expectedCoupon, "");

        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        model.updateFilteredCouponList(Model.PREDICATE_SHOW_ALL_ARCHIVED_COUPONS);
        ObservableList<Coupon> archivedCouponList = model.getFilteredCouponList();

        Index outOfBoundIndex = Index.fromOneBased(archivedCouponList.size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(TypicalIndexes.INDEX_SECOND_COUPON);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand archiveFirstCommandCopy = new UnarchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        assertTrue(unarchiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }
}
