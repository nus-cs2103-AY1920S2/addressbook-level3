package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DEBT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDebts.MOVIE;
import static seedu.address.testutil.TypicalDebts.SUPPER;
import static seedu.address.testutil.TypicalLoans.BREAKFAST;
import static seedu.address.testutil.TypicalLoans.SHOPPING;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB)
                .withTags(VALID_TAG_DEBT).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_DEBT).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_DEBT).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // @@author cheyannesim
        // different debt -> returns false
        editedAlice = new PersonBuilder(ALICE)
                .withDebts(MOVIE).build();
        assertFalse(ALICE.equals(editedAlice));
        // @@author

        // different loan -> returns false
        editedAlice = new PersonBuilder(ALICE)
                .withLoans(SHOPPING).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_DEBT).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void hashcode() {
        Person aliceCopy = new PersonBuilder(ALICE).build();

        // same values -> returns same hashcode
        assertEquals(ALICE.hashCode(), new PersonBuilder(ALICE).build().hashCode());

        // different name value -> returns different hashcode
        assertNotEquals(ALICE.hashCode(), new PersonBuilder(ALICE).withName("Bob").build().hashCode());

        // different email value -> returns different hashcode
        assertNotEquals(ALICE.hashCode(),
                new PersonBuilder(ALICE).withEmail("bob@example.com").build().hashCode());

        // different phone value -> returns different hashcode
        assertNotEquals(ALICE.hashCode(), new PersonBuilder(ALICE).withPhone("91234567").build().hashCode());

        // different debt value -> returns different hashcode
        assertNotEquals(ALICE.hashCode(), new PersonBuilder(ALICE).withDebts(SUPPER).build().hashCode());

        // different loan value -> returns different hashcode
        assertNotEquals(ALICE.hashCode(), new PersonBuilder(ALICE).withLoans(BREAKFAST).build().hashCode());
    }
}
