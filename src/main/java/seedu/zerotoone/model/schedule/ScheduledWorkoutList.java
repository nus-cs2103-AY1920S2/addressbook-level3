package seedu.zerotoone.model.schedule;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 */
public class ScheduledWorkoutList implements Iterable<ScheduledWorkout> {

    private final ObservableList<ScheduledWorkout> internalList = FXCollections.observableArrayList();
    private final ObservableList<ScheduledWorkout> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public ObservableList<ScheduledWorkout> getScheduledWorkoutList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<ScheduledWorkout> iterator() {
        return internalList.iterator();
    }
}
