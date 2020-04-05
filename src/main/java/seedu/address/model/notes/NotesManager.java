package seedu.address.model.notes;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Represents the Notes Manager, which serves to keep track of all notes.
 * NotesManager implements the interface ReadOnlyNotes
 */
public class NotesManager implements ReadOnlyNotes {

    private final UniqueNotesList notes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        notes = new UniqueNotesList();
    }

    public NotesManager() {}

    /**
     * Creates a list of Saved Notes using the notes in {@code toBeCopied}
     * @param toBeCopied list of assessments.
     */
    public NotesManager(ReadOnlyNotes toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the notes.
     * Must not contain duplicate notes.
     * @param notes refers to the list of notes.
     */
    public void setNotes(List<Notes> notes) {
        this.notes.setNotes(notes);
    }

    /**
     * Resets the existing data of this {@code notes} with {@code newData}
     * @param newData
     */
    public void resetData(ReadOnlyNotes newData) {
        requireNonNull(newData);
        setNotes(newData.getNotesList());
    }

    /**
     * Returns true if a note with the same identity as {@code note} exists in the list
     * @param note
     * @return
     */
    public boolean hasNote(Notes note) {
        requireNonNull(note);
        return notes.contains(note);
    }

    /**
     * Adds a note to the list of current notes
     * @param note
     */
    public void addNote(Notes note) {
        notes.add(note);
    }

    /**
     * Replaces the given Notes {@code toBeChanged} in the list with {@code editedNote}
     */
    public void setNote(Notes toBeChanged, Notes editedNote) {
        requireNonNull(editedNote);
        notes.setNote(toBeChanged, editedNote);
    }

    /**
     * Removes a note from the list of notes
     * @param note
     */
    public void removeNote(Notes note) {
        notes.remove(note);
    }

    @Override
    public String toString() {
        return notes.asUnmodifiableObservableList().size() + "students";
    }

    @Override
    public ObservableList<Notes> getNotesList() {
        return notes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NotesManager)
                && notes.equals(((NotesManager) other).notes);
    }

    @Override
    public int hashCode() {
        return notes.hashCode();
    }
}
