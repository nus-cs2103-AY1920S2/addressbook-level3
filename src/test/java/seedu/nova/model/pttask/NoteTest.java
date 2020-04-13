package seedu.nova.model.pttask;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.nova.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.nova.model.progresstracker.PtNote;

public class NoteTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PtNote(null));
    }

    @Test
    public void isValidNote() {
        assertFalse(PtNote.isValidNote(""));

        assertTrue(PtNote.isValidNote("note"));
    }
}
