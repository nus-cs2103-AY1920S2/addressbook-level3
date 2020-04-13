package seedu.nova.model;

import static java.util.Objects.requireNonNull;
import static seedu.nova.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.nova.commons.core.GuiSettings;
import seedu.nova.commons.core.LogsCenter;
import seedu.nova.logic.parser.ModeEnum;
import seedu.nova.model.person.Person;
import seedu.nova.model.plan.Plan;
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
 * Represents the in-memory model of the data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Nova nova;
    private final VersionedAddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final Schedule schedule;
    private final Plan plan;
    private final ProgressTracker progressTracker;
    private Mode mode;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(Nova nova, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(nova, userPrefs);

        logger.fine("Initializing with NOVA: " + nova + " and user prefs " + userPrefs);

        this.nova = nova;
        this.addressBook = nova.getAddressBookNova();
        this.userPrefs = new UserPrefs(userPrefs);
        this.progressTracker = nova.getProgressTracker();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.schedule = nova.getScheduleNova();
        // this.schedule = new Schedule(LocalDate.of(2020, 1, 13), LocalDate.of(2020, 5, 3));
        this.plan = nova.getStudyPlan();
        this.mode = new Mode(ModeEnum.HOME);
    }

    public ModelManager() {
        this(new Nova(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
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
    public Path getNovaFilePath() {
        return userPrefs.getNovaFilePath();
    }

    @Override
    public void setNovaFilePath(Path novaFilePath) {
        requireNonNull(novaFilePath);
        userPrefs.setNovaFilePath(novaFilePath);
    }

    @Override
    public Nova getNova() {
        nova.setScheduleNova(schedule);
        return this.nova;
    }

    //=========== Mode ==================================================================================
    @Override
    public Mode getMode() {
        return mode;
    }

    @Override
    public ModeEnum getModeEnum(Mode mode) {
        return mode.getModeEnum();
    }

    @Override
    public String getModeName(ModeEnum modeEnum) {
        return modeEnum.name();
    }

    @Override
    public void setModeEnum(Mode mode, ModeEnum modeEnum) {
        mode.setModeEnum(modeEnum);
    }

    //=========== AddressBook ================================================================================
    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
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

    //=========== Address Book =============================================================
    //=========== AB: Filtered Person List Accessors =============================================================

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

    //=========== AB: Undo/Redo =============================================================

    @Override
    public void commitAddressBook() {
        addressBook.commit();
    }

    @Override
    public void undoAddressBook() {
        addressBook.undo();
    }

    @Override
    public boolean canUndoAddressBook() {
        return addressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return addressBook.canRedo();
    }

    @Override
    public void redoAddressBook() {
        addressBook.redo();
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

    //=========== Scheduler Methods =============================================================

    @Override
    public String viewSchedule(LocalDate date) {

        return schedule.view(date);

    }

    @Override
    public String viewSchedule(int weekNumber) {

        return schedule.view(weekNumber);

    }

    @Override
    public boolean isWithinSem(LocalDate date) {

        return schedule.checkDateValidity(date);

    }

    @Override
    public boolean isWithinSem(int weekNumber) {

        return schedule.checkWeekValidity(weekNumber);

    }

    //=========== Event and Schedule =============================================================
    @Override
    public void addEvent(Event e) {
        schedule.addEvent(e);
    }

    @Override
    public void addAllLessons(Lesson l) {
        schedule.addAllLessons(l);
    }

    @Override
    public DateTimeSlotList getFreeSlotOn(LocalDate date) {
        return schedule.getDay(date).getFreeSlotList();
    }

    @Override
    public String viewFreeSlot(LocalDate date) {
        return getFreeSlotOn(date).toString();
    }

    @Override
    public String deleteEvent(LocalDate date, int index) {
        return schedule.deleteEvent(date, index).toString();
    }

    @Override
    public boolean deleteEvent(Event e) {
        return schedule.deleteEvent(e);
    }

    @Override
    public String addNote(String desc, LocalDate date, int index) {
        return schedule.addNote(desc, date, index);
    }

    //=========== Study Planner =============================================================
    @Override
    public void resetPlan() {
        plan.resetPlan();
    }

    @Override
    public boolean addRoutineTask(StrongTask task) {
        return plan.addTask(task);
    }

    @Override
    public boolean addFlexibleTask(WeakTask task) {
        return plan.addTask(task);
    }

    @Override
    public List<Task> getTaskList() {
        return plan.getTaskList();
    }

    @Override
    public Task searchTask(String name) {
        return plan.searchTask(name);
    }

    @Override
    public boolean deleteTask(Task task) {
        task.getEventAfter(LocalDate.now()).forEach(schedule::deleteEvent);
        return plan.deleteTask(task);
    }

    @Override
    public Event generateTaskEvent(Task task, LocalDate date) throws Exception {
        return plan.generateTaskEvent(task, date, schedule);
    }

    //=========== Progress Tracker =============================================================
    @Override
    public ProgressTracker getProgressTracker() {
        return progressTracker;
    }

    @Override
    public Ip getProgressTrackerIp() {
        return progressTracker.getIp();
    }

    @Override
    public Tp getProgressTrackerTp() {
        return progressTracker.getTp();
    }

    @Override
    public String listPtTask(String projectName, int weekNum) {
        return progressTracker.listPtTask(projectName, weekNum);
    }

    @Override
    public void addPtTask(String projectName, int weekNum, PtTask task) {
        progressTracker.addPtTask(projectName, weekNum, task);
    }

    @Override
    public boolean deletePtTask(String projectName, int weekNum, int taskNum) {
        return progressTracker.deletePtTask(projectName, weekNum, taskNum);
    }

    @Override
    public boolean editPtTask(String projectName, int weekNum, int taskNum, String taskDesc) {
        return progressTracker.editPtTask(projectName, weekNum, taskNum, taskDesc);
    }

    @Override
    public boolean setDonePtTask(String projectName, int weekNum, int taskNum) {
        return progressTracker.setDonePtTask(projectName, weekNum, taskNum);
    }

    @Override
    public boolean addPtNote(String projectName, int weekNum, int taskNum, String note) {
        return progressTracker.addPtNote(projectName, weekNum, taskNum, note);
    }

    @Override
    public boolean deletePtNote(String projectName, int weekNum, int taskNum) {
        return progressTracker.deletePtNote(projectName, weekNum, taskNum);
    }

    @Override
    public boolean editPtNote(String projectName, int weekNum, int taskNum, String note) {
        return progressTracker.editPtNote(projectName, weekNum, taskNum, note);
    }
}
