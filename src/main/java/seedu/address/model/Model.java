package seedu.address.model;

import java.nio.file.Path;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.model.calender.Task;
import seedu.address.model.diary.DiaryEntry;
import seedu.address.model.diary.mood.Mood;
import seedu.address.model.diary.weather.Weather;
import seedu.address.model.notes.Notes;
import seedu.address.model.nusmodule.Grade;
import seedu.address.model.nusmodule.Major;
import seedu.address.model.nusmodule.ModuleCode;
import seedu.address.model.nusmodule.ModuleTask;
import seedu.address.model.nusmodule.NusModule;
import seedu.address.model.person.Person;
import seedu.address.model.studentprofile.Profile;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Notes> PREDICATE_SHOW_ALL_NOTES = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Task> PREDICATE_SHOW_ALL_TASK = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<NusModule> PREDICATE_SHOW_ALL_MODULES_TAKEN = unused -> true;

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

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getDiaryBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
    //=========== Diary Module ==================================================================================
    boolean isEmptyDiaryEntry(DiaryEntry diaryEntry);

    void addDiaryEntry(DiaryEntry diaryEntry);

    String showDiaryLog();

    ObservableList<DiaryEntry> getDiaryList();

    boolean isValidEntryId(int entryId);

    void deleteDiaryEntry(int entryId);

    void tagWeather(int entryId, Weather weather);

    void tagMood(int entryId, Mood mood);

    DiaryEntry getDiaryEntryById(int entryId);

    List<Integer> getListOfIdsByDate(LocalDate date);

    boolean isExistingDate(LocalDate date);

    //=========== Notes Module ==================================================================================
    /** Returns an list of String that contains what is currently in the folder */
    ObservableList<Notes> getFilesInFolderList();

    /**
     * Updates the notes list by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateNotesList(Predicate<Notes> predicate);

    //=========== CAP Module ==================================================================================
    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    boolean hasModule(ModuleCode moduleCode);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the address book.
     */
    void addModule(NusModule module);

    void deleteModule(ModuleCode moduleCode);

    void gradeModule(ModuleCode moduleCode, Grade grade);

    double getCap();

    void addModuleTask(ModuleTask moduleTask);

    ObservableList<NusModule> getModulesListTaken();

    ModuleBook getModuleBook();

    int getSizeOfModuleTaskList(ModuleCode moduleCode);

    void deleteModuleTask(ModuleCode moduleCode, Index index);

    void doneModuleTask(ModuleCode moduleCode, Index index);

    String getModuleTaskInfo(ModuleCode moduleCode);

    String getTaskBreakdown();

    List<ModuleTask> getModuleTaskList(ModuleCode moduleCode);

    List<Task> findTasksByDate(String date);

    List<Task> findTasksByCat(String cat);

    void updateModulesListTaken(Predicate<NusModule> predicate);

    Path getModuleBookFilePath();

    //=========== Deadline ==================================================================================

    /**
     * Adds deadline.
     */
    void addDeadlineTask(Task deadline);

    void sortTaskList();

    Task deleteTask(Task task);

    Task doneDeadlineTask(Task deadline);

    void sortTask(String param);

    /**
     * Checks if content of deadline is empty
     */
    boolean isEmptyDeadline(Task deadline);

    /** Returns an list of Deadline that is currently in the list */
    ObservableList<Task> getDeadlineTaskList();

    /**
     * Updates the deadline list by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateDeadlineTaskList(Predicate<Task> predicate);

    //=========== TD ==================================================================================

    /**
     * Adds todo.
     */
    void addToDo(Task todo);

    /**
     * Checks if content of todo is empty
     */
    boolean isEmptyToDo(Task todo);

    //=========== Profile ==================================================================================

    void updateMajor(Major major);

    ObservableValue<String> getMajor();

    Profile getProfile();
}
