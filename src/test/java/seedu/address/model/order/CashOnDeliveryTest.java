package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Parcel.order.CashOnDelivery;
import seedu.address.model.Parcel.ParcelAttributes.TransactionId;
import seedu.address.testutil.Assert;

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
        assertFalse(TransactionId.isValidTid("")); // empty string
        assertFalse(TransactionId.isValidTid("   ")); // string with spaces
        assertTrue(TransactionId.isValidTid("12345678")); // numbers only
        assertTrue(TransactionId.isValidTid("A943739")); // alphanumeric characters
        assertTrue(TransactionId.isValidTid("$")); // dollar sign only
        // valid TID
        assertTrue(TransactionId.isValidTid("$0.01")); // decimal places only
        assertTrue(TransactionId.isValidTid("$1")); // numbers only
        assertTrue(TransactionId.isValidTid("$4.45")); // number and decimals
        assertTrue(TransactionId.isValidTid("$100000000")); // Huge amount of money
    }
}
