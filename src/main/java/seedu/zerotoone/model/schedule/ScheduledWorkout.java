package seedu.zerotoone.model.schedule;

import java.util.Comparator;

import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Represents a scheduled workout in the scheduled workout list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ScheduledWorkout {

    private final Schedule schedule;
    private final WorkoutName workoutNameToSchedule;
    private final DateTime dateTime;
    private final DateTime dateTimeNow;

    public ScheduledWorkout(Schedule schedule,
                            WorkoutName workoutNameToSchedule,
                            DateTime dateTime,
                            DateTime dateTimeNow) {
        this.schedule = schedule;
        this.workoutNameToSchedule = workoutNameToSchedule;
        this.dateTime = dateTime;
        this.dateTimeNow = dateTimeNow;
    }

    public static Comparator<ScheduledWorkout> getComparator() {
        return new Comparator<ScheduledWorkout>() {
            @Override
            public int compare(ScheduledWorkout s1, ScheduledWorkout s2) {
                return s1.getDateTime().compareTo(s2.getDateTime());
            }
        };
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public WorkoutName getScheduledWorkoutName() {
        return workoutNameToSchedule;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public boolean isOutDated() {
        return dateTime.compareTo(dateTimeNow) < 0;
    }

    /**
     * Calls {@code equals} method.
     */
    public boolean isSameScheduledWorkout(ScheduledWorkout otherScheduledWorkout) {
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
