package seedu.zerotoone.model.log;

import javafx.collections.ObservableList;
import seedu.zerotoone.model.session.CompletedWorkout;

/**
 * Unmodifiable view of an log list
 */
public interface ReadOnlyLogList {

    /**
     * Returns an unmodifiable view of the completed workouts list.
     */
    ObservableList<CompletedWorkout> getLogList();

}
