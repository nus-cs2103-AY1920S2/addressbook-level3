package seedu.zerotoone.model.util;

import java.util.ArrayList;
import java.util.List;

import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseName;
import seedu.zerotoone.model.exercise.ExerciseSet;
import seedu.zerotoone.model.exercise.NumReps;
import seedu.zerotoone.model.exercise.Weight;
import seedu.zerotoone.model.workout.Workout;
import seedu.zerotoone.model.workout.WorkoutList;
import seedu.zerotoone.model.workout.WorkoutName;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;

/**
 * Contains utility methods for populating {@code WorkoutList} with sample data.
 */
public class SampleWorkoutDataUtil {
    public static Workout[] getSampleWorkouts() {
        Workout[] workouts = new Workout[3];

        // Workout 1
        ExerciseName exerciseOneName = new ExerciseName("Bench Press");
        List<ExerciseSet> exerciseOneSets = new ArrayList<>();
        exerciseOneSets.add(new ExerciseSet(new Weight("60"), new NumReps("5")));
        exerciseOneSets.add(new ExerciseSet(new Weight("60"), new NumReps("5")));

        WorkoutName workoutOneName = new WorkoutName("Legs Day");
        List<Exercise> workoutOneExercises = new ArrayList<>();
        workoutOneExercises.add(new Exercise(exerciseOneName, exerciseOneSets));
        workoutOneExercises.add(new Exercise(exerciseOneName, exerciseOneSets));
        workoutOneExercises.add(new Exercise(exerciseOneName, exerciseOneSets));
        workouts[0] = new Workout(workoutOneName, workoutOneExercises);

        // Workout 2
        ExerciseName exerciseTwoName = new ExerciseName("Overhead Press");
        List<ExerciseSet> exerciseTwoSets = new ArrayList<>();
        exerciseTwoSets.add(new ExerciseSet(new Weight("30"), new NumReps("10")));
        exerciseTwoSets.add(new ExerciseSet(new Weight("30"), new NumReps("10")));

        WorkoutName workoutTwoName = new WorkoutName("Arms Day");
        List<Exercise> workoutTwoExercises = new ArrayList<>();
        workoutTwoExercises.add(new Exercise(exerciseTwoName, exerciseTwoSets));
        workoutTwoExercises.add(new Exercise(exerciseTwoName, exerciseTwoSets));
        workoutTwoExercises.add(new Exercise(exerciseTwoName, exerciseTwoSets));
        workouts[1] = new Workout(workoutTwoName, workoutTwoExercises);

        // Workout 3
        ExerciseName exerciseThreeName = new ExerciseName("Triceps Pushdown");
        List<ExerciseSet> exerciseThreeSets = new ArrayList<>();
        exerciseThreeSets.add(new ExerciseSet(new Weight("15"), new NumReps("10")));
        exerciseThreeSets.add(new ExerciseSet(new Weight("15"), new NumReps("10")));
        exerciseThreeSets.add(new ExerciseSet(new Weight("15"), new NumReps("10")));

        WorkoutName workoutThreeName = new WorkoutName("Strength");
        List<Exercise> workoutThreeExercises = new ArrayList<>();
        workoutThreeExercises.add(new Exercise(exerciseThreeName, exerciseThreeSets));
        workoutThreeExercises.add(new Exercise(exerciseThreeName, exerciseThreeSets));
        workoutThreeExercises.add(new Exercise(exerciseThreeName, exerciseThreeSets));
        workouts[2] = new Workout(workoutThreeName, workoutThreeExercises);

        return workouts;
    }

    public static ReadOnlyWorkoutList getSampleWorkoutList() {
        WorkoutList sampleWorkoutList = new WorkoutList();
        for (Workout workout : getSampleWorkouts()) {
            sampleWorkoutList.addWorkout(workout);
        }
        return sampleWorkoutList;
    }

}
