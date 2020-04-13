package seedu.zerotoone.model.session;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.zerotoone.model.workout.Workout;

/**
 * Model for Session.
 */
public interface SessionModel {
    /**
     * Returns true if a workout has currently started.
     */
    boolean isInSession();

    OngoingWorkout startSession(Workout workoutToStart, LocalDateTime currentDateTime);

    void stopSession(LocalDateTime currentDateTime);

    ReadOnlyOngoingSetList getOngoingSetList();

    ReadOnlyCompletedSetList getLastSet();

    ReadOnlyTimerList getTimerList();

    // todo write java docs

    Optional<OngoingWorkout> getCurrentWorkout();

    CompletedSet skip();

    CompletedSet done();

    Boolean hasExerciseLeft();
}
