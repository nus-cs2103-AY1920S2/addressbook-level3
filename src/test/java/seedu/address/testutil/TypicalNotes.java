package seedu.address.testutil;

import seedu.address.model.notes.Notes;
import seedu.address.model.notes.NotesManager;

/**
 * Represents Typical Notes used for testing.
 */
public class TypicalNotes {
    public static final Notes[] TYPICAL_NOTES = {
        new Notes("Simon Lam", "Good Student", "LOW", "29/03/2020 22:40"),
        new Notes("Gerren Seow", "Good Student", "LOW", "29/03/2020 22:41"),
        new Notes("Lee Hui Ting", "Good Student", "LOW", "29/03/2020 22:42"),
        new Notes("Gary Syndromes", "Good Student", "LOW", "29/03/2020 22:43"),
        new Notes("Freddy Zhang", "Good Student", "LOW", "29/03/2020 22:44")
    };

    public static NotesManager getTypicalNotes() {
        NotesManager notesManager = new NotesManager();
        for (Notes note : TYPICAL_NOTES) {
            notesManager.addNote(note);
        }
        return notesManager;
    }
}
