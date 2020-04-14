package fithelper.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code FitHelper} that keeps track of its own history.
 */
public class VersionedFitHelper extends FitHelper {

    private final List<FitHelperCommit> fitHelperStateList;
    private int currentStatePointer;
    private boolean isTrackingEnabled = true;

    /**
     * Creates a {@code VersionedFitHelper} with an initial {@code ReadOnlyFitHelper}.
     */
    public VersionedFitHelper(ReadOnlyFitHelper initialState) {
        super(initialState);

        fitHelperStateList = new ArrayList<>();
        fitHelperStateList.add(new FitHelperCommit(new FitHelper(initialState), "initial commit"));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code FitHelper} state at the end of the state list if version tracking is enabled.
     * If tracking is not enabled, does nothing.
     *
     * @param commitMessage the message describing the details of the commit
     */
    public void commit(String commitMessage) {
        if (isTrackingEnabled) {
            removeStatesAfterCurrentPointer();
            fitHelperStateList.add(new FitHelperCommit(new FitHelper(this), commitMessage));
            currentStatePointer++;
        }
    }

    public void setVersionControl(boolean isEnabled) {
        this.isTrackingEnabled = isEnabled;
    }

    private void removeStatesAfterCurrentPointer() {
        fitHelperStateList.subList(currentStatePointer + 1, fitHelperStateList.size()).clear();
    }

    /**
     * Restores FitHelper to its previous state.
     * @return the commit message of the current state.
     */
    public String undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(fitHelperStateList.get(currentStatePointer).fitHelper);
        return fitHelperStateList.get(currentStatePointer + 1).commitMessage;
    }

    /**
     * Restores the baking home to its previously undone state.
     * @return the commit message of the previous state.
     */
    public String redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(fitHelperStateList.get(currentStatePointer).fitHelper);

        return fitHelperStateList.get(currentStatePointer).commitMessage;
    }

    /**
     * Returns true if {@code undo()} has baking home states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has baking home states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < fitHelperStateList.size() - 1;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("NoUndoableStateException: Unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("NoRedoableStateException: Unable to redo.");
        }
    }

}
