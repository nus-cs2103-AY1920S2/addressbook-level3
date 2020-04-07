package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
//import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.testutil.*;
//import seedu.address.model.person.NameContainsKeywordsPredicate;
//import seedu.address.testutil.AddressBookBuilder;

public class ProfileManagerTest {

    private ProfileManager profileManager = new ProfileManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), profileManager.getUserPrefs());
        assertEquals(new GuiSettings(), profileManager.getGuiSettings());
        assertEquals(new ProfileList(), profileManager.getProfileList());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        profileManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, profileManager.getUserPrefs());

        // Modifying userPrefs should not modify profileManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, profileManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        profileManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, profileManager.getGuiSettings());
    }

    @Test
    public void setProfileListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.setProfileListFilePath(null));
    }

    @Test
    public void setProfileListFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        profileManager.setProfileListFilePath(path);
        assertEquals(path, profileManager.getProfileListFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> profileManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInProfileList_returnsFalse() {
        assertFalse(profileManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInProfileList_returnsTrue() {
        profileManager.addPerson(ALICE);
        assertTrue(profileManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personDeletedFromProfileList_returnsFalse() {
        profileManager.addPerson(ALICE);
        profileManager.deletePerson(ALICE);
        assertFalse(profileManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personReplacedInProfileList_returnsTrue() {
        profileManager.addPerson(ALICE);
        profileManager.setPerson(ALICE, BENSON);
        assertTrue(profileManager.hasPerson(BENSON));
    }

    @Test
    public void hasPerson_personReplacedInProfileList_returnFalse() {
        profileManager.addPerson(ALICE);
        profileManager.setPerson(ALICE, BENSON);
        assertFalse(profileManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> profileManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        ProfileList profileList = new ProfileListBuilder().withProfile(ALICE).withProfile(BENSON).build();
        ProfileList differentProfileList = new ProfileList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        profileManager = new ProfileManager(profileList, userPrefs);
        ProfileManager profileManagerCopy = new ProfileManager(profileList, userPrefs);
        assertTrue(profileManager.equals(profileManagerCopy));

        // same object -> returns true
        assertTrue(profileManager.equals(profileManager));

        // null -> returns false
        assertFalse(profileManager.equals(null));

        // different types -> returns false
        assertFalse(profileManager.equals(5));

        // different addressBook -> returns false
        assertFalse(profileManager.equals(new ProfileManager(differentProfileList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        //profileManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        //assertFalse(profileManager.equals(new ProfileManager(addressBook, userPrefs)));

        // resets profileManager to initial state for upcoming tests
        profileManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(profileManager.equals(new ProfileManager(profileList, differentUserPrefs)));
    }
}
