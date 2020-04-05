package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;


public class NextOfKinTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NextOfKin(null));
    }

    @Test
    public void constructor_invalidNok_throwsIllegalArgumentException() {
        String invalidNok = "";
        assertThrows(IllegalArgumentException.class, () -> new NextOfKin(invalidNok));
    }

    @Test
    public void isValidTNok() {
        // null temperatures
        assertThrows(NullPointerException.class, () -> NextOfKin.isValidNok(null));

        // invalid NOK
        assertFalse(NextOfKin.isValidNok("")); // empty string
        assertFalse(NextOfKin.isValidNok(" ")); // spaces only
        assertFalse(NextOfKin.isValidNok("nok")); // non-numeric

        // valid NOK
        assertTrue(NextOfKin.isValidNok("Insert NOK details here!")); // Insert comment allowed
        assertTrue(NextOfKin.isValidNok("jon-father-12345")); // correct format
    }
}
