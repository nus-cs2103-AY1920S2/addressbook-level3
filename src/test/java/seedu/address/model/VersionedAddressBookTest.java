package seedu.address.model;

import org.junit.jupiter.api.Test;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        AddressBook expectedAddressBook = new AddressBook(versionedAddressBook);

        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBook);
    }

    @Test
    public void undo_afterMultipleCommits_returnsToMostRecentCommit() {
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        AddressBook expectedAddressBookFirstCommit = new AddressBook(versionedAddressBook);

        versionedAddressBook.addPerson(BENSON);
        versionedAddressBook.commit();
        AddressBook expectedAddressBookSecondCommit = new AddressBook(versionedAddressBook);

        versionedAddressBook.addPerson(CARL);
        versionedAddressBook.commit();

        // first undo goes to second commit
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBookSecondCommit);

        // second undo goes to first commit
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBookFirstCommit);
    }

    @Test
    public void undo_afterUnsavedChanges_removesUnsavedAndPreviousChanges() {
        AddressBook expectedAddressBook = new AddressBook(versionedAddressBook);
        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();

        Person p = new PersonBuilder().withName("Erased Ignored").build();
        versionedAddressBook.addPerson(p);
        versionedAddressBook.undo();

        assertEquals(versionedAddressBook, expectedAddressBook);
    }

    @Test
    public void commit_afterUndo_removesFutureHistory() {
        AddressBook expectedAddressBookAfterRewrite = new AddressBook(versionedAddressBook);
        expectedAddressBookAfterRewrite.addPerson(ALICE);
        expectedAddressBookAfterRewrite.addPerson(BENSON);
        expectedAddressBookAfterRewrite.addPerson(DANIEL);

        AddressBook expectedAddressBookAfterUndoFromRewrite = new AddressBook(versionedAddressBook);
        expectedAddressBookAfterUndoFromRewrite.addPerson(ALICE);
        expectedAddressBookAfterUndoFromRewrite.addPerson(BENSON);

        versionedAddressBook.addPerson(ALICE);
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(BENSON);
        versionedAddressBook.commit();
        versionedAddressBook.addPerson(CARL);
        versionedAddressBook.commit();

        // ensures the current state points to the most recent commit
        versionedAddressBook.undo();
        versionedAddressBook.addPerson(DANIEL);
        versionedAddressBook.commit();
        assertEquals(versionedAddressBook, expectedAddressBookAfterRewrite);

        // ensures that current state is not added on top of deleted history
        versionedAddressBook.undo();
        assertEquals(versionedAddressBook, expectedAddressBookAfterUndoFromRewrite);
    }
}
