package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MatricNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MatricNumber(null));
    }

    @Test
    public void constructor_invalidMatricNumber_throwsIllegalArgumentException() {
        String invalidMatricNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new MatricNumber(invalidMatricNumber));
    }

    @Test
    public void isValidMatricNumber() {
        // null matric number
        assertThrows(NullPointerException.class, () -> MatricNumber.isValidMatricNumber(null));

        // invalid matric numbers
        assertFalse(MatricNumber.isValidMatricNumber("")); // empty string
        assertFalse(MatricNumber.isValidMatricNumber(" ")); // spaces only
        assertFalse(MatricNumber.isValidMatricNumber("A123B")); // less than 7 numbers
        assertFalse(MatricNumber.isValidMatricNumber("test")); // non-numeric
        assertFalse(MatricNumber.isValidMatricNumber("A019fbf36H")); // alphabets within digits
        assertFalse(MatricNumber.isValidMatricNumber("A015 3965H")); // spaces within digits
        assertFalse(MatricNumber.isValidMatricNumber("A0151585")); // must end with an alphabet
        assertFalse(MatricNumber.isValidMatricNumber("a0123456b")); // alphabets must be capitalised
        assertFalse(MatricNumber.isValidMatricNumber("A012345678B")); // more than 7 numbers
        assertFalse(MatricNumber.isValidMatricNumber("0123456B")); // does not start with capital A
        assertFalse(MatricNumber.isValidMatricNumber("a0183936R")); // does not start with capital A
        assertFalse(MatricNumber.isValidMatricNumber("A0183936r")); // does not end with capital letter







        // valid matric numbers
        assertTrue(MatricNumber.isValidMatricNumber("A0156986H"));
    }
}
