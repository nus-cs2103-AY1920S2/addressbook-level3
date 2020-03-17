package seedu.zerotoone.testutil;

import seedu.zerotoone.model.ExerciseList;
import seedu.zerotoone.model.person.Person;

/**
 * A utility class to help with building ExerciseList objects.
 * Example usage: <br>
 *     {@code ExerciseList ab = new ExerciseListBuilder().withPerson("John", "Doe").build();}
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
     * Adds a new {@code Person} to the {@code ExerciseList} that we are building.
     */
    public ExerciseListBuilder withPerson(Person person) {
        exerciseList.addPerson(person);
        return this;
    }

    public ExerciseList build() {
        return exerciseList;
    }
}
