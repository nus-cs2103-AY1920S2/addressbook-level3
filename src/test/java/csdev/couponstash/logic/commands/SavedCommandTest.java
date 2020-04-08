package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandFailure;
import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.model.UserPrefs;
import csdev.couponstash.model.coupon.savings.PureMonetarySavings;
import csdev.couponstash.model.coupon.savings.Saveable;
import csdev.couponstash.testutil.TypicalCoupons;


/**
 * Unit tests for Saved Command.
 */
public class SavedCommandTest {
    private Model model;
    // Saved Command should not change the model
    private Model expectedModel;

    @BeforeEach
    public void equals_modelAndExpectedModel_success() {
        // ensure model and expected model are the same
        model = new ModelManager(TypicalCoupons.getTypicalCouponStash(), new UserPrefs());
        expectedModel = new ModelManager(model.getCouponStash(), new UserPrefs());
        assertEquals(model, expectedModel);
    }

    @Test
    public void execute_noDatesSpecified_success() {
        PureMonetarySavings totalSavings = TypicalCoupons.ALICE_PMS_1
                .add(TypicalCoupons.ALICE_PMS_2).add(TypicalCoupons.ALICE_PMS_3)
                .add(TypicalCoupons.BENSON_PMS_1).add(TypicalCoupons.BENSON_PMS_2).add(TypicalCoupons.BENSON_PMS_3)
                .add(TypicalCoupons.CARL_PMS_1).add(TypicalCoupons.CARL_PMS_2).add(TypicalCoupons.CARL_PMS_3)
                .add(TypicalCoupons.DANIEL_PMS_1).add(TypicalCoupons.DANIEL_PMS_2).add(TypicalCoupons.DANIEL_PMS_3)
                .add(TypicalCoupons.ELLE_PMS_1).add(TypicalCoupons.ELLE_PMS_2).add(TypicalCoupons.ELLE_PMS_3)
                .add(TypicalCoupons.FIONA_PMS_1).add(TypicalCoupons.FIONA_PMS_2).add(TypicalCoupons.FIONA_PMS_3)
                .add(TypicalCoupons.GEORGE_PMS_1).add(TypicalCoupons.GEORGE_PMS_2).add(TypicalCoupons.GEORGE_PMS_3);
        StringBuilder saveablesString = new StringBuilder();
        List<Saveable> saveablesList = totalSavings.getListOfSaveables();
        saveablesList.sort(Saveable::compareTo);
        saveablesList.forEach(sv -> saveablesString.append(sv.toString()).append(", "));
        String moneySymbol = model.getStashSettings().getMoneySymbol().getString();
        String expectedMessage = "In total, you have saved " + moneySymbol
                + String.format("%.2f", totalSavings.getMonetaryAmountAsDouble())
                + " as well as earned " + saveablesString.substring(0, saveablesString.length() - 2) + ".";
        assertCommandSuccess(new SavedCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_oneDateSpecified_success() {
        PureMonetarySavings totalSavings = TypicalCoupons.CARL_PMS_3.add(TypicalCoupons.ELLE_PMS_3)
                .add(TypicalCoupons.FIONA_PMS_1).add(TypicalCoupons.GEORGE_PMS_2);
        StringBuilder saveablesString = new StringBuilder();
        List<Saveable> saveablesList = totalSavings.getListOfSaveables();
        saveablesList.sort(Saveable::compareTo);
        saveablesList.forEach(sv -> saveablesString.append(sv.toString()).append(", "));
        String moneySymbol = model.getStashSettings().getMoneySymbol().getString();
        String expectedMessage = "You saved " + moneySymbol
                + String.format("%.2f", totalSavings.getMonetaryAmountAsDouble())
                + " as well as earned " + saveablesString.substring(0, saveablesString.length() - 2)
                + " on " + SavedCommand.formatDate(TypicalCoupons.SHARED_DATE) + ".";
        assertCommandSuccess(new SavedCommand(TypicalCoupons.SHARED_DATE), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_dateRangeSpecified_success() {
        PureMonetarySavings totalSavings = TypicalCoupons.ALICE_PMS_1.add(TypicalCoupons.ALICE_PMS_2)
                .add(TypicalCoupons.BENSON_PMS_1).add(TypicalCoupons.BENSON_PMS_2).add(TypicalCoupons.CARL_PMS_2)
                .add(TypicalCoupons.DANIEL_PMS_1).add(TypicalCoupons.DANIEL_PMS_2).add(TypicalCoupons.DANIEL_PMS_3)
                .add(TypicalCoupons.ELLE_PMS_1).add(TypicalCoupons.ELLE_PMS_2)
                .add(TypicalCoupons.FIONA_PMS_2).add(TypicalCoupons.GEORGE_PMS_1);

        StringBuilder saveablesString = new StringBuilder();
        List<Saveable> saveablesList = totalSavings.getListOfSaveables();
        saveablesList.sort(Saveable::compareTo);
        saveablesList.forEach(sv -> saveablesString.append(sv.toString()).append(", "));
        String moneySymbol = model.getStashSettings().getMoneySymbol().getString();
        String expectedMessage = "You saved " + moneySymbol
                + String.format("%.2f", totalSavings.getMonetaryAmountAsDouble())
                + " as well as earned " + saveablesString.substring(0, saveablesString.length() - 2)
                + " between " + SavedCommand.formatDate(TypicalCoupons.GEORGE_DATE_1)
                + " and " + SavedCommand.formatDate(TypicalCoupons.FIONA_DATE_2) + ".";
        assertCommandSuccess(new SavedCommand(TypicalCoupons.GEORGE_DATE_1, TypicalCoupons.FIONA_DATE_2),
                model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_startDateAfterToday_throwsCommandException() {
        LocalDate today = LocalDate.now();
        SavedCommand command = new SavedCommand(today.plusDays(1), today.plusDays(2));
        assertCommandFailure(command, model, SavedCommand.MESSAGE_FUTURE_DATE);
    }
}
