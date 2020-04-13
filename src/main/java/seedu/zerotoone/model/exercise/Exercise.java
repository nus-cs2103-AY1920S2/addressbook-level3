package seedu.zerotoone.model.exercise;

import static seedu.zerotoone.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;

/**
 * Represents a Exercise in the exercise list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Exercise {

    // Identity fields
    private final ExerciseName exerciseName;
    private final List<ExerciseSet> exerciseSets = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Every field must be present and not null.
     */
    public Exercise(ExerciseName exerciseName, List<ExerciseSet> exerciseSets) {
        requireAllNonNull(exerciseName, exerciseSets);
        logger.fine("Creating Exercise with Exercise Name: " + exerciseName
                + " and Exercise Sets: " + exerciseSets);
        this.exerciseName = exerciseName;
        this.exerciseSets.addAll(exerciseSets);
    }

    public ExerciseName getExerciseName() {
        logger.fine("Getting Exercise Name");
        return exerciseName;
    }

    public List<ExerciseSet> getExerciseSets() {
        logger.fine("Getting Exercise Sets");
        return Collections.unmodifiableList(exerciseSets);
    }

    /**
     * Returns true if both exercises of the same exerciseName.
     * This defines a weaker notion of equality between two exercises.
     */
    public boolean isSameExercise(Exercise otherExercise) {
        logger.fine("Checking if Exercise " + this.toString()
                + " is same as " + otherExercise);

        if (otherExercise == this) {
            return true;
        }

        return otherExercise != null
                && otherExercise.getExerciseName().equals(getExerciseName());
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
        return otherExercise.getExerciseName().equals(getExerciseName())
                && otherExercise.getExerciseSets().equals(getExerciseSets());
    }

    @Override
    public int hashCode() {
        return Objects.hash(exerciseName, exerciseSets);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getExerciseName())
                .append(" Exercise set: ")
                .append(getExerciseSets().toString());
        return builder.toString();
    }

}
