package seedu.zerotoone.model.schedule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import seedu.zerotoone.model.exercise.Exercise;

/**
 * STEPH_TODO_JAVADOC
 */
public class OneTimeSchedule implements Schedule {

    private final Exercise workoutToSchedule; // TO_CHANGE_EXERCISE_TO_WORKOUT
    private final DateTime dateTime;

    public OneTimeSchedule(Exercise workoutToSchedule, DateTime dateTime) { // TO_CHANGE_EXERCISE_TO_WORKOUT
        this.workoutToSchedule = workoutToSchedule;
        this.dateTime = dateTime;
    }

    @Override
    public Exercise getWorkoutToSchedule() {
        return workoutToSchedule;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    @Override
    public Optional<List<ScheduledWorkout>> getScheduledWorkout() {
        ScheduledWorkout scheduledWorkout = new ScheduledWorkout(workoutToSchedule, dateTime);
        return Optional.of(Collections.singletonList(scheduledWorkout));
    }

    @Override
    public boolean isSameSchedule(Schedule other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OneTimeSchedule)) {
            return false;
        }

        OneTimeSchedule otherSchedule = (OneTimeSchedule) other;
        return otherSchedule.getScheduledWorkout().equals(getScheduledWorkout())
                && otherSchedule.getDateTime().equals(getDateTime());
    }
}
