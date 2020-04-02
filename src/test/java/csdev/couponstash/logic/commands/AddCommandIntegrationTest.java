package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandFailure;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.TypicalCoupons;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
    }

    @Test
    public void execute_newCoupon_success() {
        Coupon validCoupon = new CouponBuilder().build();

        Model expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
        expectedModel.addCoupon(validCoupon, "");

        assertCommandSuccess(new AddCommand(validCoupon), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validCoupon.getName()), expectedModel);
    }

    @Test
    public void execute_duplicateCoupon_throwsCommandException() {
        Coupon couponInList = model.getCouponStash().getCouponList().get(0);
        assertCommandFailure(new AddCommand(couponInList), model, AddCommand.MESSAGE_DUPLICATE_COUPON);
    }

}
