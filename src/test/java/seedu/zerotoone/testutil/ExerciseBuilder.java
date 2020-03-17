package seedu.zerotoone.testutil;

// import java.util.HashSet;
// import java.util.Set;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.Interval;
import seedu.zerotoone.model.exercise.Name;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
// import seedu.zerotoone.model.util.SampleDataUtil;

/**
 * A utility class to help with building Exercise objects.
 */
public class ExerciseBuilder {

    public static final String DEFAULT_NAME = "Bench Press";
    public static final String DEFAULT_WEIGHT = "30";
    public static final String DEFAULT_NUM_REPS = "3";
    public static final String DEFAULT_INTERVAL = "120";

    private Name name;
    private ExerciseSet exerciseSet;

    public ExerciseBuilder() {
        name = new Name(DEFAULT_NAME);
        exerciseSet = new ExerciseSet(
                new Weight(DEFAULT_WEIGHT), new NumReps(DEFAULT_NUM_REPS), new Interval(DEFAULT_INTERVAL));
    }

    /**
     * Initializes the ExerciseBuilder with the data of {@code personToCopy}.
     */
    public ExerciseBuilder(Exercise personToCopy) {
        name = personToCopy.getName();
        exerciseSet = personToCopy.getExerciseSet();
    }

    /**
     * Sets the {@code Name} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code exerciseSet} of the {@code Exercise} that we are building.
     */
    public ExerciseBuilder withExerciseSet(String weight, String numReps, String interval) {
        this.exerciseSet = new ExerciseSet(new Weight(weight), new NumReps(numReps), new Interval(interval));
        return this;
    }

    public Exercise build() {
        return new Exercise(name, exerciseSet);
    }

}
