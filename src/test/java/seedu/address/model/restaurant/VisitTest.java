package seedu.address.model.restaurant;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VisitTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Visit(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Visit(invalidName));
    }

    @Test
    public void isValidVisit() {
        // null visit
        assertThrows(NullPointerException.class, () -> Visit.isValidVisit(null));

        // invalid visit
        assertFalse(Visit.isValidVisit("")); // empty string
        assertFalse(Visit.isValidVisit(" ")); // spaces only
        assertFalse(Visit.isValidVisit("^")); // only non-alphanumeric characters
        assertFalse(Visit.isValidVisit("dasd*")); // contains non-alphanumeric characters
        assertFalse(Visit.isValidVisit("afsa sdads")); // alphabets only
        assertFalse(Visit.isValidVisit("12345")); // numbers only
        assertFalse(Visit.isValidVisit("pfas3 33as")); // alphanumeric characters
        assertFalse(Visit.isValidVisit("s sZSW sZ")); // with capital letters
        assertFalse(Visit.isValidVisit("sdaZ fs C 3 ")); // long visits

        // valid visit
        assertTrue(Visit.isValidVisit("Yes"));
        assertTrue(Visit.isValidVisit("No"));
    }
}
