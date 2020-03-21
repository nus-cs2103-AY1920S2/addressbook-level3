package seedu.zerotoone.testutil.exercise;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;

/**
 * A utility class to help with building ExerciseList objects.
 * Example usage: <br>
 *     {@code ExerciseList exerciseList =
 *          new ExerciseListBuilder().withExercise(new Exercise(...)).build();}
 */
public class ExerciseListBuilder {

    private ExerciseList exerciseList;

    public ExerciseListBuilder() {
        exerciseList = new ExerciseList();
    }

    public ExerciseListBuilder(ExerciseList exerciseList) {
        this.exerciseList = exerciseList;
    }

    /**
     * Adds a new {@code Exercise} to the {@code ExerciseList} that we are building.
     */
    public ExerciseListBuilder withExercise(Exercise exercise) {
        exerciseList.addExercise(exercise);
        return this;
    }

    public ExerciseList build() {
        return exerciseList;
    }
}
