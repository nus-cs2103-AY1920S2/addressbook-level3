package seedu.address.model.notes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class NotesTest {

    private static final String VALID_FILE_TYPE = "file";
    private static final String INVALID_FILE_TYPE = "DASDQWE";
    private static final String VALID_PATH_TYPE = "abs";
    private static final String INVALID_PATH_TYPE = "QEWDS";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null, "dummy"));
    }

    @Test
    public void constructor2_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null, "dummy", "dummy2"));
    }

    @Test
    public void constructor3_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null));
    }

    @Test
    public void validFileType() {
        assertTrue(Notes.isValidType(VALID_FILE_TYPE));
    }

    @Test
    public void invalidFileType() {
        assertFalse(Notes.isValidType(INVALID_FILE_TYPE));
    }

    @Test
    public void validPathType() {
        assertTrue(Notes.isValidPathType(VALID_PATH_TYPE));
    }

    @Test
    public void invalidPathType() {
        assertFalse(Notes.isValidPathType(INVALID_PATH_TYPE));
    }



}
