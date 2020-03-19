package seedu.address.model.itemtype;

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
        assertThrows(NullPointerException.class, () -> TypeOfItem.isValidItemName(null));

        // invalid items
        assertFalse(TypeOfItem.isValidItemName("")); // empty string
        assertFalse(TypeOfItem.isValidItemName(" ")); // spaces only

        // valid items
        assertTrue(TypeOfItem.isValidItemName("Bottle")); //one word
        assertTrue(TypeOfItem.isValidItemName("ThisIsAVeryLongWord")); // one long word
    }
}
