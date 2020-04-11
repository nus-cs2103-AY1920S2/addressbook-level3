//@@author gb3h
package seedu.zerotoone.model.session;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.zerotoone.model.workout.WorkoutName;

/**
 * Represents an immutable Log once a session is completed.
 */
public class CompletedWorkout {

    // Identity fields
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final WorkoutName workoutName;
    private final List<CompletedExercise> exercises;

    /**
     * Every field must be present and not null.
     */
    public CompletedWorkout(WorkoutName name, List<CompletedExercise> exercises, LocalDateTime start,
        LocalDateTime end) {
        requireAllNonNull(name, exercises, start, end);
        this.workoutName = name;
        this.startTime = start;
        this.endTime = end;
        this.exercises = exercises;
    }

    public WorkoutName getWorkoutName() {
        return this.workoutName;
    }

    public List<CompletedExercise> getExercises() {
        return Collections.unmodifiableList(exercises);
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * Returns true if both workouts have the same identity and data fields.
     * This defines a stronger notion of equality between two workouts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CompletedWorkout)) {
            return false;
        }

        CompletedWorkout otherWorkout = (CompletedWorkout) other;
        return otherWorkout.getWorkoutName().equals(getWorkoutName())
            && otherWorkout.getExercises().equals(getExercises());
    }

    @Override
    public int hashCode() {
        return Objects.hash(workoutName, exercises);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getWorkoutName())
            .append(" Completed Workout set: ")
            .append(getExercises().toString());
        return builder.toString();
    }
}
