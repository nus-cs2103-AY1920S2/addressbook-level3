package seedu.nova.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.nova.commons.core.GuiSettings;
import seedu.nova.logic.parser.ModeEnum;
import seedu.nova.model.person.Person;
import seedu.nova.model.plan.StrongTask;
import seedu.nova.model.plan.Task;
import seedu.nova.model.plan.WeakTask;
import seedu.nova.model.progresstracker.Ip;
import seedu.nova.model.progresstracker.ProgressTracker;
import seedu.nova.model.progresstracker.PtTask;
import seedu.nova.model.progresstracker.Tp;
import seedu.nova.model.schedule.event.Event;
import seedu.nova.model.schedule.event.Lesson;
import seedu.nova.model.util.time.slotlist.DateTimeSlotList;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' nova book file path.
     */
    Path getNovaFilePath();

    /**
     * Sets the user prefs' nova book file path.
     */
    void setNovaFilePath(Path addressBookFilePath);

    /**
     * Retrieves Nova object
     * @return Nova object
     */
    Nova getNova();

    /**
     * Retrieves mode
     * @return mode
     */
    Mode getMode();

    /**
     * Retrieves ModeEnum in mode
     * @param mode mode to get ModeEnum from
     * @return ModeEnum in mode
     */
    ModeEnum getModeEnum(Mode mode);

    /**
     * Gets the string form of the modeEnum
     * @param modeEnum modeEnum to get name from
     * @return name of modeEnum
     */
    String getModeName(ModeEnum modeEnum);

    /**
     * Sets modeEnum in mode
     * @param mode mode to set modeEnum
     * @param modeEnum modeEnum to be set
     */
    void setModeEnum(Mode mode, ModeEnum modeEnum);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces nova book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the nova book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the nova book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the nova book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the nova book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the nova book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    void commitAddressBook();

    void undoAddressBook();

    boolean canUndoAddressBook();

    boolean canRedoAddressBook();

    void redoAddressBook();

    String viewSchedule(LocalDate date);

    String viewSchedule(int weekNumber);

    boolean isWithinSem(LocalDate date);

    boolean isWithinSem(int weekNumber);

    void addEvent(Event e);

    void addAllLessons(Lesson l);

    DateTimeSlotList getFreeSlotOn(LocalDate date);

    String viewFreeSlot(LocalDate date);

    String deleteEvent(LocalDate date, int index);

    boolean deleteEvent(Event event);

    String addNote(String desc, LocalDate date, int index);

    //==============studyplanner=============

    void resetPlan();

    boolean addRoutineTask(StrongTask st);

    boolean addFlexibleTask(WeakTask wt);

    List<Task> getTaskList();

    Task searchTask(String name);

    boolean deleteTask(Task task);

    Event generateTaskEvent(Task task, LocalDate date) throws Exception;

    //============== Progress Tracker =============

    /**
     * Retrieves ProgressTracker object
     * @return ProgressTracker object
     */
    ProgressTracker getProgressTracker();

    /**
     * Retrieves Ip object
     * @return Ip object
     */
    Ip getProgressTrackerIp();

    /**
     * Retrieves Tp object
     * @return Tp object
     */
    Tp getProgressTrackerTp();

    /**
     * Lists PtTasks in specified week of project
     * @param projectName project to list tasks in
     * @param weekNum week to list tasks in
     * @return String of lists of tasks
     */
    String listPtTask(String projectName, int weekNum);

    /**
     * Adds PtTask
     * @param projectName Project to add PtTask to
     * @param weekNum week to add PtTask to
     * @param task PtTask to be added
     */
    void addPtTask(String projectName, int weekNum, PtTask task);

    /**
     * Deletes PtTask
     * @param projectName Project the PtTask in
     * @param weekNum week the PtTask in
     * @param taskNum task number of PtTask to be deleted
     * @return boolean on whether execution was successful
     */
    boolean deletePtTask(String projectName, int weekNum, int taskNum);

    /**
     * Edits PtTask
     * @param projectName Project the PtTask in
     * @param weekNum week the PtTask in
     * @param taskNum task number of PtTask to be deleted
     * @param taskDesc new task description
     * @return boolean on whether execution was successful
     */
    boolean editPtTask(String projectName, int weekNum, int taskNum, String taskDesc);

    /**
     * Changes done status of PtTask
     * @param projectName Project the PtTask in
     * @param weekNum week the PtTask in
     * @param taskNum task number of PtTask to change done
     * @return boolean on whether execution was successful
     */
    boolean setDonePtTask(String projectName, int weekNum, int taskNum);

    /**
     * Adds note to PtTask
     * @param projectName Project the PtTask in
     * @param weekNum week the PtTask in
     * @param taskNum task number of PtTask to add note to
     * @param note note to be added
     * @return boolean on whether execution was successful
     */
    boolean addPtNote(String projectName, int weekNum, int taskNum, String note);

    /**
     * Deletes note from PtTask
     * @param projectName Project the PtTask in
     * @param weekNum week the PtTask in
     * @param taskNum task number of PtTask to delete note
     * @return boolean on whether execution was successful
     */
    boolean deletePtNote(String projectName, int weekNum, int taskNum);

    /**
     * Edits note of PtTask
     * @param projectName Project the PtTask in
     * @param weekNum week the PtTask in
     * @param taskNum task number of PtTask to edit note
     * @param note new note
     * @return boolean on whether execution was successful
     */
    boolean editPtNote(String projectName, int weekNum, int taskNum, String note);
}
