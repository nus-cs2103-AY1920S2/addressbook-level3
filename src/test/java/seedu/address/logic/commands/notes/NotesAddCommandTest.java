package seedu.address.logic.commands.notes;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.notes.TypicalNotes.NOTE1;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.math3.util.Pair;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.Date;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.model.event.EventScheduleView;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.model.notes.Notes;
import seedu.address.model.notes.NotesManager;
import seedu.address.model.notes.ReadOnlyNotes;
import seedu.address.model.student.ReadOnlyTeaPet;
import seedu.address.model.student.Student;
import seedu.address.testutil.notes.NotesBuilder;
import seedu.address.testutil.student.StudentBuilder;


public class NotesAddCommandTest {

    @Test
    public void constructor_nullNote_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NotesAddCommand(null));
    }

    @Test
    public void execute_noteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Notes validNote = new NotesBuilder().build();
        Student validStudent = new StudentBuilder().withName(validNote.getStudent()).build();
        modelStub.addStudent(validStudent);

        CommandResult commandResult = new NotesAddCommand(validNote).execute(modelStub);

        assertEquals(String.format(NotesAddCommand.MESSAGE_SUCCESS, validNote), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validNote), modelStub.notesAdded);
    }

    @Test
    public void execute_noteNoteAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingNoteAdded modelStub = new ModelStubAcceptingNoteAdded();
        Notes validNote = new NotesBuilder().build();

        //Student not found in model.
        assertThrows(CommandException.class, () -> new NotesAddCommand(validNote).execute(modelStub));
    }


    @Test
    public void execute_duplicateNote_throwCommandException() {
        Notes validNote = new NotesBuilder().build();
        NotesAddCommand addCommand = new NotesAddCommand(validNote);
        ModelStub modelStub = new ModelStubWithNote(validNote);
        Student validStudent = new StudentBuilder().withName(validNote.getStudent()).build();
        modelStub.addStudent(validStudent);

        assertThrows(CommandException.class, NotesAddCommand.MESSAGE_DUPLICATE_NOTE, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Notes validNote = new NotesBuilder().build();
        NotesAddCommand addCommand = new NotesAddCommand(validNote);
        NotesAddCommand addCommandSecond = new NotesAddCommand(NOTE1);

        assertEquals(addCommand, addCommand);

        assertNotEquals(addCommand, addCommandSecond);

        assertNotEquals(addCommand, null);

        assertNotEquals(addCommand, 3);
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
        public Path getTeaPetFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeaPetFilePath(Path teaPetFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addStudent(Student student) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeaPet(ReadOnlyTeaPet newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTeaPet getTeaPet() {
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
        public boolean hasStudentNameNonCaseSensitive(String student) {
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
        public void setAcademicsFilePath(Path academicsFilePath) {
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
        @Override
        public List<Pair<Index, VEvent>> searchVEvents(String eventName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Pair<Index, VEvent> searchMostSimilarVEventName(String eventName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Pair<Index, VEvent>> getAllVEventsWithIndex() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single note.
     */
    private class ModelStubWithNote extends ModelStub {
        private final Notes note;
        private final ArrayList<Student> studentsAdded = new ArrayList<>();

        ModelStubWithNote(Notes note) {
            requireNonNull(note);
            this.note = note;
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public boolean hasStudentName(String student) {
            requireNonNull(student);
            boolean contains = false;
            for (Student stu : studentsAdded) {
                if (stu.getName().fullName.equals(student)) {
                    contains = true;
                }
            }
            return contains;
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableList(studentsAdded);
        }

        @Override
        public boolean hasNote(Notes note) {
            requireNonNull(note);
            return this.note.isSameNote(note);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingNoteAdded extends ModelStub {
        final ArrayList<Notes> notesAdded = new ArrayList<>();
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasNote(Notes note) {
            requireNonNull(note);
            return notesAdded.stream().anyMatch(note::isSameNote);
        }

        @Override
        public void addNote(Notes note) {
            requireNonNull(note);
            notesAdded.add(note);
        }

        @Override
        public void addStudent(Student student) {
            requireNonNull(student);
            studentsAdded.add(student);
        }

        @Override
        public boolean hasStudentName(String student) {
            requireNonNull(student);
            boolean contains = false;
            for (Student stu : studentsAdded) {
                if (stu.getName().fullName.equals(student)) {
                    contains = true;
                }
            }
            return contains;
        }

        @Override
        public ObservableList<Student> getFilteredStudentList() {
            return FXCollections.observableList(studentsAdded);
        }

        @Override
        public ReadOnlyNotes getNotesManager() {
            return new NotesManager();
        }
    }

}

