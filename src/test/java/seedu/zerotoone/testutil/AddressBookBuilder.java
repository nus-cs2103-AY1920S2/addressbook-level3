package seedu.zerotoone.testutil;

import seedu.zerotoone.model.ExerciseList;
import seedu.zerotoone.model.exercise.Exercise;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ExerciseList ab = new ExerciseListBuilder().withExercise("John", "Doe").build();}
 */
public class ExerciseListBuilder {

    private ExerciseList addressBook;

    public ExerciseListBuilder() {
        addressBook = new ExerciseList();
    }

    public ExerciseListBuilder(ExerciseList addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Exercise} to the {@code ExerciseList} that we are building.
     */
    public ExerciseListBuilder withExercise(Exercise exercise) {
        addressBook.addExercise(exercise);
        return this;
    }

    public ExerciseList build() {
        return addressBook;
    }
}
