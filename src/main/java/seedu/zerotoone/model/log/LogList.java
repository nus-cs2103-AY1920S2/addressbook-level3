package seedu.zerotoone.model.log;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.zerotoone.model.session.CompletedExercise;
import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * Wraps all data at the session list level.
 */
public class LogList implements ReadOnlyLogList {

    private final ObservableList<CompletedWorkout> internalList;
    private final ObservableList<CompletedWorkout> internalUnmodifiableList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        internalList = FXCollections.observableArrayList();
        internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);
    }

    public LogList() {}

    /**
     * Creates an LogList using the Sessions in the {@code toBeCopied}
     */
    public LogList(ReadOnlyLogList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Session list with {@code Sessions}.
     * {@code Sessions} must not contain duplicate Sessions.
     */
    public void setCompletedWorkouts(List<CompletedWorkout> completedWorkouts) {
        requireNonNull(completedWorkouts);
        internalList.setAll(completedWorkouts);
    }

    /**
     * Resets the existing data of this {@code SessionList} with {@code newData}.
     */
    public void resetData(ReadOnlyLogList newData) {
        requireNonNull(newData);
        setCompletedWorkouts(newData.getLogList());
    }

    //// Log-level operations
    /**
     * Adds a CompletedWorkout to the Log list.
     */
    public void addCompletedWorkout(CompletedWorkout completedWorkout) {
        internalList.add(completedWorkout);
    }

    /**
     * Removes {@code key} from this {@code LogList}.
     * {@code key} must exist in the Log list.
     */
    public void removeCompletedWorkout(int key) {
        internalList.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return getLogList().size() + " Logs";
        // TODO: refine later
    }

    @Override
    public ObservableList<CompletedWorkout> getLogList() {
        return internalUnmodifiableList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LogList // instanceof handles nulls
            && internalList.equals(((LogList) other).internalList));
    }
}
