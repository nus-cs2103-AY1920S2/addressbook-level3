package seedu.zerotoone.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NumRepsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NumReps(null));
    }

    @Test
    public void constructor_invalidNumReps_throwsIllegalArgumentException() {
        String invalidNumReps = "";
        assertThrows(IllegalArgumentException.class, () -> new NumReps(invalidNumReps));
    }

    @Test
    public void isValidNumReps() {
        // null phone number
        assertThrows(NullPointerException.class, () -> NumReps.isValidNumReps(null));

        // invalid phone numbers
        assertFalse(NumReps.isValidNumReps("")); // empty string
        assertFalse(NumReps.isValidNumReps(" ")); // spaces only
        assertFalse(NumReps.isValidNumReps("asdf")); // non-numeric
        assertFalse(NumReps.isValidNumReps("9011p041")); // alphabets within digits
        assertFalse(NumReps.isValidNumReps("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(NumReps.isValidNumReps("911"));
        assertTrue(NumReps.isValidNumReps("93121534"));
        assertTrue(NumReps.isValidNumReps("124293842033123")); // long numbers
    }
}
