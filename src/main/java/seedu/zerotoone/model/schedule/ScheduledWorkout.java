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

    public String getScheduledWorkoutName() {
        return workoutToSchedule.getExerciseName().fullName; // TO_CHANGE_EXERCISE_TO_WORKOUT
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    /**
     * STEPH_TODO: may not even need this
     */
    public boolean isSameScheduledWorkout(ScheduledWorkout otherScheduledWorkout) {
        // if (otherScheduledWorkout == this) {
        //     return true;
        // }
        //
        // return otherScheduledWorkout != null
        //         && otherScheduledWorkout.getScheduledWorkoutName().equals(getScheduledWorkoutName());

        return equals(otherScheduledWorkout);
    }

    /**
     * Returns true if both scheduledWorkouts have the same identity and data fields.
     * This defines a stronger notion of equality between two scheduledWorkouts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ScheduledWorkout)) {
            return false;
        }

        ScheduledWorkout otherScheduledWorkout = (ScheduledWorkout) other;
        return otherScheduledWorkout.getScheduledWorkoutName().equals(getScheduledWorkoutName())
                && otherScheduledWorkout.getDateTime().equals(getDateTime());
    }
}
