package seedu.recipe.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.recipe.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidQuantity(null));

        // invalid name
        assertFalse(Name.isValidQuantity("")); // empty string
        assertFalse(Name.isValidQuantity(" ")); // spaces only
        assertFalse(Name.isValidQuantity("^")); // only non-alphanumeric chars except permitted special characters.
        assertFalse(Name.isValidQuantity("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidQuantity("peter jack")); // alphabets only
        assertTrue(Name.isValidQuantity("12345")); // numbers only
        assertTrue(Name.isValidQuantity("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidQuantity("Capital Tan")); // with capital letters
        assertTrue(Name.isValidQuantity("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
