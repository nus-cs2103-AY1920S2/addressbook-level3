package seedu.address.model.parcel.itemtype;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TypeOfItemTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TypeOfItem(null));
    }

    @Test
    public void constructor_invalidComment_throwsIllegalArgumentException() {
        String invalidTypeOfItem = "*glass";
        assertThrows(IllegalArgumentException.class, () -> new TypeOfItem(invalidTypeOfItem));
    }

    @Test
    public void isValidItemType() {
        assertThrows(NullPointerException.class, () -> TypeOfItem.isValidItemType(null));

        // invalid items
        assertFalse(TypeOfItem.isValidItemType("")); // empty string
        assertFalse(TypeOfItem.isValidItemType(" ")); // spaces only

        // valid items
        assertTrue(TypeOfItem.isValidItemType("Bottle")); //one word
        assertTrue(TypeOfItem.isValidItemType("ThisIsAVeryLongWord")); // one long word
    }
}
