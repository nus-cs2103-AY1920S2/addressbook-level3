package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    @Test
    public void constructor_null_returnsNullDescription() {
        Description description = new Description(null);
        assertNull(description.value);
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("very very very very"
                + " very very very very very very very very very long description")); // long description

        // valid description
        assertTrue(Description.isValidDescription("software engineering")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("software engineering 12345")); // alphanumeric characters
        assertTrue(Description.isValidDescription("Software Engineering")); // with capital letters
        assertTrue(Description.isValidDescription("-")); // one character
        assertTrue(Description.isValidDescription("^")); // only non-alphanumeric characters
        assertTrue(Description.isValidDescription("software engineering*")); // contains non-alphanumeric characters
    }
}
