package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ResumeBook} that keeps track of its own history.
 */
public class VersionedResumeBook extends ResumeBook {

    private final List<ReadOnlyResumeBook> resumeBookStateList;
    private int currentStatePointer;
    public VersionedResumeBook(ReadOnlyResumeBook initialState) {
        super(initialState);

        resumeBookStateList = new ArrayList<>();
        resumeBookStateList.add(new ResumeBook(initialState));
        currentStatePointer = 0;
    }
    /**
     * Saves a copy of the current {@code ResumeBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        resumeBookStateList.add(new ResumeBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        resumeBookStateList.subList(currentStatePointer + 1, resumeBookStateList.size()).clear();
    }

    /**
     * Restores the resume book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        /*
            This ugly chunk is to allow the current state, aka the latest command, to let the past state know what
            type of item it should display.
            e.g. undoing a internship deletion item should result in display of internship list

            Note that all states that can be redone would have had the correct display type since they have already
            been undone.
         */
        currentStatePointer--;
        resetData(resumeBookStateList.get(currentStatePointer));
        /* currentStatePointer + 1 now points to the just undone command, to whose displayType we want to set the
        VersionedResumeBook*/
        setItemsToDisplay(resumeBookStateList.get(currentStatePointer + 1).getDisplayType());
    }

    /**
     * Restores the resume book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(resumeBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has resume book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has resume book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < resumeBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedResumeBook)) {
            return false;
        }

        VersionedResumeBook otherVersionedResumeBook = (VersionedResumeBook) other;

        // state check
        return super.equals(otherVersionedResumeBook)
                && resumeBookStateList.equals(otherVersionedResumeBook.resumeBookStateList)
                && currentStatePointer == otherVersionedResumeBook.currentStatePointer;
    }

    public ReadOnlyResumeBook getStatelessResumeBook() {
        ResumeBook newResumeBook = new ResumeBook();
        newResumeBook.resetData(this);
        return newResumeBook;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        public static final String MESSAGE_NO_UNDO_STATE =
                "Current state pointer is at start of resumeBookState list, unable to undo.";
        private NoUndoableStateException() {
            super(MESSAGE_NO_UNDO_STATE);
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        public static final String MESSAGE_NO_REDO_STATE =
                "Current state pointer is at end of resumeBookState list, unable to redo.";
        private NoRedoableStateException() {
            super(MESSAGE_NO_REDO_STATE);
        }
    }
}
