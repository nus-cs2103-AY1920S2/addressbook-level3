package seedu.address.model.parcel.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.parcel.parcelattributes.Warehouse;

class WarehouseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Warehouse(null));
    }

    @Test
    public void constructor_invalidWarehouseAddress_throwsIllegalArgumentException() {
        String invalid = "";
        assertThrows(IllegalArgumentException.class, () -> new Warehouse(invalid));
    }

    @Test
    public void isValidAddress() {
        // null address
        assertThrows(NullPointerException.class, () -> Warehouse.isValidAddress(null));

        // invalid address
        assertFalse(Warehouse.isValidAddress("")); // empty string
        assertFalse(Warehouse.isValidAddress("   ")); // string with spaces

        // valid address
        assertTrue(Warehouse.isValidAddress("13 Goose Rd, S048293"));
        assertTrue(Warehouse.isValidAddress("-")); // one character
        assertTrue(Warehouse.isValidAddress("S012948"));
    }

    @Test
    public void equals() {
        Warehouse w1 = new Warehouse("S049332");
        Warehouse w2 = new Warehouse("S060392");
        assertNotEquals(w1, w2);
        assertNotEquals(w2, w1);

        Warehouse w3 = new Warehouse("S049332");
        assertEquals(w1, w3);
    }
}
