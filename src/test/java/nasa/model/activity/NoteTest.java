package nasa.model.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void isValidNote_validNote_true() {
        boolean result = Note.isValidNote("Remember to update UG and DG!");
        assertEquals(true, result);
    }

    @Test
    public void isValidNote_invalidNote_false() {
        /*
         * Test for empty string, or whitespace characters
         */
        assertEquals(false, Note.isValidNote("       "));
        assertEquals(false, Note.isValidNote(""));
        assertEquals(false, Note.isValidNote("\t\t\n"));
    }

    @Test
    public void note_validInstantiation_noException() throws IllegalArgumentException {
        Note note = new Note("Update UG and DG!");
        note = new Note("ABC");
        note = new Note("\t\t\tThe best in the world!");
        note = new Note("\n\t\nUpdate website headings!");
        note = new Note("..........");
    }

    @Test
    public void note_invalidInstantiation_exceptionThrown() {
        try {
            Note note = new Note("            \t\n");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("java.lang.IllegalArgumentException: " + Note.MESSAGE_CONSTRAINTS, e.toString());
        }
    }
}
