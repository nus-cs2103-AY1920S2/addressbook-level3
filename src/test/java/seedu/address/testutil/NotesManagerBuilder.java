package seedu.address.testutil;

import seedu.address.model.notes.Notes;
import seedu.address.model.notes.NotesManager;

/**
 * A utility class to help with building NotesManager objects.
 */
public class NotesManagerBuilder {
    private NotesManager notesManager;

    public NotesManagerBuilder() {
        notesManager = new NotesManager();
    }

    public NotesManagerBuilder(NotesManager notesManager) {
        this.notesManager = notesManager;
    }

    /**
     * Constructs a NotesManagerBuilder with notes.
     * @param note
     * @return
     */
    public NotesManagerBuilder withNotes(Notes note) {
        notesManager.addNote(note);
        return this;
    }

    public NotesManager build() {
        return notesManager;
    }
}
