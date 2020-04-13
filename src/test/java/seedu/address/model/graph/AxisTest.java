package seedu.address.model.graph;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AxisTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Axis(null));
    }

    @Test
    public void constructor_invalidAxis_throwsIllegalArgumentException() {
        String invalidAxis = "";
        assertThrows(IllegalArgumentException.class, () -> new Axis(invalidAxis));
    }

    @Test
    public void isValidAxis() {
        // null axis
        assertThrows(NullPointerException.class, () -> Axis.isValidAxis(null));

        // invalid axis
        assertFalse(Axis.isValidAxis("")); // empty string
        assertFalse(Axis.isValidAxis(" ")); // spaces only
        assertFalse(Axis.isValidAxis("^")); // only non-alphanumeric characters
        assertFalse(Axis.isValidAxis("reps*")); // contains non-alphanumeric characters

        // valid axis
        assertTrue(Axis.isValidAxis("reps")); // small letters
        assertTrue(Axis.isValidAxis("Reps")); // with capital letters
        assertTrue(Axis.isValidAxis("weIgHt")); // with capital letters
        assertTrue(Axis.isValidAxis("WEIGHT")); // with all caps

    }
}
