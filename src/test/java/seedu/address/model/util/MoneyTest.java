package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Money(null));
    }

    @Test
    public void constructor_invalidSales_throwsIllegalArgumentException() {
        String invalidSales = "";
        assertThrows(IllegalArgumentException.class, () -> new Money(invalidSales));
    }

    @Test
    public void isValidSales() {
        // null sales
        assertThrows(NullPointerException.class, () -> Money.isValidMoney(null));

        // invalid sales format
        assertFalse(Money.isValidMoney("")); // empty string
        assertFalse(Money.isValidMoney(" ")); // spaces only
        assertFalse(Money.isValidMoney("price")); // non-numeric
        assertFalse(Money.isValidMoney("9011p041")); // alphabets within digits
        assertFalse(Money.isValidMoney("9312 1534")); // spaces within digits
        assertFalse(Money.isValidMoney("93121534")); // long sales

        // valid sales
        assertTrue(Money.isValidMoney("911")); // exactly 3 numbers
        assertTrue(Money.isValidMoney("1000000"));
    }

    @Test
    public void isValidAmount() {
        // invalid amount
        assertFalse(Money.isValidAmount((93121534))); // long amount
        assertFalse(Money.isValidAmount((10000000))); // long amount
        assertFalse(Money.isValidAmount((1000001))); // long amount

        // valid amount
        assertTrue(Money.isValidAmount((0))); // 0 amount
        assertTrue(Money.isValidAmount((999999))); // almost exceeds limit.

    }
}
