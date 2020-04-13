package seedu.address.model.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.mod.ModLink.isValidModLink;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class ModLinkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ModLink(null));
    }

    @Test
    public void constructor_invalidModLink_throwsIllegalArgumentException() {
        String invalidModLink = "";
        assertThrows(IllegalArgumentException.class, () -> new ModLink(invalidModLink));
    }

    @Test
    void isValidModLink_invalidLinks_returnFalse() {
        assertFalse(isValidModLink("")); // null string
        assertFalse(isValidModLink("abcdef")); // no protocol
    }

    @Test
    void isValidModLink_validLinks_returnTrue() {
        assertTrue(isValidModLink("https://coursemology.org"));
        assertTrue(isValidModLink(" https://comp.nus.edu.sg/~cs1231"));
    }

    @Test
    void testToString() {
        ModLink modLink = new ModLink("https://comp.nus.edu.sg/~cs1231");
        assertEquals("https://comp.nus.edu.sg/~cs1231", modLink.toString());
    }
}
