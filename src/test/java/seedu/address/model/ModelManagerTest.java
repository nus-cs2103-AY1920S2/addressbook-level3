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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.group.Group;
import seedu.address.model.person.ActivityList;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PlaceList;
import seedu.address.model.person.TimeList;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPersons_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPersons(null));
    }

    @Test
    public void hasGroup_nullGroup_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasGroup(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPersons_personNotInAddressBook_returnsFalse() {
        List<Person> personList = new ArrayList<>();
        personList.add(ALICE);
        personList.add(BENSON);
        assertFalse(modelManager.hasPersons(personList));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPersons_personInAddressBook_returnsTrue() {
        List<Person> personList = new ArrayList<>();
        personList.add(ALICE);
        personList.add(BENSON);
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);
        assertTrue(modelManager.hasPersons(personList));
    }

    @Test
    public void hasGroupTest_returnsTrue() {
        Group g1 = new Group(new Name("SoC Friends"), new PlaceList(new ArrayList<String>()),
                new ActivityList(new ArrayList<String>()), new TimeList(new ArrayList<String>()));
        Group g2 = new Group(new Name("RC Friends"), new PlaceList(new ArrayList<String>()),
                new ActivityList(new ArrayList<String>()), new TimeList(new ArrayList<String>()));
        modelManager.addGroup(g1);
        modelManager.addGroup(g2);
        assertTrue(modelManager.hasGroup(g1));
        assertTrue(modelManager.hasGroup(g2));
    }

    @Test
    public void hasGroup_groupNotInAddressBook_returnsFalse() {
        Group g1 = new Group(new Name("SoC Friends"), new PlaceList(new ArrayList<String>()),
                new ActivityList(new ArrayList<String>()), new TimeList(new ArrayList<String>()));
        Group g2 = new Group(new Name("RC Friends"), new PlaceList(new ArrayList<String>()),
                new ActivityList(new ArrayList<String>()), new TimeList(new ArrayList<String>()));
        assertFalse(modelManager.hasGroup(g1));
        assertFalse(modelManager.hasGroup(g2));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredGroupList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(
                UnsupportedOperationException.class, () -> modelManager.getFilteredGroupList().remove(0));
    }

    @Test
    public void importCsvToAddressBook_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.importCsvToAddressBook(null));
    }

    @Test
    public void importCsvGroupsToAddressBook_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.importCsvGroupsToAddressBook(null));
    }

    @Test
    public void importCsvEventsToAddressBook_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.importCsvEventsToAddressBook(null));
    }

    @Test
    public void showPlaceList_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.showPlaceList(null));
    }

    @Test
    public void showActivityList_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.showActivityList(null));
    }

    @Test
    public void showRecentList_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.showRecentList(null));
    }

    @Test
    public void copyRecent_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.copyRecent(null));
    }

    @Test
    public void copyTime_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.copyTime(null));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(
                new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
