package seedu.address.model;

import org.junit.jupiter.api.Test;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.modelAssignment.AssignmentAddressBook;
import seedu.address.model.modelCourse.CourseAddressBook;
import seedu.address.model.modelFinance.FinanceAddressBook;
import seedu.address.model.modelObjectTags.NameContainsKeywordsPredicate;
import seedu.address.model.modelProgress.ProgressAddressBook;
import seedu.address.model.modelStaff.StaffAddressBook;
import seedu.address.model.modelStudent.StudentAddressBook;
import seedu.address.testutil.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignment.ASSIGNMENT_EASY;
import static seedu.address.testutil.TypicalAssignment.ASSIGNMENT_HARD;
import static seedu.address.testutil.TypicalCourse.COURSE_ALICE;
import static seedu.address.testutil.TypicalCourse.COURSE_BENSON;
import static seedu.address.testutil.TypicalFinance.FINANCE_ALICE;
import static seedu.address.testutil.TypicalFinance.FINANCE_BENSON;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalProgress.Progress_S1_A1;
import static seedu.address.testutil.TypicalProgress.Progress_S1_A2;
import static seedu.address.testutil.TypicalStudent.STUDENT_ALICE;
import static seedu.address.testutil.TypicalStudent.STUDENT_BENSON;
import static seedu.address.testutil.TypicalTeacher.TEACHER_ALICE;
import static seedu.address.testutil.TypicalTeacher.TEACHER_BENSON;

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
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
        StaffAddressBook staffAddressBook = new StaffAddressBookBuilder().withTeacher(TEACHER_ALICE).withTeacher(TEACHER_BENSON).build();
        StudentAddressBook studentAddressBook = new StudentAddressBookBuilder().withStudent(STUDENT_ALICE).withStudent(STUDENT_BENSON).build();
        FinanceAddressBook financeAddressBook = new FinanceAddressBookBuilder().withFinance(FINANCE_ALICE).withFinance(FINANCE_BENSON).build();
        CourseAddressBook courseAddressBook = new CourseAddressBookBuilder().withCourse(COURSE_ALICE).withCourse(COURSE_BENSON).build();
        AssignmentAddressBook assignmentAddressBook = new AssignmentBookBuilder().withAssignment(ASSIGNMENT_EASY).withAssignment(ASSIGNMENT_HARD).build();
        ProgressAddressBook progressAddressBook = new ProgressAddressBookBuilder().withProgress(Progress_S1_A1).withProgress(Progress_S1_A2).build();

        AddressBook differentAddressBook = new AddressBook();
        StaffAddressBook differentStaffAddressBook = new StaffAddressBook();
        StudentAddressBook differentStudentAddressBook = new StudentAddressBook();
        FinanceAddressBook differentFinanceAddressBook = new FinanceAddressBook();
        CourseAddressBook differentCourseAddressBook = new CourseAddressBook();
        AssignmentAddressBook differentAssignmentAddressBook = new AssignmentAddressBook();
        ProgressAddressBook differentProgressAddressBook = new ProgressAddressBook();


        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, staffAddressBook, studentAddressBook, financeAddressBook, courseAddressBook, assignmentAddressBook, progressAddressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, staffAddressBook, studentAddressBook, financeAddressBook, courseAddressBook, assignmentAddressBook, progressAddressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, differentStaffAddressBook, differentStudentAddressBook,
                differentFinanceAddressBook, differentCourseAddressBook, differentAssignmentAddressBook, differentProgressAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, differentStaffAddressBook, differentStudentAddressBook,
                differentFinanceAddressBook, differentCourseAddressBook, differentAssignmentAddressBook, differentProgressAddressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, staffAddressBook, studentAddressBook, financeAddressBook, courseAddressBook, assignmentAddressBook, progressAddressBook, differentUserPrefs)));
    }
}
