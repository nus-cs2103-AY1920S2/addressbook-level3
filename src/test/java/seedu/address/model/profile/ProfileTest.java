package seedu.address.model.profile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ProfileTest {

    @Test
    public void isSameProfile() {
        // same object -> returns true
        assertTrue(ALICE.isSameProfile(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameProfile(null));

        // different name -> returns false
        Profile editedAlice = new PersonBuilder().withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameProfile(editedAlice));

        // same name -> returns true
        editedAlice = new PersonBuilder(ALICE).withCourseName("Computer Science").withCurrentSemester("1").build();
        assertTrue(ALICE.isSameProfile(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Profile aliceCopy = new PersonBuilder(ALICE).withCourseName("Computer Science")
                .withCurrentSemester("1").build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different profile -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Profile editedAlice = new PersonBuilder().withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
