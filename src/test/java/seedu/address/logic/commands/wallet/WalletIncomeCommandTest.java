package seedu.address.logic.commands.wallet;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalWallet.ALLOWANCE;
import static seedu.address.testutil.TypicalWallet.TA_JOB;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.transaction.Income;
import seedu.address.testutil.ModelStub;

public class WalletIncomeCommandTest {

    @Test
    public void constructor_nullIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new WalletIncomeCommand(null));
    }

    @Test
    public void execute_incomeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIncomeAdded modelStub = new ModelStubAcceptingIncomeAdded();

        CommandResult commandResult = new WalletIncomeCommand(ALLOWANCE).execute(modelStub);

        assertEquals(String.format(WalletIncomeCommand.MESSAGE_SUCCESS, ALLOWANCE), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(ALLOWANCE), modelStub.incomesAdded);
    }

    @Test
    public void equals() {
        WalletIncomeCommand allowanceCommand = new WalletIncomeCommand(ALLOWANCE);
        WalletIncomeCommand taCommand = new WalletIncomeCommand(TA_JOB);

        // same object -> returns true
        assertTrue(allowanceCommand.equals(allowanceCommand));

        // same values -> returns true
        WalletIncomeCommand allowanceCommandCopy = new WalletIncomeCommand(ALLOWANCE);
        assertTrue(allowanceCommand.equals(allowanceCommandCopy));

        // different types -> returns false
        assertFalse(allowanceCommand.equals(1));

        // null -> returns false
        assertFalse(allowanceCommand.equals(null));

        // different income -> returns false
        assertFalse(allowanceCommand.equals(taCommand));
    }

    /**
     * A Model stub that always accept the income being added.
     */
    private class ModelStubAcceptingIncomeAdded extends ModelStub {
        final ArrayList<Income> incomesAdded = new ArrayList<>();

        @Override
        public void addIncome(Income income) {
            requireNonNull(income);
            incomesAdded.add(income);
        }
    }

}
