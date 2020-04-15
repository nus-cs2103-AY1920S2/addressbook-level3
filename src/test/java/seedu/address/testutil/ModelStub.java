package seedu.address.testutil;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.math3.util.Pair;

import javafx.collections.ObservableList;
import jfxtras.icalendarfx.components.VEvent;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
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
import seedu.address.model.notes.ReadOnlyNotes;
import seedu.address.model.student.ReadOnlyTeaPet;
import seedu.address.model.student.Student;





/**
 * Model stub which in default fails all mthods.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public Path getTeaPetFilePath() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setTeaPetFilePath(Path teaPetFilePath) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setTeaPet(ReadOnlyTeaPet teaPet) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ReadOnlyTeaPet getTeaPet() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public boolean hasStudent(Student student) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public boolean hasStudentName(String student) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public boolean hasStudentNameNonCaseSensitive(String student) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteStudent(Student target) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void addStudent(Student student) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ObservableList<Student> getFilteredStudentList() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public Path getAcademicsFilePath() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setAcademicsFilePath(Path addressBookFilePath) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setAcademics(ReadOnlyAcademics academics) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ReadOnlyAcademics getAcademics() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public boolean hasAssessment(Assessment assessment) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void deleteAssessment(Assessment target) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void addAssessment(Assessment assessment) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setAssessment(Assessment target, Assessment assessment) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void submitAssessment(Assessment target, List<String> students) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void addStudentToAssessments(String toAdd) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void removeStudentFromAssessments(String toRemove) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void updateStudentToAssessments(String prevName, String newName) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public boolean hasStudentSubmitted(Assessment assessment, String student) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void markAssessment(Assessment target, HashMap<String, Integer> submissions) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ObservableList<Assessment> getFilteredAcademicsList() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ObservableList<Assessment> getHomeworkList() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ObservableList<Assessment> getExamList() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void updateFilteredAcademicsList(Predicate<Assessment> predicate) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public Path getAdminFilePath() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setAdminFilePath(Path adminBookFilePath) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setAdmin(ReadOnlyAdmin admin) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ReadOnlyAdmin getAdmin() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public boolean hasDate(Date date) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void deleteDate(Date target) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void addDate(Date date) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setDate(Date target, Date date) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ObservableList<Date> getFilteredDateList() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void updateFilteredDateList(Predicate<Date> predicate) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public Path getNotesFilePath() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setNotesFilePath(Path notesFilePath) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setNotesManager(ReadOnlyNotes notes) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ReadOnlyNotes getNotesManager() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public boolean hasNote(Notes note) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void deleteNote(Notes toBeDeleted) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void addNote(Notes note) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setNote(Notes toBeChanged, Notes editedNote) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ObservableList<Notes> getFilteredNotesList() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void updateFilteredNotesList(Predicate<Notes> predicate) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public boolean hasVEvent(VEvent vEvent) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void addVEvent(VEvent vEvent) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void delete(Index index) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setVEvent(Index index, VEvent vEvent) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public VEvent getVEvent(Index index) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ObservableList<VEvent> getVEvents() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setEventHistory(ReadOnlyEvents events) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setEventHistory(Path eventHistoryFilePath) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ReadOnlyEvents getEventHistory() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public ReadOnlyVEvents getVEventHistory() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setEventScheduleLocalDateTime(LocalDateTime localDateTime) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public String getEventSchedulePref() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public LocalDateTime getEventScheduleLocalDateTime() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public EventScheduleView getEventScheduleView() {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public void setEventScheduleView(EventScheduleView eventScheduleView) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public List<Pair<Index, VEvent>> searchVEvents(String eventName) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public Pair<Index, VEvent> searchMostSimilarVEventName(String eventName) {
        throw new AssertionError("Assertion error, method should not be called.");
    }

    @Override
    public List<Pair<Index, VEvent>> getAllVEventsWithIndex() {
        throw new AssertionError("This method should not be called.");
    }

}
