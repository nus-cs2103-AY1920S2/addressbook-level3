//@@author gb3h
package seedu.zerotoone.model.session;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.zerotoone.model.exercise.ExerciseName;

/**
 * Represents an immutable Session once a session is completed.
 */
public class CompletedExercise {

    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final ExerciseName exerciseName;
    private final List<CompletedSet> sets;

    /**
     * Every field must be present and not null.
     */
    public CompletedExercise(ExerciseName name, List<CompletedSet> sets, LocalDateTime start, LocalDateTime end) {
        requireAllNonNull(name, sets, start, end);
        this.exerciseName = name;
        this.startTime = start;
        this.endTime = end;
        this.sets = sets;
    }

    public ExerciseName getExerciseName() {
        return this.exerciseName;
    }

    public List<CompletedSet> getSets() {
        return Collections.unmodifiableList(sets);
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * Returns true if both completed exercises have the same identity and data fields.
     * This defines a stronger notion of equality between two exercises.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CompletedExercise)) {
            return false;
        }

        CompletedExercise otherExercise = (CompletedExercise) other;
        return otherExercise.getExerciseName().equals(getExerciseName())
            && otherExercise.getSets().equals(getSets());
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseName, sets);
    }

    @Override
    public String toString() {
        return String.format("%s Completed Exercise set: %s", getExerciseName(), getSets().toString());
    }

}
