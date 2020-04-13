package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.model.calender.Task;
import seedu.address.model.diary.DiaryBook;
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
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final ObservableList<DiaryEntry> diaryEntries;
    private DiaryBook diaryBook;
    private final FilteredList<Notes> filesInFolder;
    private ModuleBook moduleBook;
    private FilteredList<Task> deadlineTaskList;
    private final FilteredList<NusModule> moduleListTaken;
    private Profile studentProfile;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ModuleBook moduleBook, ObservableList<Task> taskList) {
        super();
        requireAllNonNull(addressBook, userPrefs, moduleBook);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs
                + " and module list: " + moduleBook);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.moduleBook = moduleBook;
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        diaryBook = new DiaryBook();
        diaryEntries = diaryBook.getInternalList();
        filesInFolder = new FilteredList<>(Notes.getAllFilesInFolder());
        deadlineTaskList = new FilteredList<>(taskList);
        Task.setDeadlineTaskList(taskList);
        moduleListTaken = new FilteredList<>(moduleBook.getModulesTakenList());
        studentProfile = new Profile(moduleBook);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new ModuleBook(),
                FXCollections.observableList(new ArrayList<>()));
    }


    //=========== UserPrefs ==================================================================================

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

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Diary Module ==================================================================================
    @Override
    public void addDiaryEntry(DiaryEntry diaryEntry) {
        diaryBook.addEntry(diaryEntry);
    }

    @Override
    public boolean isEmptyDiaryEntry(DiaryEntry diaryEntry) {
        // implement later
        return false;
    }

    @Override
    public boolean isValidEntryId(int entryId) {
        int minId = 1;
        int maxId = diaryBook.getDiaryEntries().size();
        return (entryId >= minId) && (entryId <= maxId);
    }

    @Override
    public void deleteDiaryEntry(int entryId) {
        diaryBook.deleteEntry(entryId);
    }

    @Override
    public void tagWeather(int entryId, Weather weather) {
        diaryBook.tagWeather(entryId, weather);
    }

    @Override
    public void tagMood(int entryId, Mood mood) {
        diaryBook.tagMood(entryId, mood);
    }

    @Override
    public DiaryEntry getDiaryEntryById(int entryId) {
        return diaryBook.getDiaryEntryById(entryId);
    }

    @Override
    public List<Integer> getListOfIdsByDate(LocalDate date) {
        return diaryBook.getListOfIdsByDate(date);
    }

    @Override
    public boolean isExistingDate(LocalDate date) {
        return diaryBook.isExistingDate(date);
    }

    @Override
    public String showDiaryLog() {
        return diaryBook.showLog();
    }

    @Override
    public Path getDiaryBookFilePath() {
        return null;
    }

    @Override
    public ObservableList<DiaryEntry> getDiaryList() {
        return diaryEntries;
    }


    //=========== Cap Module ==================================================================================

    /**
     * Dummy java docs
     *
     * @param
     * @return
     */
    @Override
    public boolean hasModule(ModuleCode moduleCode) {
        return moduleBook.hasModule(moduleCode);
    }

    @Override
    public void addModule(NusModule module) {
        moduleBook.addModule(module);
    }

    @Override
    public void deleteModule(ModuleCode moduleCode) {
        moduleBook.deleteModule(moduleCode);
    }

    @Override
    public void gradeModule(ModuleCode moduleCode, Grade updatedGrade) {
        moduleBook.gradeModule(moduleCode, updatedGrade);
    }

    @Override
    public double getCap() {
        return moduleBook.getCap();
    }

    public ObservableList<NusModule> getModulesListTaken() {
        return moduleListTaken;
    }

    @Override
    public void updateModulesListTaken(Predicate<NusModule> predicate) {
        requireNonNull(predicate);
        moduleListTaken.setPredicate(predicate);
    }

    @Override
    public Path getModuleBookFilePath() {
        return userPrefs.getModuleBookFilePath();
    }

    @Override
    public ModuleBook getModuleBook() {
        return this.moduleBook;
    }

    @Override
    public int getSizeOfModuleTaskList(ModuleCode moduleCode) {
        return moduleBook.getSizeOfModuleTaskList(moduleCode);
    }

    @Override
    public List<ModuleTask> getModuleTaskList(ModuleCode moduleCode) {
        return moduleBook.getModuleTaskList(moduleCode);
    }

    @Override
    public void deleteModuleTask(ModuleCode moduleCode, Index index) {
        moduleBook.deleteModuleTask(moduleCode, index);
    }

    @Override
    public void doneModuleTask(ModuleCode moduleCode, Index index) {
        moduleBook.doneModuleTask(moduleCode, index);
    }

    @Override
    public void addModuleTask(ModuleTask moduleTask) {
        moduleBook.addModuleTask(moduleTask);
    }

    @Override
    public String getModuleTaskInfo(ModuleCode moduleCode) {
        return moduleBook.getModuleTaskInfo(moduleCode);
    }

    @Override
    public String getTaskBreakdown() {
        return moduleBook.getTaskBreakdown();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Notes Module ==================================================================================

    /**
     * Returns an list of String that contains what is currently in the folder
     */
    @Override
    public ObservableList<Notes> getFilesInFolderList() {
        return filesInFolder;
    }

    @Override
    public void updateNotesList(Predicate<Notes> predicate) {
        requireNonNull(predicate);
        filesInFolder.setPredicate(predicate);
    }


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
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //=========== Deadline Module ==================================================================================

    @Override
    public boolean isEmptyDeadline(Task deadline) {
        return false;
    }

    /**
     * Returns a list of task in the deadline task list
     */
    @Override
    public ObservableList<Task> getDeadlineTaskList() {
        return deadlineTaskList;
    }

    @Override
    public void updateDeadlineTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        deadlineTaskList.setPredicate(predicate);
    }

    @Override
    public List<Task> findTasksByDate(String date) {
        List<Task> targetTasks = new ArrayList<>();
        for (Task task : deadlineTaskList) {
            if (task.getDate().equals(date)) {
                targetTasks.add(task);
            }
        }
        return targetTasks;
    }

    @Override
    public List<Task> findTasksByCat(String cat) {
        List<Task> targetTasks = new ArrayList<>();
        for (Task task : deadlineTaskList) {
            if (task.getCategory().equals(cat)) {
                targetTasks.add(task);
            }
        }
        return targetTasks;
    }

    @Override
    public void addDeadlineTask(Task deadline) {

        Task.add(deadline);
    }

    @Override
    public Task doneDeadlineTask(Task deadline) {
        Task.getDeadlineTaskList().get(deadline.getIndex() - 1).markAsDone();

        Task done = Task.getDeadlineTaskList().get(deadline.getIndex() - 1);
        Task.getDeadlineTaskList().remove(deadline.getIndex() - 1);
        Task.getDeadlineTaskList().add(done);
        return done;
    }

    @Override
    public void sortTaskList() {
        Task.sortDeadlineTaskList("date");
        Task.sortDeadlineTaskList("done");
    }

    @Override
    public Task deleteTask(Task task) {
        Task.getDeadlineTaskList().remove(task);
        Task.removeTaskPerDate(task.getDate(), task);
        return task;
    }

    @Override
    public void sortTask(String param) {
        Task.sortDeadlineTaskList(param);
    }


    //=========== TD Module ==================================================================================


    @Override
    public void addToDo(Task todo) {

    }

    @Override
    public boolean isEmptyToDo(Task todo) {
        return false;
    }


    //=========== Profile Module ==================================================================================
    @Override
    public void updateMajor(Major major) {
        studentProfile.setMajor(major);
    }

    public ObservableValue<String> getMajor() {
        return studentProfile.getMajor();
    }

    public Profile getProfile() {
        return studentProfile;
    }


}
