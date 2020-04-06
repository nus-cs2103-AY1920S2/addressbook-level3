package seedu.expensela.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expensela.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null, true));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount, true));
    }

    @Test
    public void isValidAmount() {
        // null amount number
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid amount numbers
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("amount")); // non-numeric
        assertFalse(Amount.isValidAmount("9011p041")); // alphabets within digits
        assertFalse(Amount.isValidAmount("9312 1534")); // spaces within digits
        assertFalse(Amount.isValidAmount("-88")); // negative amount

        // valid amount numbers
        assertTrue(Amount.isValidAmount("91")); // no decimal places
        assertTrue(Amount.isValidAmount("91.1")); // 1 decimal place
        assertTrue(Amount.isValidAmount("91.10")); // exactly 2 decimal places
        assertTrue(Amount.isValidAmount("124293842033123.00")); // long amount numbers
    }
}
