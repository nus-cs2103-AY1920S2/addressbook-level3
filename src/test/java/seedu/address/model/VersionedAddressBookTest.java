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
import static seedu.address.testutil.TypicalPersons.DANIEL;

public class VersionedAddressBookTest {

    private VersionedAddressBook versionedAddressBook = new VersionedAddressBook();

    @Test
    public void undo_withoutCommits_throwsStateNotFoundException() {
        assertThrows(StateNotFoundException.class, () -> versionedAddressBook.undo());
    }

    @Test
    public void undo_afterOneCommit_removeChanges() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.undo();
        assertFalse(versionedAddressBook.hasPerson(ALICE));
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

    @Test
    public void commit_afterUndo_removesFutureHistory() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(BENSON);
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(CARL);
        versionedAddressBook.commit();

        // ensures the current state points to the most recent commit
        versionedAddressBook.undo();
        versionedAddressBook.addPerson(DANIEL);
        assertTrue(versionedAddressBook.hasPerson(DANIEL));

        // ensures that current state is not added on top of deleted history
        versionedAddressBook.undo();
        assertFalse(versionedAddressBook.hasPerson(DANIEL));
        assertFalse(versionedAddressBook.hasPerson(CARL));
    }
}
