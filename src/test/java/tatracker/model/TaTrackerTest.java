package tatracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tatracker.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.TypicalTaTracker.getTypicalTaTrackerWithStudents;
import static tatracker.testutil.student.TypicalStudents.ALICE;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.session.Session;
import tatracker.model.student.Student;
import tatracker.testutil.student.StudentBuilder;

public class TaTrackerTest {

    private final TaTracker taTracker = new TaTracker();

    /*
    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), taTracker.getStudentList());
    }*/

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taTracker.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyTaTracker_replacesData() {
        TaTracker newData = getTypicalTaTrackerWithStudents();
        taTracker.resetData(newData);
        assertEquals(newData, taTracker);
    }

    /*
    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        TaTrackerStub newData = new TaTrackerStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> taTracker.resetData(newData));
    }*/

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> taTracker.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInTaTracker_returnsFalse() {
        assertFalse(taTracker.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInTaTracker_returnsTrue() {
        taTracker.addStudent(ALICE);
        assertTrue(taTracker.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInTaTracker_returnsTrue() {
        taTracker.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(taTracker.hasStudent(editedAlice));
    }

    /*
    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> taTracker.getStudentList().remove(0));
    }*/

    // TODO: Add test cases for SessionList

    /**
     * A stub ReadOnlyTaTracker whose internal lists can violate interface constraints.
     */
    private static class TaTrackerStub implements ReadOnlyTaTracker {
        private final ObservableList<Session> sessions = FXCollections.observableArrayList();
        private final ObservableList<Session> doneSessions = FXCollections.observableArrayList();
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Group> groups = FXCollections.observableArrayList();

        TaTrackerStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Session> getSessionList() {
            return sessions;
        }

        @Override
        public ObservableList<Session> getDoneSessionList() {
            return doneSessions;
        }

        @Override
        public ObservableList<Student> getCurrentlyShownStudentList() {
            return null;
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }

        @Override
        public ObservableList<Group> getCurrentlyShownGroupList() {
            return groups;
        }

        @Override
        public ObservableList<Student> getCompleteStudentList() {
            return null;
        }

        public long getTotalHours() {
            throw new AssertionError("This method should not be called.");
        }

        public int getRate() {
            throw new AssertionError("This method should not be called.");
        }

        public long getTotalEarnings() {
            throw new AssertionError("This method should not be called.");
        }
    }

}
