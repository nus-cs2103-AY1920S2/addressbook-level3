package seedu.address.model;

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
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Student> PREDICATE_SHOW_ALL_STUDENTS = unused -> true;
    Predicate<Assessment> PREDICATE_SHOW_ALL_ASSESSMENTS = unused -> true;
    Predicate<Date> PREDICATE_SHOW_ALL_DATES = unused -> true;
    Predicate<Notes> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    // ==================== TEA PET START ====================
    /**
     * Returns the user prefs' tea pet file path.
     */
    Path getTeaPetFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setTeaPetFilePath(Path teaPetFilePath);

    /**
     * Replaces tea pet data with the data in {@code teaPet}.
     */
    void setTeaPet(ReadOnlyTeaPet teaPet);

    /** Returns the TeaPet */
    ReadOnlyTeaPet getTeaPet();

    /**
     * Returns true if a student with the same identity as {@code student} exists in tea pet.
     */
    boolean hasStudent(Student student);

    /**
     * Returns true if a student with the same identity as {@code student} exists in tea pet.
     */
    boolean hasStudentName(String student);

    /**
     * Returns true if a student with the same name non case sensitive as {@code student} exists in tea pet.
     */
    boolean hasStudentNameNonCaseSensitive(String student);

    /**
     * Deletes the given student.
     * The student must exist in tea pet.
     */
    void deleteStudent(Student target);

    /**
     * Adds the given student.
     * {@code student} must not already exist in tea pet.
     */
    void addStudent(Student student);

    /**
     * Replaces the given student {@code target} with {@code editedStudent}.
     * {@code target} must exist in tea pet.
     * The student identity of {@code editedStudent} must not be the same as another existing student
     * in tea pet.
     */
    void setStudent(Student target, Student editedStudent);

    /** Returns an unmodifiable view of the filtered student list */
    ObservableList<Student> getFilteredStudentList();

    /**
     * Updates the filter of the filtered student list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredStudentList(Predicate<Student> predicate);
    // ==================== TEA PET END ====================

    // ==================== ACADEMICS START ====================
    /**
     * Returns the user prefs' academics file path.
     */
    Path getAcademicsFilePath();

    /**
     * Sets the user prefs' academics file path.
     */
    void setAcademicsFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAcademics(ReadOnlyAcademics academics);

    /** Returns the Academics */
    ReadOnlyAcademics getAcademics();

    /**
     * Returns true if an assessment with the same identity as {@code assessment} exists in the Academics
     * record.
     */
    boolean hasAssessment(Assessment assessment);

    /**
     * Returns the assessment that has been deleted based on the index.
     */
    void deleteAssessment(Assessment target);

    /**
     * Adds the given assessment. {@code assessment} must not exist in the assessment list.
     */
    void addAssessment(Assessment assessment);

    /**
     * Replaces the assessment at the specified index.
     */
    void setAssessment(Assessment target, Assessment assessment);

    /**
     * Submits students' submission for the specified assessment.
     */
    void submitAssessment(Assessment target, List<String> students);

    /**
     * Adds new student to the submission tracker of all assessments.
     */
    void addStudentToAssessments(String toAdd);

    /**
     * Removes student to the submission tracker of all assessments.
     */
    void removeStudentFromAssessments(String toRemove);

    /**
     * Updates student name to the submission tracker of all assessments.
     */
    void updateStudentToAssessments(String prevName, String newName);

    /**
     * Returns true if the student has submitted their work for the given assessment.
     * record.
     */
    boolean hasStudentSubmitted(Assessment assessment, String student);

    /**
     * Mark students' submission for the specified assessment.
     */
    void markAssessment(Assessment target, HashMap<String, Integer> submissions);

    /** Returns an unmodifiable view of the filtered academics list */
    ObservableList<Assessment> getFilteredAcademicsList();

    /** Returns a list of homework assessments */
    ObservableList<Assessment> getHomeworkList();

    /** Returns a list of homework assessments */
    ObservableList<Assessment> getExamList();

    /**
     * Updates the filter of the filtered academics list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAcademicsList(Predicate<Assessment> predicate);
    // ==================== ACADEMICS END ====================

    // ==================== ADMIN START ====================
    /**
     * Returns the user prefs' admin file path.
     */
    Path getAdminFilePath();

    /**
     * Sets the user prefs' admin file path.
     */
    void setAdminFilePath(Path adminBookFilePath);

    /**
     * Replaces admin data with the data in {@code admin}.
     */
    void setAdmin(ReadOnlyAdmin admin);

    /** Returns the Admin */
    ReadOnlyAdmin getAdmin();

    /**
     * Returns true if an date with the same identity as {@code date} exists in the admin
     * record.
     */
    boolean hasDate(Date date);

    /**
     * Returns the date that has been deleted based on the index.
     */
    void deleteDate(Date target);

    /**
     * Adds the given date. {@code date} must not exist in the date list.
     */
    void addDate(Date date);

    /**
     * Replaces the date at the specified index.
     */
    void setDate(Date target, Date date);

    /** Returns an unmodifiable view of the filtered date list */
    ObservableList<Date> getFilteredDateList();

    /**
     * Updates the filter of the filtered date list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDateList(Predicate<Date> predicate);
    // ==================== ADMIN END ====================

    // ==================== NOTES START ====================
    /**
     * Returns the user prefs' notes file path.
     */
    Path getNotesFilePath();

    /**
     * Sets the user prefs' notes file path.
     */
    void setNotesFilePath(Path notesFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setNotesManager(ReadOnlyNotes notes);

    /** Returns the NotesManager */
    ReadOnlyNotes getNotesManager();

    /**
     * Returns true if a note with the same identity as {@code note} exists in the Notes
     * record.
     */
    boolean hasNote(Notes note);

    /**
     * Returns the note that has been deleted based on the index.
     */
    void deleteNote(Notes toBeDeleted);

    /**
     * Adds the given note. {@code note} must not exist in the notes list.
     */
    void addNote(Notes note);

    /**
     * Replaces the note at the specified index.
     */
    void setNote(Notes toBeChanged, Notes editedNote);

    /** Returns an unmodifiable view of the filtered notes list */
    ObservableList<Notes> getFilteredNotesList();

    /**
     * Updates the filter of the filtered academics list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredNotesList(Predicate<Notes> predicate);
    // ==================== NOTES END ====================

    // ==================== EVENTS START ====================

    boolean hasVEvent(VEvent vEvent);

    void addVEvent(VEvent vEvent);

    void delete(Index index);

    void setVEvent(Index index, VEvent vEvent);

    VEvent getVEvent(Index index);

    ObservableList<VEvent> getVEvents();

    List<Pair<Index, VEvent>> getAllVEventsWithIndex();

    void setEventHistory(ReadOnlyEvents events);

    void setEventHistory(Path eventHistoryFilePath);

    ReadOnlyEvents getEventHistory();

    ReadOnlyVEvents getVEventHistory();

    void setEventScheduleLocalDateTime(LocalDateTime localDateTime);

    String getEventSchedulePref();

    LocalDateTime getEventScheduleLocalDateTime();

    EventScheduleView getEventScheduleView();

    void setEventScheduleView(EventScheduleView eventScheduleView);


    List<Pair<Index, VEvent>> searchVEvents(String eventName);

    Pair<Index, VEvent> searchMostSimilarVEventName(String eventName);

    // ==================== EVENTS END ====================
}
