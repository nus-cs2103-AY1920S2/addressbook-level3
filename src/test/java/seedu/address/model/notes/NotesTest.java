package seedu.address.model.notes;

import static org.junit.jupiter.api.Assertions.assertFalse;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NotesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Notes(null, null, null));
    }

    @Test
    public void constructor_invalidNotes_throwsIllegalArgumentException() {
        String invalidNote = "";
        String content = "Test Content";
        String priority = "LOW";
        assertThrows(IllegalArgumentException.class, () -> new Notes(invalidNote, content, priority));
    }

    @Test
    public void isValidNote() {
        assertThrows(NullPointerException.class, () -> Notes.isValidName(null));
        assertFalse(Notes.isValidName("James.Wong."));
        assertFalse(Notes.isValidName("Alice!!"));
        assertFalse(Notes.isValidName("Phua Lai Wee #455"));
        assertFalse(Notes.isValidName("Jane_Koh"));
        assertFalse(Notes.isValidName("Mr Chan C. W."));
    }

}
