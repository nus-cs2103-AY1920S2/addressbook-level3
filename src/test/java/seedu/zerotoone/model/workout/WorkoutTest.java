package seedu.zerotoone.model.workout;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.ABS_WORKOUT;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.LEGS_WORKOUT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_EXERCISE_LUNGES;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_WORKOUT_NAME_LEGS_WORKOUT;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.testutil.workout.WorkoutBuilder;

public class WorkoutTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Workout workout = new WorkoutBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> workout.getWorkoutExercises().remove(0));
    }

    @Test
    public void isSameWorkout() {
        // same object -> returns true
        assertTrue(ABS_WORKOUT.isSameWorkout(ABS_WORKOUT));

        // null -> returns false
        assertFalse(ABS_WORKOUT.isSameWorkout(null));

        // same name, same attributes -> returns true
        Workout editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT).build();
        assertTrue(ABS_WORKOUT.isSameWorkout(editedAbsWorkout));

        // different name -> returns false
        editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutName(VALID_WORKOUT_NAME_LEGS_WORKOUT)
                .build();
        assertFalse(ABS_WORKOUT.isSameWorkout(editedAbsWorkout));

        // different workoutSet -> returns true
        editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutExercise(VALID_EXERCISE_LUNGES)
                .build();
        assertTrue(ABS_WORKOUT.isSameWorkout(editedAbsWorkout));

        // different name, different workoutSet -> returns false
        editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutName(VALID_WORKOUT_NAME_LEGS_WORKOUT)
                .withWorkoutExercise(VALID_EXERCISE_LUNGES)
                .build();
        assertFalse(ABS_WORKOUT.isSameWorkout(editedAbsWorkout));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Workout absWorkoutCopy = new WorkoutBuilder(ABS_WORKOUT).build();
        assertTrue(ABS_WORKOUT.equals(absWorkoutCopy));

        // same object -> returns true
        assertTrue(ABS_WORKOUT.equals(ABS_WORKOUT));

        // null -> returns false
        assertFalse(ABS_WORKOUT.equals(null));

        // different type -> returns false
        assertFalse(ABS_WORKOUT.equals(5));

        // different workout -> returns false
        assertFalse(ABS_WORKOUT.equals(LEGS_WORKOUT));

        // different name -> returns false
        Workout editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutName(VALID_WORKOUT_NAME_LEGS_WORKOUT).build();
        assertFalse(ABS_WORKOUT.equals(editedAbsWorkout));

        // different workoutSet -> returns false
        editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutExercise(VALID_EXERCISE_LUNGES).build();
        assertFalse(ABS_WORKOUT.equals(editedAbsWorkout));
    }
}
