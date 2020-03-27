package seedu.zerotoone.model.schedule;

import seedu.zerotoone.model.exercise.Exercise;

/**
 * STEPH_TODO_JAVADOC
 */
public class ScheduledWorkout {

    private final Exercise workoutToSchedule; // TO_CHANGE_EXERCISE_TO_WORKOUT
    private final DateTime dateTime;

    public ScheduledWorkout(Exercise workoutToSchedule, DateTime dateTime) {
        this.workoutToSchedule = workoutToSchedule;
        this.dateTime = dateTime;
    }
}
