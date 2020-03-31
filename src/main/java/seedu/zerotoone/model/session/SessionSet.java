package seedu.zerotoone.model.session;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

/**
 * Represents a Session Set in the exercise list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class SessionSet {
    /*
     * The first character of the exercise set must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final Weight weight;
    public final NumReps numReps;
    public final boolean isFinished;

    /**
     * Every field must be present and not null.
     */
    public SessionSet(Weight weight, NumReps numReps, boolean isFinished) {
        requireAllNonNull(weight, numReps);
        this.weight = weight;
        this.numReps = numReps;
        this.isFinished = isFinished;
    }

    public SessionSet(ExerciseSet exerciseSet, boolean isFinished) {
        requireAllNonNull(exerciseSet.getWeight(), exerciseSet.getNumReps());
        this.weight = exerciseSet.getWeight();
        this.numReps = exerciseSet.getNumReps();
        this.isFinished = isFinished;
    }

    public Weight getWeight() {
        return weight;
    }

    public NumReps getNumReps() {
        return numReps;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Weight: ")
                .append(getWeight())
                .append(" Number of repetitions: ")
                .append(getNumReps());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SessionSet)) {
            return false;
        }

        SessionSet otherExerciseSet = (SessionSet) other;
        return otherExerciseSet.getWeight().equals(getWeight())
                && otherExerciseSet.getNumReps().equals(getNumReps());
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, numReps);
    }

}
