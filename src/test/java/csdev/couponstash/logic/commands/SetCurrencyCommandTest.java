package csdev.couponstash.logic.commands;

import static csdev.couponstash.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import csdev.couponstash.model.Model;
import csdev.couponstash.model.ModelManager;
import csdev.couponstash.testutil.MoneySymbolStub;
import csdev.couponstash.testutil.TypicalCoupons;
import csdev.couponstash.testutil.UserPrefsStub;

/**
 * Unit tests for Set Currency Command, as well
 * as integration tests with ModelManager.
 */
public class SetCurrencyCommandTest {
    private MoneySymbolStub moneySymbol = new MoneySymbolStub("TEST");
    // model's money symbol will not be changed
    private Model model = new ModelManager(TypicalCoupons.getTypicalCouponStash(),
            new UserPrefsStub(moneySymbol));

    @Test
    public void execute_validMoneySymbol_correctMessageOutput() {
        String newSymbol = "STASH";
        String expectedMessage = String.format(SetCurrencyCommand.MESSAGE_SUCCESS, "TEST")
                + newSymbol + "!";
        assertCommandSuccess(new SetCurrencyCommand(newSymbol), model, expectedMessage, model);
    }
}
