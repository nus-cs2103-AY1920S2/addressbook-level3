package seedu.zerotoone.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.ABS_WORKOUT;
import static seedu.zerotoone.testutil.workout.TypicalWorkouts.LEGS_WORKOUT;
import static seedu.zerotoone.testutil.workout.WorkoutCommandTestUtil.VALID_EXERCISE_DUMBBELL_CRUNCH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.workout.exceptions.DuplicateWorkoutException;
import seedu.zerotoone.model.workout.exceptions.WorkoutNotFoundException;
import seedu.zerotoone.testutil.workout.WorkoutBuilder;

public class UniqueWorkoutListTest {

    private final UniqueWorkoutList uniqueWorkoutList = new UniqueWorkoutList();

    @Test
    public void contains_nullWorkout_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWorkoutList.contains(null));
    }

    @Test
    public void contains_workoutNotInList_returnsFalse() {
        assertFalse(uniqueWorkoutList.contains(ABS_WORKOUT));
    }

    @Test
    public void contains_workoutInList_returnsTrue() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        assertTrue(uniqueWorkoutList.contains(ABS_WORKOUT));
    }

    @Test
    public void contains_workoutWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        Workout editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutExercise(VALID_EXERCISE_DUMBBELL_CRUNCH).build();
        assertTrue(uniqueWorkoutList.contains(editedAbsWorkout));
    }

    @Test
    public void add_nullWorkout_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWorkoutList.add(null));
    }

    @Test
    public void add_duplicateWorkout_throwsDuplicateWorkoutException() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        assertThrows(DuplicateWorkoutException.class, () -> uniqueWorkoutList.add(ABS_WORKOUT));
    }

    @Test
    public void setWorkout_nullTargetWorkout_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWorkoutList.setWorkout(null, ABS_WORKOUT));
    }

    @Test
    public void setWorkout_nullEditedWorkout_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWorkoutList.setWorkout(ABS_WORKOUT, null));
    }

    @Test
    public void setWorkout_targetWorkoutNotInList_throwsWorkoutNotFoundException() {
        assertThrows(WorkoutNotFoundException.class, () -> uniqueWorkoutList.setWorkout(ABS_WORKOUT, ABS_WORKOUT));
    }

    @Test
    public void setWorkout_editedWorkoutIsSameWorkout_success() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        uniqueWorkoutList.setWorkout(ABS_WORKOUT, ABS_WORKOUT);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(ABS_WORKOUT);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkout_editedWorkoutHasSameIdentity_success() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        Workout editedAbsWorkout = new WorkoutBuilder(ABS_WORKOUT)
                .withWorkoutExercise(VALID_EXERCISE_DUMBBELL_CRUNCH).build();
        uniqueWorkoutList.setWorkout(ABS_WORKOUT, editedAbsWorkout);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(editedAbsWorkout);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkout_editedWorkoutHasDifferentIdentity_success() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        uniqueWorkoutList.setWorkout(ABS_WORKOUT, LEGS_WORKOUT);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(LEGS_WORKOUT);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkout_editedWorkoutHasNonUniqueIdentity_throwsDuplicateWorkoutException() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        uniqueWorkoutList.add(LEGS_WORKOUT);
        assertThrows(DuplicateWorkoutException.class, () -> uniqueWorkoutList.setWorkout(ABS_WORKOUT, LEGS_WORKOUT));
    }

    @Test
    public void remove_nullWorkout_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWorkoutList.remove(null));
    }

    @Test
    public void remove_workoutDoesNotExist_throwsWorkoutNotFoundException() {
        assertThrows(WorkoutNotFoundException.class, () -> uniqueWorkoutList.remove(ABS_WORKOUT));
    }

    @Test
    public void remove_existingWorkout_removesWorkout() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        uniqueWorkoutList.remove(ABS_WORKOUT);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkouts_nullUniqueWorkoutList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWorkoutList.setWorkouts((UniqueWorkoutList) null));
    }

    @Test
    public void setWorkouts_uniqueWorkoutList_replacesOwnListWithProvidedUniqueWorkoutList() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(LEGS_WORKOUT);
        uniqueWorkoutList.setWorkouts(expectedUniqueWorkoutList);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkouts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueWorkoutList.setWorkouts((List<Workout>) null));
    }

    @Test
    public void setWorkouts_list_replacesOwnListWithProvidedList() {
        uniqueWorkoutList.add(ABS_WORKOUT);
        List<Workout> workoutList = Collections.singletonList(LEGS_WORKOUT);
        uniqueWorkoutList.setWorkouts(workoutList);
        UniqueWorkoutList expectedUniqueWorkoutList = new UniqueWorkoutList();
        expectedUniqueWorkoutList.add(LEGS_WORKOUT);
        assertEquals(expectedUniqueWorkoutList, uniqueWorkoutList);
    }

    @Test
    public void setWorkouts_listWithDuplicateWorkouts_throwsDuplicateWorkoutException() {
        List<Workout> listWithDuplicateWorkouts = Arrays.asList(ABS_WORKOUT, ABS_WORKOUT);
        assertThrows(
                DuplicateWorkoutException.class, () -> uniqueWorkoutList.setWorkouts(listWithDuplicateWorkouts));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueWorkoutList.asUnmodifiableObservableList().remove(0));
    }
}
