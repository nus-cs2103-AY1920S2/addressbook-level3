package seedu.expensela.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class BalanceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Balance(null));
    }

    @Test
    public void constructor_invalidBalance_throwsIllegalArgumentException() {
        String invalidBalance = "";
        assertThrows(IllegalArgumentException.class, () -> new Balance(invalidBalance));
    }

    @Test
    public void isValidBalance() {
        // null budget
        assertThrows(NullPointerException.class, () -> Balance.isValidAmount(null));

        // invalid budget
        assertFalse(Balance.isValidAmount("")); // empty string
        assertFalse(Balance.isValidAmount(" ")); // spaces only
        assertFalse(Balance.isValidAmount(".88")); // no dollars
        assertFalse(Balance.isValidAmount("eighty eight")); // non-numeric
        assertFalse(Balance.isValidAmount("88e88")); // alphabets within digits
        assertFalse(Balance.isValidAmount("69 96")); // spaces within digits

        // valid budget
        assertTrue(Balance.isValidAmount("-88")); // negative amount
        assertTrue(Balance.isValidAmount("88")); // no cents
        assertTrue(Balance.isValidAmount("88.9")); // cents not 2 digits
        assertTrue(Balance.isValidAmount("88.80")); // both dollars and cents
        assertTrue(Balance.isValidAmount("87654321.88"));
        assertTrue(Balance.isValidAmount("124293842033123.00")); // long budget
    }

}
