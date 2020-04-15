package seedu.address.logic.commands.academics;

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
import seedu.address.model.academics.Academics;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.Date;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.model.event.EventScheduleView;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.model.notes.Notes;
import seedu.address.model.notes.ReadOnlyNotes;
import seedu.address.model.student.ReadOnlyTeaPet;
import seedu.address.model.student.Student;
import seedu.address.testutil.academics.AssessmentBuilder;

public class AcademicsAddCommandTest {

    @Test
    public void constructor_nullAssessment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AcademicsAddCommand(null));
    }

    @Test
    public void execute_assessmentAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAssessmentAdded modelStub = new ModelStubAcceptingAssessmentAdded();
        Assessment validAssessment = new AssessmentBuilder().build();

        CommandResult commandResult = new AcademicsAddCommand(validAssessment).execute(modelStub);

        assertEquals(String.format(AcademicsAddCommand.MESSAGE_SUCCESS, validAssessment),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validAssessment), modelStub.assessmentsAdded);
    }

    @Test
    public void execute_duplicateAssessment_throwsCommandException() {
        Assessment validAssessment = new AssessmentBuilder().build();
        AcademicsAddCommand addCommand = new AcademicsAddCommand(validAssessment);
        ModelStub modelStub = new ModelStubWithAssessment(validAssessment);

        assertThrows(CommandException.class, AcademicsAddCommand.MESSAGE_DUPLICATE_ASSESSMENT, () ->
                addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Assessment scienceHomework = new AssessmentBuilder().withDescription("Science Homework").build();
        Assessment chineseHomework = new AssessmentBuilder().withDescription("Chinese Homework").build();
        AcademicsAddCommand addScienceHomeworkCommand = new AcademicsAddCommand(scienceHomework);
        AcademicsAddCommand addChineseHomeworkCommand = new AcademicsAddCommand(chineseHomework);

        // same object -> returns true
        assertTrue(addScienceHomeworkCommand.equals(addScienceHomeworkCommand));

        // same values -> returns true
        AcademicsAddCommand addScienceHomeworkCommandCopy = new AcademicsAddCommand(scienceHomework);
        assertTrue(addScienceHomeworkCommand.equals(addScienceHomeworkCommandCopy));

        // different types -> returns false
        assertFalse(addScienceHomeworkCommand.equals(1));

        // null -> returns false
        assertFalse(addScienceHomeworkCommand.equals(null));

        // different student -> returns false
        assertFalse(addScienceHomeworkCommand.equals(addChineseHomeworkCommand));
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
        public List<Pair<Index, VEvent>> getAllVEventsWithIndex() {
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
    }

    /**
     * A Model stub that contains a single assessment.
     */
    private class ModelStubWithAssessment extends ModelStub {
        private final Assessment assessment;
        private final ArrayList<Student> studentsAdded = new ArrayList<>();

        ModelStubWithAssessment(Assessment assessment) {
            requireNonNull(assessment);
            this.assessment = assessment;
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
        public boolean hasAssessment(Assessment assessment) {
            requireNonNull(assessment);
            return this.assessment.isSameAssessment(assessment);
        }
    }

    /**
     * A Model stub that always accept the student being added.
     */
    private class ModelStubAcceptingAssessmentAdded extends ModelStub {
        final ArrayList<Assessment> assessmentsAdded = new ArrayList<>();
        final ArrayList<Student> studentsAdded = new ArrayList<>();

        @Override
        public boolean hasAssessment(Assessment assessment) {
            requireNonNull(assessment);
            return assessmentsAdded.stream().anyMatch(assessment::isSameAssessment);
        }

        @Override
        public void addAssessment(Assessment assessment) {
            requireNonNull(assessment);
            assessmentsAdded.add(assessment);
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
        public ReadOnlyAcademics getAcademics() {
            return new Academics();
        }
    }

}

