package seedu.zerotoone.model.util;

import java.util.ArrayList;
import java.util.List;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.exercise.Weight;

/**
 * Contains utility methods for populating {@code ExerciseList} with sample data.
 */
public class SampleExerciseDataUtil {
    public static Exercise[] getSampleExercises() {
        Exercise[] exercises = new Exercise[3];

        // Exercise 1
        ExerciseName exerciseOneName = new ExerciseName("Bench Press");
        List<ExerciseSet> exerciseOneSets = new ArrayList<>();
        exerciseOneSets.add(new ExerciseSet(new Weight("60"), new NumReps("5")));
        exerciseOneSets.add(new ExerciseSet(new Weight("60"), new NumReps("5")));
        exerciseOneSets.add(new ExerciseSet(new Weight("60"), new NumReps("5")));
        exercises[0] = new Exercise(exerciseOneName, exerciseOneSets);

        // Exercise 2
        ExerciseName exerciseTwoName = new ExerciseName("Overhead Press");
        List<ExerciseSet> exerciseTwoSets = new ArrayList<>();
        exerciseTwoSets.add(new ExerciseSet(new Weight("30"), new NumReps("10")));
        exerciseTwoSets.add(new ExerciseSet(new Weight("30"), new NumReps("10")));
        exerciseTwoSets.add(new ExerciseSet(new Weight("30"), new NumReps("10")));
        exercises[1] = new Exercise(exerciseTwoName, exerciseTwoSets);

        // Exercise 3
        ExerciseName exerciseThreeName = new ExerciseName("Triceps Pushdown");
        List<ExerciseSet> exerciseThreeSets = new ArrayList<>();
        exerciseThreeSets.add(new ExerciseSet(new Weight("15"), new NumReps("10")));
        exerciseThreeSets.add(new ExerciseSet(new Weight("15"), new NumReps("10")));
        exerciseThreeSets.add(new ExerciseSet(new Weight("15"), new NumReps("10")));
        exerciseThreeSets.add(new ExerciseSet(new Weight("15"), new NumReps("10")));
        exerciseThreeSets.add(new ExerciseSet(new Weight("15"), new NumReps("10")));
        exercises[2] = new Exercise(exerciseThreeName, exerciseThreeSets);

        return exercises;
    }

    public static ReadOnlyExerciseList getSampleExerciseList() {
        ExerciseList sampleExerciseList = new ExerciseList();
        for (Exercise exercise : getSampleExercises()) {
            sampleExerciseList.addExercise(exercise);
        }
        return sampleExerciseList;
    }

}
