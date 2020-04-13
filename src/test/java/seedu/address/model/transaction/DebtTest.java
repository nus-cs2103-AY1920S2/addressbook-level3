// @@author cheyannesim
package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDebts.SUPPER;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TransactionBuilder;

class DebtTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Debt(null, null, null));
    }

    @Test
    public void equals() {
        Debt supperCopy = new TransactionBuilder()
                .withDescription("Supper")
                .withAmount("10")
                .withDate("23/01/2020")
                .buildDebt();
        Loan supperLoan = new TransactionBuilder()
                .withDescription("Supper")
                .withAmount("10")
                .withDate("23/01/2020")
                .buildLoan();
        Debt debtDiffDesc = new TransactionBuilder()
                .withDescription("Dinner")
                .withAmount("10")
                .withDate("23/01/2020")
                .buildDebt();
        Debt debtDiffAmt = new TransactionBuilder()
                .withDescription("Supper")
                .withAmount("20")
                .withDate("23/01/2020")
                .buildDebt();
        Debt debtDiffDate = new TransactionBuilder()
                .withDescription("Supper")
                .withAmount("10")
                .withDate("12/01/2020")
                .buildDebt();

        // same values -> returns true
        assertTrue(SUPPER.equals(supperCopy));

        // same object -> returns true
        assertTrue(SUPPER.equals(SUPPER));

        // null -> returns false
        assertFalse(SUPPER.equals(null));

        // different type -> returns false
        assertFalse(SUPPER.equals(5));

        // different type of transaction -> returns false
        assertFalse(SUPPER.equals(supperLoan));

        // different description -> returns false
        assertFalse(SUPPER.equals(debtDiffDesc));

        // different amount -> returns false
        assertFalse(SUPPER.equals(debtDiffAmt));

        // different date -> returns false
        assertFalse(SUPPER.equals(debtDiffDate));

    }

    @Test
    public void tostring() {
        String expected = " Debt description: Supper Amount: $10.00 Date: 2020-01-23";
        assertEquals(expected, SUPPER.toString());
    }
}
