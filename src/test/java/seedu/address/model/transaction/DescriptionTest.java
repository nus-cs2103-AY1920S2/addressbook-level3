package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_emptyDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertFalse(Description.isValidDescription(null));

        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription("   ")); // spaces only

        // valid description
        assertTrue(Description.isValidDescription("duck rice")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("328 katong laksa")); // alphanumeric characters
        assertTrue(Description.isValidDescription("P6 Tuition")); // with capital letters
        assertTrue(Description.isValidDescription("Fisherman's Friend")); // with non-alphanumeric characters
        assertTrue(Description.isValidDescription("Gift for Grace (Soft toy plushie)")); // long descriptions
    }

    @Test
    public void compareTo() {
        Description before = new Description("a");
        Description after = new Description("b");
        assertEquals(0, before.compareTo(before));
        assertTrue(before.compareTo(after) < 0);
        assertTrue(after.compareTo(before) > 0);
    }
}
