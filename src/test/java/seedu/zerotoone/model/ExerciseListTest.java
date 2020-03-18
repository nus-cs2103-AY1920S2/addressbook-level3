package seedu.zerotoone.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.zerotoone.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.zerotoone.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.TypicalExercises.ALICE;
import static seedu.zerotoone.testutil.TypicalExercises.getTypicalExerciseList;

import java.util.Arrays;
import java.util.Collection;
// import java.util.Collections;

import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ExerciseList;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.exercise.exceptions.DuplicateExerciseException;
import seedu.zerotoone.testutil.ExerciseBuilder;

public class ExerciseListTest {

    private final ExerciseList exerciseList = new ExerciseList();

    @Test
    public void constructor() {
        // assertEquals(Collections.emptyList(), exerciseList.getExerciseList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> exerciseList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyExerciseList_replacesData() {
        ExerciseList newData = getTypicalExerciseList();
        exerciseList.resetData(newData);
        assertEquals(newData, exerciseList);
    }

    @Test
    public void resetData_withDuplicateExercises_throwsDuplicateExerciseException() {
        // Two exercises with the same identity fields
        Exercise editedAlice = new ExerciseBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Exercise> newExercises = Arrays.asList(ALICE, editedAlice);
        ExerciseListStub newData = new ExerciseListStub(newExercises);

        assertThrows(DuplicateExerciseException.class, () -> exerciseList.resetData(newData));
    }

    // @Test
    // public void hasExercise_nullExercise_throwsNullPointerException() {
    //     assertThrows(NullPointerException.class, () -> exerciseList.hasExercise(null));
    // }

    // @Test
    // public void hasExercise_exerciseNotInExerciseList_returnsFalse() {
    //     assertFalse(exerciseList.hasExercise(ALICE));
    // }

    // @Test
    // public void hasExercise_exerciseInExerciseList_returnsTrue() {
    //     exerciseList.addExercise(ALICE);
    //     assertTrue(exerciseList.hasExercise(ALICE));
    // }

    // @Test
    // public void hasExercise_exerciseWithSameIdentityFieldsInExerciseList_returnsTrue() {
    //     exerciseList.addExercise(ALICE);
    //     Exercise editedAlice = new ExerciseBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
    //             .build();
    //     assertTrue(exerciseList.hasExercise(editedAlice));
    // }

    // @Test
    // public void getExerciseList_modifyList_throwsUnsupportedOperationException() {
    //     assertThrows(UnsupportedOperationException.class, () -> exerciseList.getExerciseList().remove(0));
    // }

    /**
     * A stub ReadOnlyExerciseList whose exercises list can violate interface constraints.
     */
    private static class ExerciseListStub implements ReadOnlyExerciseList {
        private final ObservableList<Exercise> exercises = FXCollections.observableArrayList();

        ExerciseListStub(Collection<Exercise> exercises) {
            this.exercises.setAll(exercises);
        }

        @Override
        public ObservableList<Exercise> getExerciseList() {
            return exercises;
        }
    }

}
