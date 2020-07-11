package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import csdev.couponstash.logic.commands.exceptions.CommandException;
import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.Coupon;

import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.TypicalCoupons;
import csdev.couponstash.testutil.TypicalIndexes;

class UndoCommandTest {
    private Model model = new ModelManager(
            TypicalCoupons.getTypicalCouponStash(),
            new UserPrefs()
    );
    private Model expectedModel = new ModelManager(
            TypicalCoupons.getTypicalCouponStash(),
            new UserPrefs()
    );

    @Test
    public void execute_undoAdd_success() {

        CouponBuilder couponBuilder = new CouponBuilder();
        Coupon validCoupon = couponBuilder.build();
        String commandText = "add " + couponBuilder.FULL_COMMAND_TEXT;

        model.addCoupon(validCoupon, commandText);

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, commandText);

        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.ALICE,
                TypicalCoupons.BENSON,
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_undoDelete_success() {
        Coupon couponToDelete = model
                .getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());

        model.deleteCoupon(couponToDelete, "");

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.ALICE,
                TypicalCoupons.BENSON,
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_undoEdit_success() {
        Coupon editedCoupon = new CouponBuilder().build();

        model.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.ALICE,
                TypicalCoupons.BENSON,
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_undoClear_success() {

        model.setCouponStash(new CouponStash(), "");

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new UndoCommand(), model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.ALICE,
                TypicalCoupons.BENSON,
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_noStateToUndoTo_throwsCommandException() {
        UndoCommand undoCommand = new UndoCommand();

        assertThrows(
                CommandException.class, UndoCommand.MESSAGE_NO_STATE_TO_UNDO_TO, () -> undoCommand.execute(model)
        );
    }
}
