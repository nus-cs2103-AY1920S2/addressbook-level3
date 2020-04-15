package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import org.apache.commons.math3.util.Pair;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import jfxtras.icalendarfx.components.VEvent;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.academics.Academics;
import seedu.address.model.academics.Assessment;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.Admin;
import seedu.address.model.admin.Date;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.model.event.EventHistory;
import seedu.address.model.event.EventSchedulePrefs;
import seedu.address.model.event.EventScheduleView;
import seedu.address.model.event.ReadOnlyEvents;
import seedu.address.model.event.ReadOnlyVEvents;
import seedu.address.model.notes.Notes;
import seedu.address.model.notes.NotesManager;
import seedu.address.model.notes.ReadOnlyNotes;
import seedu.address.model.student.ReadOnlyTeaPet;
import seedu.address.model.student.Student;
import seedu.address.model.student.TeaPet;

/**
 * Represents the in-memory model of the tea pet data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final TeaPet teaPet;
    private final FilteredList<Student> filteredStudents;
    private final Academics academics;
    private final FilteredList<Assessment> filteredAssessments;
    private final Admin admin;
    private final FilteredList<Date> filteredDates;
    private final EventHistory eventHistory;
    private final EventSchedulePrefs eventSchedulePrefs;
    private final NotesManager notesManager;
    private final FilteredList<Notes> filteredNotes;


    /**
     * Initializes a ModelManager with the given teaPet and userPrefs.
     */
    public ModelManager(ReadOnlyTeaPet teaPet,
                        ReadOnlyAcademics academics,
                        ReadOnlyAdmin admin,
                        ReadOnlyNotes notes,
                        ReadOnlyEvents events,
                        ReadOnlyUserPrefs userPrefs) {


        super();
        requireAllNonNull(teaPet, academics, admin, notes, events, userPrefs);

        logger.fine("Initializing with tea pet: " + teaPet + " and user prefs " + userPrefs);

        this.teaPet = new TeaPet(teaPet);
        this.academics = new Academics(academics);
        this.admin = new Admin(admin);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.teaPet.getStudentList());
        filteredAssessments = new FilteredList<>(this.academics.getAcademicsList());
        filteredDates = new FilteredList<>(this.admin.getDateList());
        this.notesManager = new NotesManager(notes);
        filteredNotes = new FilteredList<>(this.notesManager.getNotesList());
        this.eventHistory = new EventHistory(events);
        this.eventSchedulePrefs = new EventSchedulePrefs(EventScheduleView.WEEKLY, LocalDateTime.now());
    }


    public ModelManager() {
        this(new TeaPet(), new Academics(), new Admin(), new NotesManager(), new EventHistory(), new UserPrefs());
    }

    // ==================== USER PREFS ====================
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    // ==================== TEA PET START ====================
    @Override
    public Path getTeaPetFilePath() {
        return userPrefs.getTeaPetFilePath();
    }

    @Override
    public void setTeaPetFilePath(Path teaPetFilePath) {
        requireNonNull(teaPetFilePath);
        userPrefs.setTeaPetFilePath(teaPetFilePath);
    }

    @Override
    public void setTeaPet(ReadOnlyTeaPet teaPet) {
        this.teaPet.resetData(teaPet);
    }

    @Override
    public ReadOnlyTeaPet getTeaPet() {
        return teaPet;
    }

    @Override
    public boolean hasStudent(Student student) {
        requireNonNull(student);
        return teaPet.hasStudent(student);
    }

    @Override
    public boolean hasStudentName(String student) {
        requireNonNull(student);
        return teaPet.hasStudentName(student);
    }

    @Override
    public boolean hasStudentNameNonCaseSensitive(String student) {
        requireNonNull(student);
        return teaPet.hasStudentNameNonCaseSensitive(student);
    }

    @Override
    public void deleteStudent(Student target) {
        teaPet.removeStudent(target);
    }

    @Override
    public void addStudent(Student student) {
        teaPet.addStudent(student);
        updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
    }

    @Override
    public void setStudent(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);
        teaPet.setStudent(target, editedStudent);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Student} backed by the internal list of
     * {@code versionedTeaPet}
     */
    @Override
    public ObservableList<Student> getFilteredStudentList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredStudentList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }
    // ==================== TEA PET END ====================

    // ==================== ACADEMICS START ====================
    @Override
    public Path getAcademicsFilePath() {
        return userPrefs.getAcademicsFilePath();
    }

    @Override
    public void setAcademicsFilePath(Path academicsFilePath) {
        requireNonNull(academicsFilePath);
        userPrefs.setAcademicsFilePath(academicsFilePath);
    }

    @Override
    public void setAcademics(ReadOnlyAcademics academics) {
        this.academics.resetData(academics);
    }

    @Override
    public ReadOnlyAcademics getAcademics() {
        return academics;
    }

    @Override
    public boolean hasAssessment(Assessment assessment) {
        requireNonNull(assessment);
        return academics.hasAssessment(assessment);
    }

    @Override
    public void deleteAssessment(Assessment target) {
        academics.removeAssessment(target);
    }

    @Override
    public void addAssessment(Assessment assessment) {
        academics.addAssessment(assessment);
        updateFilteredAcademicsList(PREDICATE_SHOW_ALL_ASSESSMENTS);
    }

    @Override
    public void setAssessment(Assessment target, Assessment editedAssessment) {
        requireAllNonNull(target, editedAssessment);
        academics.setAssessment(target, editedAssessment);
    }

    @Override
    public void submitAssessment(Assessment target, List<String> students) {
        requireAllNonNull(target, students);
        academics.submitAssessment(target, students);
    }

    @Override
    public void addStudentToAssessments(String toAdd) {
        requireNonNull(toAdd);
        academics.addStudentToAssessments(toAdd);
    }

    @Override
    public void removeStudentFromAssessments(String toRemove) {
        requireNonNull(toRemove);
        academics.removeStudentFromAssessments(toRemove);
    }

    @Override
    public void updateStudentToAssessments(String prevName, String newName) {
        requireAllNonNull(prevName, newName);
        academics.updateStudentToAssessments(prevName, newName);
    }

    @Override
    public boolean hasStudentSubmitted(Assessment assessment, String student) {
        requireAllNonNull(assessment, student);
        return academics.hasStudentSubmitted(assessment, student);
    }

    @Override
    public void markAssessment(Assessment target, HashMap<String, Integer> submissions) {
        requireAllNonNull(target, submissions);
        academics.markAssessment(target, submissions);
    }

    @Override
    public ObservableList<Assessment> getFilteredAcademicsList() {
        return filteredAssessments;
    }

    @Override
    public ObservableList<Assessment> getHomeworkList() {
        return academics.getHomeworkList();
    }

    @Override
    public ObservableList<Assessment> getExamList() {
        return academics.getExamList();
    }

    @Override
    public void updateFilteredAcademicsList(Predicate<Assessment> predicate) {
        requireNonNull(predicate);
        filteredAssessments.setPredicate(predicate);
    }
    // ==================== ACADEMICS END ====================

    // ==================== ADMIN START ====================
    @Override
    public Path getAdminFilePath() {
        return userPrefs.getAdminFilePath();
    }

    @Override
    public void setAdminFilePath(Path adminBookFilePath) {
        this.admin.resetData(admin);
    }

    @Override
    public void setAdmin(ReadOnlyAdmin admin) {
        this.admin.resetData(admin);
    }

    @Override
    public ReadOnlyAdmin getAdmin() {
        return admin;
    }

    @Override
    public boolean hasDate(Date date) {
        requireNonNull(date);
        return admin.hasDate(date);
    }

    @Override
    public void deleteDate(Date target) {
        admin.removeDate(target);
    }

    @Override
    public void addDate(Date date) {
        admin.addDate(date);
        updateFilteredDateList(PREDICATE_SHOW_ALL_DATES);
    }

    @Override
    public void setDate(Date target, Date editedDate) {
        requireAllNonNull(target, editedDate);
        admin.setDate(target, editedDate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Date} backed by the internal list of
     * {@code versionedDate}
     */
    @Override
    public ObservableList<Date> getFilteredDateList() {
        return filteredDates;
    }

    @Override
    public void updateFilteredDateList(Predicate<Date> predicate) {
        requireNonNull(predicate);
        filteredDates.setPredicate(predicate);
    }
    // ==================== ADMIN END ====================

    // ==================== NOTES START ====================
    @Override
    public Path getNotesFilePath() {
        return userPrefs.getNotesFilePath();
    }

    @Override
    public void setNotesFilePath(Path notesFilePath) {
        requireNonNull(notesFilePath);
        userPrefs.setNotesFilePath(notesFilePath);
    }

    @Override
    public void setNotesManager(ReadOnlyNotes notes) {
        this.notesManager.resetData(notes);
    }

    @Override
    public ReadOnlyNotes getNotesManager() {
        return notesManager;
    }

    @Override
    public boolean hasNote(Notes note) {
        requireNonNull(note);
        return notesManager.hasNote(note);
    }

    @Override
    public void deleteNote(Notes target) {
        notesManager.removeNote(target);
    }

    @Override
    public void addNote(Notes note) {
        notesManager.addNote(note);
        updateFilteredNotesList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void setNote(Notes toBeChanged, Notes editedNote) {
        requireAllNonNull(toBeChanged, editedNote);

        notesManager.setNote(toBeChanged, editedNote);
    }

    @Override
    public ObservableList<Notes> getFilteredNotesList() {
        return filteredNotes;
    }

    @Override
    public void updateFilteredNotesList(Predicate<Notes> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
    }

    // ==================== NOTES END ====================

    // ==================== EVENTS START ====================
    @Override
    public void setEventHistory(Path eventHistoryFilePath) {
        userPrefs.setEventHistoryFilePath(eventHistoryFilePath);
    }

    @Override
    public void setEventHistory(ReadOnlyEvents events) {
        this.eventHistory.resetDataWithReadOnlyEvents(events);
    }

    @Override
    public ReadOnlyVEvents getVEventHistory() {
        return eventHistory;
    }

    @Override
    public ReadOnlyEvents getEventHistory() {
        return eventHistory;
    }

    @Override
    public LocalDateTime getEventScheduleLocalDateTime() {
        return eventSchedulePrefs.getLocalDateTime();
    }

    @Override
    public void setEventScheduleLocalDateTime(LocalDateTime localDateTime) {
        this.eventSchedulePrefs.setLocalDateTime(localDateTime);
    }

    @Override
    public String getEventSchedulePref() {
        return eventSchedulePrefs.toString();
    }

    @Override
    public EventScheduleView getEventScheduleView() {
        return eventSchedulePrefs.getEventScheduleView();
    }

    @Override
    public void setEventScheduleView(EventScheduleView eventScheduleView) {
        eventSchedulePrefs.setEventScheduleView(eventScheduleView);
    }

    @Override
    public boolean hasVEvent(VEvent vEvent) {
        return eventHistory.contains(vEvent);
    }

    @Override
    public void addVEvent(VEvent vEvent) {
        eventHistory.addVEvent(vEvent);
    }

    @Override
    public void delete(Index index) {
        eventHistory.deleteVEvent(index);
    }

    @Override
    public void setVEvent(Index index, VEvent vEvent) {
        eventHistory.setVEvent(index, vEvent);
    }

    @Override
    public VEvent getVEvent(Index index) {
        return eventHistory.getVEvent(index);
    }

    @Override
    public ObservableList<VEvent> getVEvents() {
        return eventHistory.getVEvents();
    }

    @Override
    public List<Pair<Index, VEvent>> getAllVEventsWithIndex() {
        return eventHistory.getAllVEventsWithIndex();
    }

    @Override
    public List<Pair<Index, VEvent>> searchVEvents(String eventName) {
        return eventHistory.searchVEvents(eventName);
    }

    @Override
    public Pair<Index, VEvent> searchMostSimilarVEventName(String eventName) {
        return eventHistory.searchMostSimilarVEventName(eventName);
    }
    // ==================== EVENTS END ====================

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return teaPet.equals(other.teaPet)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents)
                && academics.equals(other.academics)
                && filteredAssessments.equals(other.filteredAssessments)
                && eventHistory.equals(other.eventHistory)
                && notesManager.equals(other.notesManager)
                && filteredNotes.equals(other.filteredNotes);
    }
}
