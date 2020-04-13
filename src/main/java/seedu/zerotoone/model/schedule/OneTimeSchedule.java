package seedu.zerotoone.model.schedule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Represents a one time schedule in the schedule list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class OneTimeSchedule implements Schedule {

    private final WorkoutName workoutNameToSchedule;
    private final DateTime dateTime;

    public OneTimeSchedule(WorkoutName workoutNameToSchedule, DateTime dateTime) {
        this.workoutNameToSchedule = workoutNameToSchedule;
        this.dateTime = dateTime;
    }

    @Override
    public WorkoutName getWorkoutNameToSchedule() {
        return workoutNameToSchedule;
    }

    @Override
    public DateTime getDateTime() {
        return dateTime;
    }

    @Override
    public Optional<List<ScheduledWorkout>> getScheduledWorkout(DateTime now) {
        return Optional.of(
                Collections.singletonList(new ScheduledWorkout(this, workoutNameToSchedule, dateTime, now)));
    }

    @Override
    public boolean isSameSchedule(Schedule other) {
        return equals(other);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OneTimeSchedule)) {
            return false;
        }

        OneTimeSchedule otherSchedule = (OneTimeSchedule) other;
        return otherSchedule.getWorkoutNameToSchedule().equals(getWorkoutNameToSchedule())
                && otherSchedule.getDateTime().equals(getDateTime());
    }

    @Override
    public String toString() {
        return workoutNameToSchedule.fullName + " at " + dateTime.toString();
    }
}
