package tatracker.model.student;

import static tatracker.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_MATRIC_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalStudents.ALICE;
import static tatracker.testutil.TypicalStudents.BOB;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import tatracker.testutil.StudentBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new StudentBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSameStudent() {
        // same object -> returns true
        Assertions.assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        Assertions.assertFalse(ALICE.isSameStudent(null));

        // different phone and email -> returns false
        Student editedAlice = new StudentBuilder(ALICE)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        Assertions.assertFalse(ALICE.isSameStudent(editedAlice));

        // different name -> returns false
        editedAlice = new StudentBuilder(ALICE)
                .withName(VALID_NAME_BOB).build();
        Assertions.assertFalse(ALICE.isSameStudent(editedAlice));

        // same name, same phone, same matric, different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE)
                .withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(ALICE.isSameStudent(editedAlice));

        // same name, same email, same matric, different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE)
                .withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(ALICE.isSameStudent(editedAlice));

        // same name, same phone, same email, same matric different attributes -> returns true
        editedAlice = new StudentBuilder(ALICE)
                .withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(ALICE.isSameStudent(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new StudentBuilder(ALICE).build();
        Assertions.assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        Assertions.assertFalse(ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(ALICE.equals(5));

        // different student -> returns false
        Assertions.assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student editedAlice = new StudentBuilder(ALICE)
                .withName(VALID_NAME_BOB).build();
        Assertions.assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new StudentBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        Assertions.assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new StudentBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        Assertions.assertFalse(ALICE.equals(editedAlice));

        // different matric -> returns false
        editedAlice = new StudentBuilder(ALICE).withMatric(VALID_MATRIC_BOB).build();
        Assertions.assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        Assertions.assertFalse(ALICE.equals(editedAlice));
    }
}
