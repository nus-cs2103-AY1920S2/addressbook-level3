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
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidPhone, true));
    }

    @Test
    public void isValidAmount() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid phone numbers
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("91")); // less than 3 numbers
        assertFalse(Amount.isValidAmount("phone")); // non-numeric
        assertFalse(Amount.isValidAmount("9011p041")); // alphabets within digits
        assertFalse(Amount.isValidAmount("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Amount.isValidAmount("911")); // exactly 3 numbers
        assertTrue(Amount.isValidAmount("93121534"));
        assertTrue(Amount.isValidAmount("124293842033123")); // long phone numbers
    }
}
