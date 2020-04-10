package seedu.delino.model.parcel.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.delino.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.delino.model.parcel.parcelattributes.Address;

public class AddressTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Address(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Address(invalidAddress));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Address.isValidAddress(null));

        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355 S234567"));
        assertTrue(Address.isValidAddress("BLK 3004 UBI AVENUE 3, #02-96 S408860"));
        assertTrue(Address.isValidAddress("16 RAFFLES QUAY 17-00 HONG LEONG BUILDING S048581")); // long address
    }
}
