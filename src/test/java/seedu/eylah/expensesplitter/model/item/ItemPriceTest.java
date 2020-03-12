package seedu.eylah.expensesplitter.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ItemPriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemPrice(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new ItemPrice(Double.valueOf(invalidPhone)));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> ItemPrice.isValidPrice(null));

        // invalid phone numbers
        assertFalse(ItemPrice.isValidPrice(Double.valueOf(""))); // empty string
        assertFalse(ItemPrice.isValidPrice(Double.valueOf(" "))); // spaces only
        assertFalse(ItemPrice.isValidPrice(Double.valueOf("0"))); // price must be greater than 0
        assertFalse(ItemPrice.isValidPrice(Double.valueOf("phone"))); // non-numeric
        assertFalse(ItemPrice.isValidPrice(Double.valueOf("9011p041"))); // alphabets within digits
        assertFalse(ItemPrice.isValidPrice(Double.valueOf("9312 1534"))); // spaces within digits

        // valid phone numbers
        assertTrue(ItemPrice.isValidPrice(Double.valueOf("9"))); // exactly 1 number
        assertTrue(ItemPrice.isValidPrice(Double.valueOf("9.84")));
        assertTrue(ItemPrice.isValidPrice(Double.valueOf("124293842033123"))); // long phone numbers
    }





}
