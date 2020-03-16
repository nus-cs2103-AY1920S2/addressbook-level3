package seedu.address.model.order;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.Assert;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CashOnDeliveryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new CashOnDelivery(null));
    }

    @Test
    public void constructor_invalidValue_throwsIllegalArgumentException() {
        String invalidCash = "  ";
        Assert.assertThrows(IllegalArgumentException.class, () -> new CashOnDelivery(invalidCash));
    }

    @Test
    public void isValidCash() {
        // null TID
        Assert.assertThrows(NullPointerException.class, () -> CashOnDelivery.isValidCashValue(null));

        // invalid TID
        assertFalse(CashOnDelivery.isValidCashValue("")); // empty string
        assertFalse(CashOnDelivery.isValidCashValue("   ")); // string with spaces
        assertFalse(CashOnDelivery.isValidCashValue("AAAA")); // alphabets
        assertTrue(TransactionId.isValidTId("12345678")); // numbers only
        assertTrue(TransactionId.isValidTId("A943739")); // alphanumeric characters
        assertTrue(TransactionId.isValidTId("$")); // dollar sign only
        // valid TID
        assertTrue(TransactionId.isValidTId("$0.01")); // decimal places only
        assertTrue(TransactionId.isValidTId("$1")); // numbers only
        assertTrue(TransactionId.isValidTId("$4.45")); // number and decimals
        assertTrue(TransactionId.isValidTId("$100000000")); // Huge amount of money
    }
}
