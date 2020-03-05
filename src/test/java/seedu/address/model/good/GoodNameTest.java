package seedu.address.model.good;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.good.GoodName.isValidGoodName;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GoodNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new GoodName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new GoodName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> isValidGoodName(null));

        // invalid name
        assertFalse(isValidGoodName("")); // empty string
        assertFalse(isValidGoodName(" ")); // spaces only
        assertFalse(isValidGoodName("^")); // only non-alphanumeric characters
        assertFalse(isValidGoodName("apple*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(isValidGoodName("fuji apple")); // alphabets only
        assertTrue(isValidGoodName("12345")); // numbers only
        assertTrue(isValidGoodName("2nd Gen fuji apple")); // alphanumeric characters
        assertTrue(isValidGoodName("Fuji apple")); // with capital letters
        assertTrue(isValidGoodName("fuji apple with very very very long name")); // long names
    }
}
