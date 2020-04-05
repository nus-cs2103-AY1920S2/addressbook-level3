package seedu.address.model.notes;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.notes.exceptions.DuplicateNotesException;
import seedu.address.model.notes.exceptions.NotesUnavailableException;

/**
 * A list of notes that enforces uniqueness between its elements and does not allow nulls.
 * A note is considered unique by comparing using {@code Note#isSameNote(Note)}. As such, adding and updating of
 * notes uses Note#isSameNote(Note) for equality so as to ensure that the note being added or updated is
 * unique in terms of identity in the UniqueNotesList. However, the removal of a note uses Note#equals(Object) so
 * as to ensure that the note with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Notes#isSameNote(Notes)
 */
public class UniqueNotesList implements Iterable<Notes> {
    private final ObservableList<Notes> internalList = FXCollections.observableArrayList();
    private final ObservableList<Notes> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent note as the given argument.
     */
    public boolean contains(Notes toBeChecked) {
        requireNonNull(toBeChecked);
        return internalList.stream().anyMatch((toBeChecked::isSameNote));
    }

    /**
     * Adds a note to the list.
     * The note must not already exist in the list.
     */
    public void add(Notes toBeAdded) {
        requireNonNull(toBeAdded);
        if (contains(toBeAdded)) {
            throw new DuplicateNotesException();
        }
        internalList.add(toBeAdded);
    }

    /**
     * Replaces the note {@code target} in the list with {@code editedNote}.
     * {@code target} must exist in the list.
     * The note identity of {@code editedNote} must not be the same as another existing note in the list.
     */
    public void setNote(Notes toBeChanged, Notes editedNote) {
        requireAllNonNull(toBeChanged, editedNote);

        int index = internalList.indexOf(toBeChanged);
        if (index == -1) {
            throw new NotesUnavailableException();
        }

        if (!toBeChanged.isSameNote(editedNote) && contains(editedNote)) {
            throw new DuplicateNotesException();
        }

        internalList.set(index, editedNote);
    }

    /**
     * Removes the equivalent note from the list.
     * The note must exist in the list.
     */
    public void remove(Notes toBeRemoved) {
        requireNonNull(toBeRemoved);
        if (!internalList.remove(toBeRemoved)) {
            throw new NotesUnavailableException();
        }
    }

    public void setNotes(UniqueNotesList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code notes}.
     * {@code notes} must not contain duplicate notes.
     */
    public void setNotes(List<Notes> notes) {
        requireNonNull(notes);
        if (!notesAreUnique(notes)) {
            throw new DuplicateNotesException();
        }
        internalList.setAll(notes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Notes> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Notes> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueNotesList // instanceof handles nulls
                && internalList.equals(((UniqueNotesList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code notes} contains only unique notes.
     */
    private boolean notesAreUnique(List<Notes> notes) {
        for (int i = 0; i < notes.size() - 1; i++) {
            for (int j = i + 1; j < notes.size(); j++) {
                if (notes.get(i).isSameNote(notes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    //    @Override
    //    public String toString() {
    //        StringBuilder sb = new StringBuilder();
    //        for (Notes note : internalList) {
    //            sb.append(note + "\n");
    //        }
    //        return sb.toString();
    //    }

}
