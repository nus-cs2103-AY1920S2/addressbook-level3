package seedu.address.model.product;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SalesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sales(null));
    }

    @Test
    public void constructor_invalidSales_throwsIllegalArgumentException() {
        String invalidSales = "";
        assertThrows(IllegalArgumentException.class, () -> new Sales(invalidSales));
    }

    @Test
    public void isValidSales() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Sales.isValidSales(null));

        // invalid phone numbers
        assertFalse(Sales.isValidSales("")); // empty string
        assertFalse(Sales.isValidSales(" ")); // spaces only
        assertFalse(Sales.isValidSales("price")); // non-numeric
        assertFalse(Sales.isValidSales("9011p041")); // alphabets within digits
        assertFalse(Sales.isValidSales("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Sales.isValidSales("911")); // exactly 3 numbers
        assertTrue(Sales.isValidSales("93121534"));
        assertTrue(Sales.isValidSales("124293842033123")); // long prices
    }
}
