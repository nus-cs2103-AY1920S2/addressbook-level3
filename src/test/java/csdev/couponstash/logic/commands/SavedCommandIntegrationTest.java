package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import csdev.couponstash.commons.core.index.Index;
import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.Coupon;
import csdev.couponstash.model.coupon.savings.DateSavingsSumMap;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.model.coupon.savings.SavingsConversionUtil;
import csdev.couponstash.testutil.CouponBuilder;
import csdev.couponstash.testutil.TypicalCoupons;
import csdev.couponstash.testutil.TypicalIndexes;

/**
 * Integration tests for Saved Command, namely with Used Command.
 */
public class SavedCommandIntegrationTest {
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());

    @Test
    public void execute_afterCouponIsUsed_savingsIncreased() {
        // check savings before usage
        PureMonetarySavings zeroSavings = new PureMonetarySavings();
        LocalDate today = LocalDate.now();
        String moneySymbol = model.getStashSettings().getMoneySymbol().getString();
        String expectedMessageBeforeUse = "You saved " + moneySymbol
                + String.format("%.2f", zeroSavings.getMonetaryAmountAsDouble())
                + " on " + SavedCommand.formatDate(today) + ".";
        Model modelBeforeUse = new ModelManager(model.getCouponStash(), new UserPrefs());
        // ensure model and modelBeforeUse are the same
        assertEquals(model, modelBeforeUse);
        // assert that savings for today is correctly set to 0, without changing the model
        assertCommandSuccess(new SavedCommand(today), model, expectedMessageBeforeUse, modelBeforeUse);


        // now, execute the usage
        Index couponIndex = TypicalIndexes.INDEX_FIRST_COUPON;
        Coupon couponToBeUsed = model.getFilteredCouponList().get(couponIndex.getZeroBased());
        UsedCommand usedCommand = new UsedCommand(couponIndex);

        ModelManager expectedModelAfterUse = new ModelManager(model.getCouponStash(), new UserPrefs());
        int expectedUsageAmount = couponToBeUsed.getUsage().value + 1;
        PureMonetarySavings savingsForOneUse =
                SavingsConversionUtil.convertToPure(couponToBeUsed.getSavingsForEachUse());
        DateSavingsSumMap expectedTotalSavings = new DateSavingsSumMap(
                LocalDate.now(),
                savingsForOneUse);
        Coupon expectedCoupon =
                new CouponBuilder(couponToBeUsed).withUsage(expectedUsageAmount)
                        .withTotalSavings(expectedTotalSavings).build();
        expectedModelAfterUse.setCoupon(couponToBeUsed, expectedCoupon, "");

        String expectedMessage = String.format(UsedCommand.MESSAGE_USED_COUPON_SUCCESS, expectedCoupon.getName());

        assertCommandSuccess(usedCommand, model, expectedMessage, expectedModelAfterUse);


        // check savings after usage, should have increased
        String saveablesString = "";
        StringBuilder saveablesStringBuilder = new StringBuilder();
        List<Saveable> saveablesList = savingsForOneUse.getListOfSaveables();
        saveablesList.sort(Saveable::compareTo);
        saveablesList.forEach(sv -> saveablesStringBuilder.append(sv.toString()).append(", "));

        if (!saveablesList.isEmpty()) {
            saveablesString = " as well as earned "
                    + saveablesStringBuilder.substring(0, saveablesStringBuilder.length() - 2);
        }

        String expectedMessageAfterUse = "You saved " + moneySymbol
                + String.format("%.2f", savingsForOneUse.getMonetaryAmountAsDouble())
                + saveablesString
                + " on " + SavedCommand.formatDate(today) + ".";
        // assert that savings for today has been changed, but model has not changed
        assertCommandSuccess(new SavedCommand(today), model, expectedMessageAfterUse, expectedModelAfterUse);
    }
}
