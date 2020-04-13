package seedu.address.model.facilitator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OfficeTest {

    @Test
    public void constructor_null_returnsNullOffice() {
        Office office = new Office(null);
        assertNull(office.value);
    }

    @Test
    public void constructor_invalidOffice_throwsIllegalArgumentException() {
        String invalidOffice = "";
        assertThrows(IllegalArgumentException.class, () -> new Office(invalidOffice));
    }

    @Test
    public void isValidOffice() {
        // null office
        assertThrows(NullPointerException.class, () -> Office.isValidOffice(null));

        // invalid offices
        assertFalse(Office.isValidOffice("")); // empty string
        assertFalse(Office.isValidOffice(" ")); // spaces only

        // valid offices
        assertTrue(Office.isValidOffice("Blk 456, Den Road, #01-355"));
        assertTrue(Office.isValidOffice("-")); // one character
        assertTrue(Office.isValidOffice("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long office
    }
}
