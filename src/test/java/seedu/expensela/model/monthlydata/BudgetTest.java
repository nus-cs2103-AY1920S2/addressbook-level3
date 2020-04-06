package seedu.expensela.model.monthlydata;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BudgetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null));
    }

    @Test
    public void constructor_invalidBudget_throwsIllegalArgumentException() {
        String invalidBudget = "";
        assertThrows(IllegalArgumentException.class, () -> new Budget(invalidBudget));
    }

    @Test
    public void isValidBudget() {
        // null budget
        assertThrows(NullPointerException.class, () -> Budget.isValidAmount(null));

        // invalid budget
        assertFalse(Budget.isValidAmount("")); // empty string
        assertFalse(Budget.isValidAmount(" ")); // spaces only
        assertFalse(Budget.isValidAmount(".88")); // no dollars
        assertFalse(Budget.isValidAmount("eighty eight")); // non-numeric
        assertFalse(Budget.isValidAmount("88e88")); // alphabets within digits
        assertFalse(Budget.isValidAmount("69 96")); // spaces within digits
        assertFalse(Budget.isValidAmount("-88")); // negative amount

        // valid budget
        assertTrue(Budget.isValidAmount("88")); // no cents
        assertTrue(Budget.isValidAmount("88.9")); // cents not 2 digits
        assertTrue(Budget.isValidAmount("88.80")); // both dollars and cents
        assertTrue(Budget.isValidAmount("87654321.88"));
        assertTrue(Budget.isValidAmount("124293842033123.00")); // long budget
    }
}
