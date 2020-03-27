package seedu.eylah.expensesplitter.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(new BigDecimal(invalidAmount)));
    }

    @Test
    public void isValidAmount() {
        // null Amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // invalid Amount
        assertThrows(IllegalArgumentException.class, () -> new Amount(new BigDecimal(" "))); // spaces only
        assertThrows(IllegalArgumentException.class, () -> new Amount(new BigDecimal("phone"))); // non-numeric
        assertThrows(IllegalArgumentException.class, () -> new Amount(new BigDecimal("9011p041")));
        // alphabets within digits
        assertThrows(IllegalArgumentException.class, () -> new Amount(new BigDecimal("9011 4192")));
        // spaces within digits
        assertFalse(Amount.isValidAmount(new BigDecimal("123.133"))); // cannot have more than 3 decimal digits

        // valid Amount
        assertTrue(Amount.isValidAmount(BigDecimal.valueOf(9))); // exactly 1 number
        assertTrue(Amount.isValidAmount(BigDecimal.valueOf(9.84)));
        assertTrue(Amount.isValidAmount(BigDecimal.valueOf(9999999.12))); // long amount
    }
}
