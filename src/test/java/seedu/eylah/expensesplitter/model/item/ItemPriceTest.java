package seedu.eylah.expensesplitter.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class ItemPriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ItemPrice(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        assertThrows(IllegalArgumentException.class, () -> new ItemPrice(new BigDecimal(invalidPrice)));
    }

    @Test
    public void isValidItemPrice() {
        // null ItemPrice
        assertThrows(NullPointerException.class, () -> ItemPrice.isValidPrice(null));

        // invalid ItemPrice
        assertThrows(IllegalArgumentException.class, () -> new ItemPrice(new BigDecimal(" "))); // spaces only
        assertFalse(ItemPrice.isValidPrice(new BigDecimal(0))); // price must be greater than 0
        assertThrows(IllegalArgumentException.class, () -> new ItemPrice(new BigDecimal("phone"))); // non-numeric
        assertThrows(IllegalArgumentException.class, () -> new ItemPrice(new BigDecimal("9011p041")));
        // alphabets within digits
        assertThrows(IllegalArgumentException.class, () -> new ItemPrice(new BigDecimal("9011 4192")));
        // spaces within digits
        assertFalse(ItemPrice.isValidPrice(new BigDecimal("123.133"))); // cannot have more than 3 decimal digits

        // valid ItemPrice
        assertTrue(ItemPrice.isValidPrice(BigDecimal.valueOf(9))); // exactly 1 number
        assertTrue(ItemPrice.isValidPrice(BigDecimal.valueOf(9.84)));
        assertTrue(ItemPrice.isValidPrice(BigDecimal.valueOf(9999999.12))); // long item price
    }





}
