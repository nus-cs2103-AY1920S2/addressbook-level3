package csdev.couponstash.model.coupon.savings;

import static csdev.couponstash.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for MonetaryAmount.
 */
public class MonetaryAmountTest {

    @Test
    public void constructor_invalidMonetaryAmount_throwsIllegalArgumentException() {
        // invalid integer amount
        assertThrows(IllegalArgumentException.class, () -> new MonetaryAmount(-1, 0));
        // invalid decimal amount
        assertThrows(IllegalArgumentException.class, () -> new MonetaryAmount(1, 100));
        // invalid double amount
        assertThrows(IllegalArgumentException.class, () -> new MonetaryAmount(-0.5));
        // invalid String amount (more than 2 decimal place)
        assertThrows(IllegalArgumentException.class, () -> new MonetaryAmount("1.000"));
        // invalid String amount (dot is useless)
        assertThrows(IllegalArgumentException.class, () -> new MonetaryAmount("1."));
    }

    @Test
    public void constructor_stringInputWithOneDecimalPlace_addsZeroToDecimalValue() {
        // 1.3 is interpreted as 1.30
        assertEquals(new MonetaryAmount(1, 30), new MonetaryAmount("1.3"));
        // 1.03 is interpreted as 1.03
        assertEquals(new MonetaryAmount(1, 3), new MonetaryAmount("1.03"));
    }

    @Test
    public void getValue_monetaryAmountWithZeroes_getsCorrectDouble() {
        // 1, 30 translates to 1.3
        assertEquals(new MonetaryAmount(1, 30).getValue(), 1.3);
        // 1, 3 translates to 1.03
        assertEquals(new MonetaryAmount(1, 3).getValue(), 1.03);
    }

    @Test
    public void add_validMonetaryAmount_worksAsExpected() {
        // 10.99 + 3.02 = 14.01
        assertEquals(new MonetaryAmount(10, 99).add(new MonetaryAmount(3, 2)),
                new MonetaryAmount(14, 1));
        // 10.90 + 3.50 = 14.40
        assertEquals(new MonetaryAmount(10, 90).add(new MonetaryAmount(3, 50)),
                new MonetaryAmount(14, 40));
    }

    @Test
    public void isValidMonetaryAmount() {
        // invalid monetaryAmount
        assertFalse(MonetaryAmount.isValidMonetaryAmount(-1, 0)); // negative integer amount
        assertFalse(MonetaryAmount.isValidMonetaryAmount(1, -1)); // negative decimal amount
        assertFalse(MonetaryAmount.isValidMonetaryAmount(1, 100)); // decimal amount 100 and above

        // valid monetaryAmount
        assertTrue(MonetaryAmount.isValidMonetaryAmount(1, 0));
    }
}
