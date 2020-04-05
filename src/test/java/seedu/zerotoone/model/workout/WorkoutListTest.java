package seedu.zerotoone.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.ABS_WORKOUT;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.getTypicalWorkoutList;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_EXERCISE_DUMBBELL_CRUNCH;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.zerotoone.model.workout.exceptions.DuplicateWorkoutException;
import seedu.zerotoone.testutil.workout.WorkoutBuilder;

public class WorkoutListTest {

    private final WorkoutList workoutList = new WorkoutList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), workoutList.getWorkoutList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> workoutList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyWorkoutList_replacesData() {
        WorkoutList newData = getTypicalWorkoutList();
        workoutList.resetData(newData);
        assertEquals(newData, workoutList);
    }

    @Test
    public void resetData_withDuplicateWorkouts_throwsDuplicateWorkoutException() {
        // Two workouts with the same identity fields
        Workout editedArmsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutExercise(VALID_EXERCISE_DUMBBELL_CRUNCH).build();
        List<Workout> newWorkouts = Arrays.asList(ABS_WORKOUT, editedArmsWorkout);
        WorkoutListStub newData = new WorkoutListStub(newWorkouts);

        assertThrows(DuplicateWorkoutException.class, () -> workoutList.resetData(newData));
    }

    @Test
    public void hasWorkout_nullWorkout_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> workoutList.hasWorkout(null));
    }

    @Test
    public void hasWorkout_workoutNotInWorkoutList_returnsFalse() {
        assertFalse(workoutList.hasWorkout(ABS_WORKOUT));
    }

    @Test
    public void hasWorkout_workoutInWorkoutList_returnsTrue() {
        workoutList.addWorkout(ABS_WORKOUT);
        assertTrue(workoutList.hasWorkout(ABS_WORKOUT));
    }

    @Test
    public void hasWorkout_workoutWithSameIdentityFieldsInWorkoutList_returnsTrue() {
        workoutList.addWorkout(ABS_WORKOUT);
        Workout editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutExercise(VALID_EXERCISE_DUMBBELL_CRUNCH).build();
        assertTrue(workoutList.hasWorkout(editedAbsWorkout));
    }

    @Test
    public void getWorkoutList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> workoutList.getWorkoutList().remove(0));
    }

    /**
     * A stub ReadOnlyWorkoutList whose workouts list can violate interface constraints.
     */
    private static class WorkoutListStub implements ReadOnlyWorkoutList {
        private final ObservableList<Workout> workouts = FXCollections.observableArrayList();

        WorkoutListStub(Collection<Workout> workouts) {
            this.workouts.setAll(workouts);
        }

        @Override
        public ObservableList<Workout> getWorkoutList() {
            return workouts;
        }
    }

}
