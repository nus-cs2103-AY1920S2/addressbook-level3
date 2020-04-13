package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalUser.ALICE;
import static seedu.address.testutil.TypicalUser.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.UserBuilder;

public class UserTest {

    @Test
    public void equals() {
        // same values -> returns true
        User aliceCopy = new UserBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different user -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        User editedAlice = new UserBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new UserBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new UserBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void hashcode() {
        User aliceCopy = new UserBuilder(ALICE).build();

        // same values -> returns same hashcode
        assertEquals(ALICE.hashCode(), aliceCopy.hashCode());

        // different name value -> returns different hashcode
        assertNotEquals(ALICE.hashCode(), new UserBuilder(ALICE).withName("Bob").build().hashCode());

        // different email value -> returns different hashcode
        assertNotEquals(ALICE.hashCode(),
                new UserBuilder(ALICE).withEmail("bob@example.com").build().hashCode());

        // different phone value -> returns different hashcode
        assertNotEquals(ALICE.hashCode(), new UserBuilder(ALICE).withPhone("91234567").build().hashCode());
    }

    @Test
    public void tostring() {
        String expectedResult = "User: Alice Pauline Phone: 94351253 Email: alice@example.com";
        assertEquals(expectedResult, ALICE.toString());
    }

}
