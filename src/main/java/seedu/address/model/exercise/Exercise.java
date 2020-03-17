package seedu.address.model.exercise;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Exercise in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exercise {

    // Identity fields
    private final Name name;
    private final ExerciseSet exerciseSet;

    /**
     * Every field must be present and not null.
     */
    public Exercise(Name name, ExerciseSet exerciseSet) {
        requireAllNonNull(name, exerciseSet);
        this.name = name;
        this.exerciseSet = exerciseSet;
    }

    public Name getName() {
        return name;
    }

    public ExerciseSet getExerciseSet() {
        return exerciseSet;
    }

    /**
     * Returns true if both exercises of the same name.
     * This defines a weaker notion of equality between two exercises.
     */
    public boolean isSameExercise(Exercise otherExercise) {
        if (otherExercise == this) {
            return true;
        }

        return otherExercise != null
                && otherExercise.getName().equals(getName());
    }

    /**
     * Returns true if both exercises have the same identity and data fields.
     * This defines a stronger notion of equality between two exercises.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Exercise)) {
            return false;
        }

        Exercise otherExercise = (Exercise) other;
        return otherExercise.getName().equals(getName())
                && otherExercise.getExerciseSet().equals(getExerciseSet());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, exerciseSet);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Exercise set: ")
                .append(getExerciseSet().toString());
        return builder.toString();
    }

}
