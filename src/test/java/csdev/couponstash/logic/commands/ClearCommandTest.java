package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.CouponStash;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.testutil.TypicalCoupons;

public class ClearCommandTest {

    @Test
    public void execute_emptyCouponStash_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyCouponStash_success() {
        Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
        Model expectedModel = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
        expectedModel.setCouponStash(new CouponStash(), "");

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
