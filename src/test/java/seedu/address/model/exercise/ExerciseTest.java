package seedu.address.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExercises.ALICE;
import static seedu.address.testutil.TypicalExercises.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ExerciseBuilder;

public class ExerciseTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Exercise exercise = new ExerciseBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> exercise.getTags().remove(0));
    }

    @Test
    public void isSameExercise() {
        // same object -> returns true
        assertTrue(ALICE.isSameExercise(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameExercise(null));

        // different phone and email -> returns false
        Exercise editedAlice = new ExerciseBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameExercise(editedAlice));

        // different name -> returns false
        editedAlice = new ExerciseBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameExercise(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ExerciseBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameExercise(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ExerciseBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameExercise(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ExerciseBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameExercise(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Exercise aliceCopy = new ExerciseBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different exercise -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Exercise editedAlice = new ExerciseBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ExerciseBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ExerciseBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ExerciseBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ExerciseBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
