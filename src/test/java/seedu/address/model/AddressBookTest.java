package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.profile.Profile;
import seedu.address.model.profile.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    //    @Test
    //    public void resetData_withValidReadOnlyAddressBook_replacesData() {
    //        AddressBook newData = getTypicalAddressBook();
    //        addressBook.resetData(newData);
    //        assertEquals(newData, addressBook);
    //    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two profiles with the same identity fields
        Profile editedAlice = new PersonBuilder(ALICE).withCourseName("Computer Science")
                .withCurrentSemester("1").build();
        List<Profile> newProfiles = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newProfiles);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Profile editedAlice = new PersonBuilder(ALICE).withCourseName("Computer Science")
                .withCurrentSemester("1").build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose profiles list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Profile> profiles = FXCollections.observableArrayList();

        AddressBookStub(Collection<Profile> profiles) {
            this.profiles.setAll(profiles);
        }

        @Override
        public ObservableList<Profile> getPersonList() {
            return profiles;
        }
    }

}
