package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void isValidPrice() {
        // null price
        assertThrows(NullPointerException.class, () -> Price.isValidPrice(null));

        // invalid price
        assertFalse(Price.isValidPrice(" ")); // spaces only
        assertFalse(Price.isValidPrice("^")); // only non-alphanumeric characters
        assertFalse(Price.isValidPrice("dasd*")); // contains non-alphanumeric characters
        assertFalse(Price.isValidPrice("afsa sdads")); // alphabets only
        assertFalse(Price.isValidPrice("12345")); // numbers only
        assertFalse(Price.isValidPrice("pfas3 33as")); // alphanumeric characters
        assertFalse(Price.isValidPrice("s sZSW sZ")); // with capital letters
        assertFalse(Price.isValidPrice("sdaZ fs C 3 ")); // long prices
        assertFalse(Price.isValidPrice("$$$$$$$$$$$$$$$$$$"));

        // valid price
        assertTrue(Price.isValidPrice("")); // empty string
        assertTrue(Price.isValidPrice("$"));
        assertTrue(Price.isValidPrice("$$"));
        assertTrue(Price.isValidPrice("$$$"));
    }
}
