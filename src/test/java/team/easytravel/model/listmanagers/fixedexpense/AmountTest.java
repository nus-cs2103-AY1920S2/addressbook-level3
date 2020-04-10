package team.easytravel.model.listmanagers.fixedexpense;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class AmountTest {

    @Test
    public void constructorNullThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructorInvalidAmountThrowsIllegalArgumentException() {
        String invalidAmount = "";
        assertThrows(IllegalArgumentException.class, () -> new Amount(invalidAmount));
    }


    @Test
    public void isValidAmount() {

        // null Amount
        assertThrows(NullPointerException.class, () -> Amount.isValidAmount(null));

        // Blank Amount
        assertFalse(Amount.isValidAmount("")); // empty amount
        assertFalse(Amount.isValidAmount(" ")); // just spaces.

        // Amount with "$/¥" in front
        assertFalse(Amount.isValidAmount("$3000"));
        assertFalse(Amount.isValidAmount("¥3000"));

        //Not valid amount
        assertFalse(Amount.isValidAmount("1000.000"));

        //Valid amount
        assertTrue(Amount.isValidAmount("1000")); // Normal case
        assertTrue(Amount.isValidAmount("1")); // Only 1 digit
        assertTrue(Amount.isValidAmount("0")); // 0 included
        assertTrue(Amount.isValidAmount("0123")); //When a 0 is placed in front
        assertTrue(Amount.isValidAmount("10000000000000000000000")); // Very large number
    }

    @Test
    public void testToString() {
        assertEquals("1000.00", new Amount("1000.00").toString());
    }

    @Test
    public void testEquals() {
        assertEquals(new Amount("1000"), new Amount("1000")); // When two amounts are the same.
        assertNotEquals(new Amount("1000.00"), new Amount("2000.00")); // When two amounts are different.
    }

    @Test
    public void testHashCode() {
        // Equal Hashcode
        assertEquals(new Amount("1000.00").hashCode(), new Amount("1000.00").hashCode());

        //Non Equal Hashcode
        assertNotEquals(new Amount("1").hashCode(), new Amount("0").hashCode());
    }
}
