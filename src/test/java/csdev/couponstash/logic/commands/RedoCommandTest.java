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

class RedoCommandTest {
    private Model model = new ModelManager(
            TypicalCoupons.getTypicalCouponStash(),
            new UserPrefs()
    );
    private Model expectedModel = new ModelManager(
            TypicalCoupons.getTypicalCouponStash(),
            new UserPrefs()
    );

    @Test
    public void execute_redoAdd_success() {
        Coupon validCoupon = new CouponBuilder().build();
        model.addCoupon(validCoupon, "");
        model.undoCouponStash();
        expectedModel.addCoupon(validCoupon, "");

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.ALICE,
                TypicalCoupons.BENSON,
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.GEORGE,
                validCoupon
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_redoDelete_success() {
        Coupon couponToDelete = model
                .getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());

        model.deleteCoupon(couponToDelete, "");
        model.undoCouponStash();

        expectedModel.deleteCoupon(couponToDelete, "");

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                TypicalCoupons.BENSON,
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_redoEdit_success() {
        Coupon editedCoupon = new CouponBuilder().build();

        model.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");
        model.undoCouponStash();

        expectedModel.setCoupon(
                model.getFilteredCouponList().get(0), editedCoupon, ""
        );

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(
                editedCoupon,
                TypicalCoupons.BENSON,
                TypicalCoupons.CARL,
                TypicalCoupons.FIONA,
                TypicalCoupons.GEORGE
                ), model.getFilteredCouponList()
        );
    }

    @Test
    public void execute_redoClear_success() {
        model.setCouponStash(new CouponStash(), "");
        model.undoCouponStash();

        expectedModel.setCouponStash(new CouponStash(), "");

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(), model.getFilteredCouponList());
    }

    @Test
    public void execute_noStateToRedoTo_throwsCommandException() {
        // Add coupon, then undo, then add again. Shiuld have no more redo states.
        Coupon validCoupon = new CouponBuilder().build();
        model.addCoupon(validCoupon, "");
        model.undoCouponStash();
        model.addCoupon(validCoupon, "");

        RedoCommand redoCommand = new RedoCommand();

        assertThrows(CommandException.class, RedoCommand.MESSAGE_NO_STATE_TO_REDO_TO, () -> redoCommand.execute(model));
    }
}
