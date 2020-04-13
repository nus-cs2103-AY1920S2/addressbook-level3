package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.consult.Consult;
import seedu.address.model.event.consult.ConsultTAble;
import seedu.address.model.event.consult.exceptions.ConsultNotFoundException;
import seedu.address.model.event.tutorial.TutorialTAble;
import seedu.address.model.mod.ModTAble;
import seedu.address.model.reminder.ReminderTAble;
import seedu.address.model.student.NameContainsKeywordsPredicate;
import seedu.address.model.student.StudentTAble;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.ConsultBuilder;


public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new StudentTAble(), new StudentTAble(modelManager.getStudentTAble()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setStudentTAbleFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setStudentTAbleFilePath(Paths.get("new/address/book/file/path"));
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
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInAddressBook_returnsTrue() {
        modelManager.addStudent(ALICE);
        assertTrue(modelManager.hasStudent(ALICE));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(0));
    }

    @Test
    public void hasConsult_consultNotInTAble_returnsFalse() throws ParseException {
        Consult consult1 = new ConsultBuilder().build();
        assertFalse(modelManager.hasConsult(consult1));
    }

    @Test
    public void deleteConsult_consultNotInTAble_throwsUnsupportedOperationException() throws ParseException {
        Consult consult1 = new ConsultBuilder().build();
        assertThrows(ConsultNotFoundException.class, () -> modelManager.deleteConsult(consult1));
    }

    @Test
    public void hasConsult_consultInTAble_returnsTrue() throws ParseException {
        Consult consult1 = new ConsultBuilder().build();
        modelManager.addConsult(consult1);
        assertTrue(modelManager.hasConsult(consult1));
    }

    @Test
    public void deleteConsult_consultInTAble_deletesConsult1() throws ParseException {
        Consult consult1 = new ConsultBuilder().build();
        modelManager.addConsult(consult1);
        modelManager.deleteConsult(consult1);
        assertTrue(!modelManager.hasConsult(consult1));
    }


    @Test
    public void equals() {
        StudentTAble studentTAble = new AddressBookBuilder().withStudent(ALICE).withStudent(BENSON).build();
        StudentTAble differentStudentTAble = new StudentTAble();
        UserPrefs userPrefs = new UserPrefs();
        ConsultTAble consultTAble = new ConsultTAble();
        TutorialTAble tutorialTAble = new TutorialTAble();
        ModTAble modTAble = new ModTAble();
        ReminderTAble reminderTAble = new ReminderTAble();

        // same values -> returns true
        modelManager = new ModelManager(studentTAble, userPrefs, new ConsultTAble(),
            new TutorialTAble(), new ModTAble(), new ReminderTAble());
        ModelManager modelManagerCopy = new ModelManager(studentTAble, userPrefs, consultTAble,
            tutorialTAble, modTAble, reminderTAble);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different studentTAble -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentStudentTAble, userPrefs, consultTAble,
            tutorialTAble, modTAble, reminderTAble)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(studentTAble, userPrefs, consultTAble,
            tutorialTAble, modTAble, reminderTAble)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setStudentTAbleFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(studentTAble, differentUserPrefs, consultTAble,
            tutorialTAble, modTAble, reminderTAble)));
    }
}
