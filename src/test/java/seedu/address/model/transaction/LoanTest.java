package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLoans.BREAKFAST;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;

public class LoanTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Loan(null, null, null));
    }

    @Test
    public void equals() {
        Loan breakfastCopy = new TransactionBuilder()
                .withDescription("Breakfast")
                .withAmount("5")
                .withDate("08/08/2018")
                .buildLoan();
        Debt breakfastDebt = new TransactionBuilder()
                .withDescription("Breakfast")
                .withAmount("5")
                .withDate("08/08/2018")
                .buildDebt();
        Loan loanDiffDesc = new TransactionBuilder()
                .withDescription("Lunch")
                .withAmount("5")
                .withDate("08/08/2018")
                .buildLoan();
        Loan loanDiffAmt = new TransactionBuilder()
                .withDescription("Breakfast")
                .withAmount("10")
                .withDate("08/08/2018")
                .buildLoan();
        Loan loanDiffDate = new TransactionBuilder()
                .withDescription("Breakfast")
                .withAmount("5")
                .withDate("12/01/2020")
                .buildLoan();

        // same values -> returns true
        assertTrue(BREAKFAST.equals(breakfastCopy));

        // same object -> returns true
        assertTrue(BREAKFAST.equals(BREAKFAST));

        // null -> returns false
        assertFalse(BREAKFAST.equals(null));

        // different type -> returns false
        assertFalse(BREAKFAST.equals(5));

        // different type of transaction -> returns false
        assertFalse(BREAKFAST.equals(breakfastDebt));

        // different description -> returns false
        assertFalse(BREAKFAST.equals(loanDiffDesc));

        // different amount -> returns false
        assertFalse(BREAKFAST.equals(loanDiffAmt));

        // different date -> returns false
        assertFalse(BREAKFAST.equals(loanDiffDate));
    }

}
