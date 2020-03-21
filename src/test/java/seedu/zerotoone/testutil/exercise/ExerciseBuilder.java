package seedu.zerotoone.testutil.exercise;

import java.util.ArrayList;
import java.util.List;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;

/**
 * A utility class to help with building Exercise objects.
 */
public class ExerciseBuilder {

    public static final String DEFAULT_EXERCISE_NAME = "Bench Press";

    private ExerciseName exerciseName;
    private List<ExerciseSet> exerciseSets;

    public ExerciseBuilder() {
        exerciseName = new ExerciseName(DEFAULT_EXERCISE_NAME);
        exerciseSets = new ArrayList<>();
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code exerciseToCopy}.
     */
    public ExerciseBuilder(Exercise exerciseToCopy) {
        exerciseName = exerciseToCopy.getExerciseName();
        exerciseSets = exerciseToCopy.getExerciseSets();
    }

    /**
     * Sets the {@code ExerciseName} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withExerciseName(String exerciseName) {
        this.exerciseName = new ExerciseName(exerciseName);
        return this;
    }

    /**
     * Sets the {@code exerciseSet} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withExerciseSet(String weight, String numReps) {
        List<ExerciseSet> exerciseSetsCopy = new ArrayList<>(exerciseSets);
        exerciseSetsCopy.add(new ExerciseSet(new Weight(weight), new NumReps(numReps)));
        exerciseSets = exerciseSetsCopy;
        return this;
    }

    public Exercise build() {
        return new Exercise(exerciseName, exerciseSets);
    }

}
