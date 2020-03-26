package seedu.address.model;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

public class VersionedAddressBookTest {

    private VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

    @Test
    public void undo_withoutCommits_throwsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.undo();
        assertFalse(versionedAddressBook.hasPerson(BENSON));
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(BENSON);
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(CARL);
        versionedAddressBook.commit();

        // first undo goes to second commit with Alice and Benson
        versionedAddressBook.undo();
        assertTrue(versionedAddressBook.hasPerson(ALICE));
        assertTrue(versionedAddressBook.hasPerson(BENSON));
        assertFalse(versionedAddressBook.hasPerson(CARL));

        // second undo goes to first commit with Alice only
        versionedAddressBook.undo();
        assertTrue(versionedAddressBook.hasPerson(ALICE));
        assertFalse(versionedAddressBook.hasPerson(BENSON));
        assertFalse(versionedAddressBook.hasPerson(CARL));
    }

    @Test
    public void undo_afterUnsavedChanges_removesUnsavedAndPreviousChanges() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();

        Person p = new PersonBuilder().withName("Erased Ignored").build();
        versionedAddressBook.undo();
        assertFalse(versionedAddressBook.hasPerson(ALICE));
        assertFalse(versionedAddressBook.hasPerson(p));
    }
}
