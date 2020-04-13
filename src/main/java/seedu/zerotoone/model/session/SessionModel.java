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
     *
     * @return the boolean
     */
    boolean isInSession();

    /**
     * Start session ongoing workout.
     *
     * @param workoutToStart  the workout to start
     * @param currentDateTime the current date time
     * @return the ongoing workout
     */
    OngoingWorkout startSession(Workout workoutToStart, LocalDateTime currentDateTime);

    /**
     * Stop session.
     *
     * @param currentDateTime the current date time
     */
    void stopSession(LocalDateTime currentDateTime);

    /**
     * Gets ongoing set list.
     *
     * @return the ongoing set list
     */
    ReadOnlyOngoingSetList getOngoingSetList();

    /**
     * Gets last set.
     *
     * @return the last set
     */
    ReadOnlyCompletedSetList getLastSet();

    /**
     * Gets timer list.
     *
     * @return the timer list
     */
    ReadOnlyTimerList getTimerList();

    /**
     * Gets current workout.
     *
     * @return the current workout
     */
    Optional<OngoingWorkout> getCurrentWorkout();

    /**
     * Skip completed set.
     *
     * @return the completed set
     */
    CompletedSet skip();

    /**
     * Done completed set.
     *
     * @return the completed set
     */
    CompletedSet done();

    /**
     * Has exercise left boolean.
     *
     * @return the boolean
     */
    Boolean hasExerciseLeft();
}
