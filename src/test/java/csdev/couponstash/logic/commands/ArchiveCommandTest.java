package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandFailure;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static csdev.couponstash.logic.commands.CommandTestUtil.showCouponAtIndex;
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

public class ArchiveCommandTest {
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Coupon couponToArchive = model.getFilteredCouponList().get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_COUPON_SUCCESS,
                couponToArchive.getName());
        Coupon expectedCoupon = couponToArchive.archive();
        ModelManager expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
        expectedModel.setCoupon(couponToArchive, expectedCoupon, "");

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCouponList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);

        Coupon couponToArchive = model.getFilteredCouponList().get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_COUPON_SUCCESS,
                couponToArchive.getName());
        Coupon expectedCoupon = couponToArchive.archive();
        Model expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
        expectedModel.setCoupon(couponToArchive, expectedCoupon, "");

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCouponAtIndex(model, TypicalIndexes.INDEX_FIRST_COUPON);

        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND_COUPON;
        // ensures that outOfBoundIndex is still in bounds of CouponStash list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCouponStash().getCouponList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, Messages.MESSAGE_INVALID_COUPON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(TypicalIndexes.INDEX_SECOND_COUPON);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(TypicalIndexes.INDEX_FIRST_COUPON);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different command -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }
}
