package tatracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalStudents.ALICE;
import static tatracker.testutil.TypicalStudents.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tatracker.logic.commands.CommandTestUtil;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;
import tatracker.model.student.exceptions.DuplicateStudentException;
import tatracker.testutil.StudentBuilder;

public class TaTrackerTest {

    private final TaTracker taTracker = new TaTracker();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taTracker.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taTracker.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        TaTracker newData = getTypicalAddressBook();
        taTracker.resetData(newData);
        assertEquals(newData, taTracker);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> taTracker.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taTracker.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInAddressBook_returnsFalse() {
        assertFalse(taTracker.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInAddressBook_returnsTrue() {
        taTracker.addStudent(ALICE);
        assertTrue(taTracker.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInAddressBook_returnsTrue() {
        taTracker.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        assertTrue(taTracker.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taTracker.getStudentList().remove(0));
    }

    // TODO: Add test cases for SessionList

    /**
     * A stub ReadOnlyAddressBook whose internal lists can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Session> sessions = FXCollections.observableArrayList();
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        private final ObservableList<Student> students = FXCollections.observableArrayList();

        AddressBookStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }

        @Override
        public ObservableList<Session> getSessionList() {
            return sessions;
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }
    }

}
