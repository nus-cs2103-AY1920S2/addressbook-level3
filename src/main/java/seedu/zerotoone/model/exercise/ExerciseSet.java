package seedu.zerotoone.model.exercise;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;

/**
 * Represents a Exercise Set in the exercise list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ExerciseSet {
    /*
     * The first character of the exercise set must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public final Weight weight;
    public final NumReps numReps;
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Every field must be present and not null.
     */
    public ExerciseSet(Weight weight, NumReps numReps) {
        requireAllNonNull(weight, numReps);
        logger.fine("Creating Exercise Set with Weight: "
                + weight + " and NumReps: " + numReps);
        this.weight = weight;
        this.numReps = numReps;
    }

    public Weight getWeight() {
        return weight;
    }

    public NumReps getNumReps() {
        return numReps;
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

        if (!(other instanceof ExerciseSet)) {
            return false;
        }

        ExerciseSet otherExerciseSet = (ExerciseSet) other;
        return otherExerciseSet.getWeight().equals(getWeight())
                && otherExerciseSet.getNumReps().equals(getNumReps());
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, numReps);
    }

}
