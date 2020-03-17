package seedu.address.model.exercise;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class ExerciseSet {
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\d{1,}";

    public final Weight weight;
    public final NumReps numReps;
    public final Interval interval;

    /**
     * Every field must be present and not null.
     */
    public ExerciseSet(Weight weight, NumReps numReps, Interval interval) {
        requireAllNonNull(weight, numReps, interval);
        this.weight = weight;
        this.numReps = numReps;
        this.interval = interval;
    }

    public Weight getWeight() {
        return weight;
    }

    public NumReps getNumReps() {
        return numReps;
    }

    public Interval getInterval() {
        return interval;
    }

    /**
     * Returns true if a given string is a valid number of exercise sets.
     */
    public static boolean isValidExerciseSet(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Weight: ")
                .append(getWeight())
                .append(" Number of repetitions: ")
                .append(getNumReps())
                .append(" Interval: ")
                .append(getInterval());
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
                && otherExerciseSet.getNumReps().equals(getNumReps())
                && otherExerciseSet.getInterval().equals(getInterval());
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, numReps, interval);
    }

}
