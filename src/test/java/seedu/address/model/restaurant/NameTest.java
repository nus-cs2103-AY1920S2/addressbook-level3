package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new seedu.address.model.restaurant.Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new seedu.address.model.restaurant.Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> seedu.address.model.restaurant.Name.isValidName(null));

        // invalid name
        assertFalse(seedu.address.model.restaurant.Name.isValidName("")); // empty string
        assertFalse(seedu.address.model.restaurant.Name.isValidName(" ")); // spaces only
        assertFalse(seedu.address.model.restaurant.Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(seedu.address.model.restaurant.Name.isValidName("dasd*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(seedu.address.model.restaurant.Name.isValidName("afsa sdads")); // alphabets only
        assertTrue(seedu.address.model.restaurant.Name.isValidName("12345")); // numbers only
        assertTrue(seedu.address.model.restaurant.Name.isValidName("pfas3 33as")); // alphanumeric characters
        assertTrue(seedu.address.model.restaurant.Name.isValidName("s sZSW sZ")); // with capital letters
        assertTrue(Name.isValidName("sdaZ fs C 3 ")); // long names
    }
}
