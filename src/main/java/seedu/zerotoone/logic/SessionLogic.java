package seedu.zerotoone.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.zerotoone.model.session.CompletedSet;
import seedu.zerotoone.model.session.CompletedWorkout;
import seedu.zerotoone.model.session.OngoingSet;

/**
 * Logic for Session.
 */
public interface SessionLogic {
    /**
     * Returns the SessionList.
     *
     * @see seedu.zerotoone.model.Model#getLogList()
     */
    ObservableList<CompletedWorkout> getLogList();

    /**
     * Returns the OngoingSessionList.
     *
     * @see seedu.zerotoone.model.Model#getOngoingSetList()
     */
    ObservableList<OngoingSet> getOngoingSetList();

    ObservableList<CompletedSet> getLastSet();

    ObservableList<Integer> getTimerList();

    /** Returns an unmodifiable view of the filtered list of workouts.
     */
    ObservableList<CompletedWorkout> getFilteredLogList();

    /**
     * Returns the user prefs' session list file path.
     */
    Path getLogListFilePath();
}
