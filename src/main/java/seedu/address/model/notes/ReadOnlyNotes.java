package seedu.address.model.notes;

import javafx.collections.ObservableList;

/**
 *  Unmodifiable view of Notes
 */
public interface ReadOnlyNotes {
    /**
     * Returns an unmodifiable view of the all the Notes.
     * This list will not contain any duplicate assessment.
     */
    ObservableList<Notes> getNotesList();
}
