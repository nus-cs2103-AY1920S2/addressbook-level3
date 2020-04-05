package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.Date;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.model.event.EventScheduleView;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.model.notes.Notes;
import seedu.address.model.notes.ReadOnlyNotes;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;




public class AddCommandTest {

    @Test
    public void constructor_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_studentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStudentAdded modelStub = new ModelStubAcceptingStudentAdded();
        Student validStudent = new StudentBuilder().build();

        CommandResult commandResult = new AddCommand(validStudent).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validStudent), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStudent), modelStub.studentsAdded);
    }

    @Test
    public void execute_duplicateStudent_throwsCommandException() {
        Student validStudent = new StudentBuilder().build();
        AddCommand addCommand = new AddCommand(validStudent);
        ModelStub modelStub = new ModelStubWithStudent(validStudent);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_STUDENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Student alice = new StudentBuilder().withName("Alice").build();
        Student bob = new StudentBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different student -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStudentName(String student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStudent(Student target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStudent(Student target, Student editedStudent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredStudentList(Predicate<Student> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAdminFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAdminFilePath(Path adminBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAdmin(ReadOnlyAdmin admin) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAdmin getAdmin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDate(Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteDate(Date target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDate(Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setDate(Date target, Date date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Date> getFilteredDateList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDateList(Predicate<Date> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAcademicsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAcademicsFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAcademics(ReadOnlyAcademics academics) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAcademics getAcademics() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAssessment(Assessment assessment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAssessment(Assessment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAssessment(Assessment assessment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAssessment(Assessment target, Assessment assessment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void submitAssessment(Assessment target, List<String> students) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudentToAssessments(String toAdd) {
            /* This method should not add to assessments */
        }

        @Override
        public void removeStudentFromAssessments(String toRemove) {
            /* This method should not remove from assessments */
        }

        @Override
        public void updateStudentToAssessments(String prevName, String newName) {
            /* This method should not update to assessments */
        }

        @Override
        public boolean hasStudentSubmitted(Assessment assessment, String student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void markAssessment(Assessment target, HashMap<String, Integer> submissions) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assessment> getFilteredAcademicsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assessment> getHomeworkList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Assessment> getExamList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAcademicsList(Predicate<Assessment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getNotesFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNotesFilePath(Path notesFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNotesManager(ReadOnlyNotes notes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyNotes getNotesManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNote(Notes note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteNote(Notes note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addNote(Notes note) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNote(Notes target, Notes edited) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Notes> getFilteredNotesList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredNotesList(Predicate<Notes> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasVEvent(VEvent vEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addVEvent(VEvent vEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void delete(Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setVEvent(Index index, VEvent vEvent) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public VEvent getVEvent(Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<VEvent> getVEvents() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventHistory(ReadOnlyEvents events) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventHistory(Path eventHistoryFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyEvents getEventHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyVEvents getVEventHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventScheduleLocalDateTime(LocalDateTime localDateTime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getEventSchedulePref() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public LocalDateTime getEventScheduleLocalDateTime() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public EventScheduleView getEventScheduleView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setEventScheduleView(EventScheduleView eventScheduleView) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single student.
     */
    private class ModelStubWithStudent extends ModelStub {
        private final Student student;

        ModelStubWithStudent(Student student) {
            requireNonNull(student);
            this.student = student;
        }

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return this.student.isSameStudent(student);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingStudentAdded extends ModelStub {
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasStudent(Student student) {
            requireNonNull(student);
            return studentsAdded.stream().anyMatch(student::isSameStudent);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
