package seedu.expensela.model.monthlydata;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ExpenseTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Expense(null));
    }

    @Test
    public void constructor_invalidExpense_throwsIllegalArgumentException() {
        String invalidExpense = "";
        assertThrows(IllegalArgumentException.class, () -> new Expense(invalidExpense));
    }

    @Test
    public void isValidExpense() {
        // null expense
        assertThrows(NullPointerException.class, () -> Expense.isValidAmount(null));

        // invalid expense
        assertFalse(Expense.isValidAmount("")); // empty string
        assertFalse(Expense.isValidAmount(" ")); // spaces only
        assertFalse(Expense.isValidAmount(".88")); // no dollars
        assertFalse(Expense.isValidAmount("eighty eight")); // non-numeric
        assertFalse(Expense.isValidAmount("88e88.00")); // alphabets within digits
        assertFalse(Expense.isValidAmount("69 96.00")); // spaces within digits
        assertFalse(Expense.isValidAmount("-88")); // negative amount

        // valid budget
        assertTrue(Expense.isValidAmount("88.9")); // cents not 2 digits
        assertTrue(Expense.isValidAmount("88")); // no cents
        assertTrue(Expense.isValidAmount("88.80")); // both dollars and cents
        assertTrue(Expense.isValidAmount("87654321.88"));
        assertTrue(Expense.isValidAmount("124293842033123.00")); // long expense
    }
}
