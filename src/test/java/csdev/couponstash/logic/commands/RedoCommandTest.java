package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static csdev.couponstash.testutil.Assert.assertThrows;

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

    @Test
    public void execute_redoAdd_success() {
        Model model = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );
        Coupon validCoupon = new CouponBuilder().build();
        model.addCoupon(validCoupon, "");
        model.undoCouponStash();

        ModelManager expectedModel = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );
        expectedModel.addCoupon(validCoupon, "");

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_redoDelete_success() {
        Model model = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );
        Coupon couponToDelete = model
                .getFilteredCouponList()
                .get(TypicalIndexes.INDEX_FIRST_COUPON.getZeroBased());

        model.deleteCoupon(couponToDelete, "");
        model.undoCouponStash();

        ModelManager expectedModel = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );
        expectedModel.deleteCoupon(couponToDelete, "");

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_redoEdit_success() {
        Model model = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );

        Coupon editedCoupon = new CouponBuilder().build();

        model.setCoupon(model.getFilteredCouponList().get(0), editedCoupon, "");
        model.undoCouponStash();

        ModelManager expectedModel = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );
        expectedModel.setCoupon(
                model.getFilteredCouponList().get(0), editedCoupon, ""
        );

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_redoClear_success() {
        Model model = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );

        model.setCouponStash(new CouponStash(), "");
        model.undoCouponStash();

        ModelManager expectedModel = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );
        expectedModel.setCouponStash(new CouponStash(), "");

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, "");

        assertCommandSuccess(new RedoCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noStateToRedoTo_throwsCommandException() {
        Model model = new ModelManager(
                TypicalCoupons.getTypicalCouponStash(),
                new UserPrefs()
        );

        RedoCommand redoCommand = new RedoCommand();

        assertThrows(CommandException.class, RedoCommand.MESSAGE_NO_STATE_TO_REDO_TO, () -> redoCommand.execute(model));
    }
}
