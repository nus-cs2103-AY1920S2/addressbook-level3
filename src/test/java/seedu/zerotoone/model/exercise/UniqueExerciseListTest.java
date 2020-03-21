package seedu.zerotoone.model.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.VALID_NUM_REPS_BENCH_PRESS;
import static seedu.zerotoone.testutil.exercise.ExerciseCommandTestUtil.VALID_WEIGHT_BENCH_PRESS;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.BENCH_PRESS;
import static seedu.zerotoone.testutil.exercise.TypicalExercises.DEADLIFT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.zerotoone.model.exercise.exceptions.DuplicateExerciseException;
import seedu.zerotoone.model.exercise.exceptions.ExerciseNotFoundException;
import seedu.zerotoone.testutil.exercise.ExerciseBuilder;

public class UniqueExerciseListTest {

    private final UniqueExerciseList uniqueExerciseList = new UniqueExerciseList();

    @Test
    public void contains_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.contains(null));
    }

    @Test
    public void contains_exerciseNotInList_returnsFalse() {
        assertFalse(uniqueExerciseList.contains(BENCH_PRESS));
    }

    @Test
    public void contains_exerciseInList_returnsTrue() {
        uniqueExerciseList.add(BENCH_PRESS);
        assertTrue(uniqueExerciseList.contains(BENCH_PRESS));
    }

    @Test
    public void contains_exerciseWithSameIdentityFieldsInList_returnsTrue() {
        uniqueExerciseList.add(BENCH_PRESS);
        Exercise editedBenchPress = new ExerciseBuilder(BENCH_PRESS)
                .withExerciseSet(VALID_WEIGHT_BENCH_PRESS, VALID_NUM_REPS_BENCH_PRESS).build();
        assertTrue(uniqueExerciseList.contains(editedBenchPress));
    }

    @Test
    public void add_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.add(null));
    }

    @Test
    public void add_duplicateExercise_throwsDuplicateExerciseException() {
        uniqueExerciseList.add(BENCH_PRESS);
        assertThrows(DuplicateExerciseException.class, () -> uniqueExerciseList.add(BENCH_PRESS));
    }

    @Test
    public void setExercise_nullTargetExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercise(null, BENCH_PRESS));
    }

    @Test
    public void setExercise_nullEditedExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercise(BENCH_PRESS, null));
    }

    @Test
    public void setExercise_targetExerciseNotInList_throwsExerciseNotFoundException() {
        assertThrows(ExerciseNotFoundException.class, () -> uniqueExerciseList.setExercise(BENCH_PRESS, BENCH_PRESS));
    }

    @Test
    public void setExercise_editedExerciseIsSameExercise_success() {
        uniqueExerciseList.add(BENCH_PRESS);
        uniqueExerciseList.setExercise(BENCH_PRESS, BENCH_PRESS);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(BENCH_PRESS);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercise_editedExerciseHasSameIdentity_success() {
        uniqueExerciseList.add(BENCH_PRESS);
        Exercise editedBenchPress = new ExerciseBuilder(BENCH_PRESS)
                .withExerciseSet(VALID_WEIGHT_BENCH_PRESS, VALID_NUM_REPS_BENCH_PRESS).build();
        uniqueExerciseList.setExercise(BENCH_PRESS, editedBenchPress);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(editedBenchPress);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercise_editedExerciseHasDifferentIdentity_success() {
        uniqueExerciseList.add(BENCH_PRESS);
        uniqueExerciseList.setExercise(BENCH_PRESS, DEADLIFT);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(DEADLIFT);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercise_editedExerciseHasNonUniqueIdentity_throwsDuplicateExerciseException() {
        uniqueExerciseList.add(BENCH_PRESS);
        uniqueExerciseList.add(DEADLIFT);
        assertThrows(DuplicateExerciseException.class, () -> uniqueExerciseList.setExercise(BENCH_PRESS, DEADLIFT));
    }

    @Test
    public void remove_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.remove(null));
    }

    @Test
    public void remove_exerciseDoesNotExist_throwsExerciseNotFoundException() {
        assertThrows(ExerciseNotFoundException.class, () -> uniqueExerciseList.remove(BENCH_PRESS));
    }

    @Test
    public void remove_existingExercise_removesExercise() {
        uniqueExerciseList.add(BENCH_PRESS);
        uniqueExerciseList.remove(BENCH_PRESS);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercises_nullUniqueExerciseList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercises((UniqueExerciseList) null));
    }

    @Test
    public void setExercises_uniqueExerciseList_replacesOwnListWithProvidedUniqueExerciseList() {
        uniqueExerciseList.add(BENCH_PRESS);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(DEADLIFT);
        uniqueExerciseList.setExercises(expectedUniqueExerciseList);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercises_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueExerciseList.setExercises((List<Exercise>) null));
    }

    @Test
    public void setExercises_list_replacesOwnListWithProvidedList() {
        uniqueExerciseList.add(BENCH_PRESS);
        List<Exercise> exerciseList = Collections.singletonList(DEADLIFT);
        uniqueExerciseList.setExercises(exerciseList);
        UniqueExerciseList expectedUniqueExerciseList = new UniqueExerciseList();
        expectedUniqueExerciseList.add(DEADLIFT);
        assertEquals(expectedUniqueExerciseList, uniqueExerciseList);
    }

    @Test
    public void setExercises_listWithDuplicateExercises_throwsDuplicateExerciseException() {
        List<Exercise> listWithDuplicateExercises = Arrays.asList(BENCH_PRESS, BENCH_PRESS);
        assertThrows(
                DuplicateExerciseException.class, () -> uniqueExerciseList.setExercises(listWithDuplicateExercises));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueExerciseList.asUnmodifiableObservableList().remove(0));
    }
}
