package seedu.expensela.model.monthlydata;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class IncomeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Income(null));
    }

    @Test
    public void constructor_invalidIncome_throwsIllegalArgumentException() {
        String invalidIncome = "";
        assertThrows(IllegalArgumentException.class, () -> new Income(invalidIncome));
    }

    @Test
    public void isValidIncome() {
        // null income
        assertThrows(NullPointerException.class, () -> Income.isValidAmount(null));

        // invalid income
        assertFalse(Income.isValidAmount("")); // empty string
        assertFalse(Income.isValidAmount(" ")); // spaces only
        assertFalse(Income.isValidAmount(".88")); // no dollars
        assertFalse(Income.isValidAmount("eighty eight")); // non-numeric
        assertFalse(Income.isValidAmount("88e88")); // alphabets within digits
        assertFalse(Income.isValidAmount("69 96")); // spaces within digits
        assertFalse(Income.isValidAmount("-88")); // negative amount

        // valid income
        assertTrue(Income.isValidAmount("88.9")); // cents not 2 digits
        assertTrue(Income.isValidAmount("88")); // no cents
        assertTrue(Income.isValidAmount("88.80")); // both dollars and cents
        assertTrue(Income.isValidAmount("87654321.88"));
        assertTrue(Income.isValidAmount("124293842033123.00")); // long income
    }
}
