//@@author fatin99

package tatracker.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MatricTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Matric(null));
    }

    @Test
    public void constructor_invalidMatric_throwsIllegalArgumentException() {
        String invalidMatric = "B1234567Y";
        assertThrows(IllegalArgumentException.class, () -> new Matric(invalidMatric));
    }

    @Test
    public void isValidMatric() {
        // null matric
        assertThrows(NullPointerException.class, () -> Matric.isValidMatric(null));

        // blank matric
        assertFalse(Matric.isValidMatric(" ")); // spaces only

        // missing parts
        assertFalse(Matric.isValidMatric("0187945J")); // missing letter A at the front
        assertFalse(Matric.isValidMatric("A0187945")); // missing final letter
        assertFalse(Matric.isValidMatric("A187945J")); // missing one digit

        // invalid parts
        assertFalse(Matric.isValidMatric("B0187945J")); //letter B in the front instead of letter A
        assertFalse(Matric.isValidMatric("A00187945J")); //8 digits rather than 7
        assertFalse(Matric.isValidMatric("AA0187945J")); //2 As at the start rather than 1
        assertFalse(Matric.isValidMatric("A0187945JJ")); //2 letters at the end rather than 1

        // valid matric
        assertTrue(Matric.isValidMatric("A0187945J"));
    }
}
