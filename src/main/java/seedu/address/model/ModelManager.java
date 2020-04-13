package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonList;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.util.action.DoableAction;
import seedu.address.model.util.action.DoableActionList;

/**
 * Represents the in-memory model of Mod Manager data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ModManager modManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;
    private final FilteredList<Facilitator> filteredFacilitators;
    private final FilteredList<Task> filteredTasks;
    private Optional<Module> moduleToShow;
    private final FilteredList<Facilitator> facilitatorsForModule;
    private final FilteredList<Task> tasksForModule;
    private Calendar calendar;
    private DoableActionList actionList;

    /**
     * Initializes a ModelManager with the given modManager and userPrefs.
     */
    public ModelManager(ReadOnlyModManager modManager, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(modManager, userPrefs);

        logger.fine("Initializing with Mod Manager: " + modManager + " and user prefs " + userPrefs);

        this.modManager = new ModManager(modManager);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFacilitators = new FilteredList<>(this.modManager.getFacilitatorList());
        filteredModules = new FilteredList<>(this.modManager.getModuleList());
        filteredTasks = new FilteredList<>(this.modManager.getTaskList());
        moduleToShow = Optional.empty();
        facilitatorsForModule = new FilteredList<>(this.modManager.getFacilitatorList());
        facilitatorsForModule.setPredicate(PREDICATE_SHOW_NO_FACILITATORS);
        tasksForModule = new FilteredList<>(this.modManager.getTaskList());
        tasksForModule.setPredicate(PREDICATE_SHOW_NO_TASKS);
        calendar = Calendar.getNowCalendar();
        actionList = new DoableActionList();
    }

    public ModelManager() {
        this(new ModManager(), new UserPrefs());
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
    public Path getModManagerFilePath() {
        return userPrefs.getModManagerFilePath();
    }

    @Override
    public void setModManagerFilePath(Path modManagerFilePath) {
        requireNonNull(modManagerFilePath);
        userPrefs.setModManagerFilePath(modManagerFilePath);
    }

    //=========== ModManager ================================================================================

    @Override
    public void setModManager(ReadOnlyModManager modManager) {
        this.modManager.resetData(modManager);
    }

    @Override
    public ReadOnlyModManager getModManager() {
        return modManager;
    }

    //=========== Module ================================================================================

    @Override
    public boolean hasModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        return modManager.hasModuleCode(moduleCode);
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modManager.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        modManager.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        modManager.addModule(module);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        modManager.setModule(target, editedModule);
    }

    @Override
    public Optional<Module> getModule() {
        return moduleToShow;
    }

    @Override
    public Optional<Module> findModule(ModuleCode moduleCode) {
        return modManager.findModule(moduleCode);
    }

    @Override
    public void updateModule(Optional<Module> moduleToShow) {
        this.moduleToShow = moduleToShow;
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedModManager}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Facilitator ================================================================================

    @Override
    public boolean hasFacilitator(Facilitator facilitator) {
        requireNonNull(facilitator);
        return modManager.hasFacilitator(facilitator);
    }

    @Override
    public void deleteFacilitator(Facilitator target) {
        modManager.removeFacilitator(target);
    }

    @Override
    public void addFacilitator(Facilitator facilitator) {
        modManager.addFacilitator(facilitator);
        updateFilteredFacilitatorList(PREDICATE_SHOW_ALL_FACILITATORS);
    }

    @Override
    public void setFacilitator(Facilitator target, Facilitator editedFacilitator) {
        requireAllNonNull(target, editedFacilitator);

        modManager.setFacilitator(target, editedFacilitator);
    }

    @Override
    public void deleteModuleCodeFromFacilitatorList(ModuleCode target) {
        modManager.removeModuleCodeFromFacilitatorList(target);
    }

    @Override
    public void setModuleCodeInFacilitatorList(ModuleCode target, ModuleCode editedModuleCode) {
        modManager.setModuleCodeInFacilitatorList(target, editedModuleCode);
    }

    //=========== Filtered Facilitator List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Facilitator} backed by the internal list of
     * {@code versionedModManager}
     */
    @Override
    public ObservableList<Facilitator> getFilteredFacilitatorList() {
        return filteredFacilitators;
    }

    @Override
    public void updateFilteredFacilitatorList(Predicate<Facilitator> predicate) {
        requireNonNull(predicate);
        filteredFacilitators.setPredicate(PREDICATE_SHOW_ALL_FACILITATORS);
        filteredFacilitators.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Facilitator} backed by the internal list of
     * {@code versionedModManager}
     */
    @Override
    public ObservableList<Facilitator> getFacilitatorListForModule() {
        return facilitatorsForModule;
    }

    @Override
    public void updateFacilitatorListForModule(Predicate<Facilitator> predicate) {
        requireNonNull(predicate);
        facilitatorsForModule.setPredicate(predicate);
    }

    //=========== Lesson =========================================================================================
    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return modManager.hasLesson(lesson);
    }

    @Override
    public void addLesson(Lesson lesson) {
        requireNonNull(lesson);
        modManager.addLesson(lesson);
    }

    @Override
    public void setLesson(Lesson target, Lesson edited) {
        requireAllNonNull(target, edited);
        modManager.setLesson(target, edited);
    }

    @Override
    public void removeLesson(Lesson lesson) {
        requireNonNull(lesson);
        modManager.removeLesson(lesson);
    }

    @Override
    public void removeLessonFromModule(ModuleCode moduleCode) {
        requireNonNull(moduleCode);
        modManager.removeLessonFromModule(moduleCode);
    }

    @Override
    public Lesson findNextLesson() {
        LessonList lessons = modManager.getLessons();
        return lessons.findNextLesson();
    }

    @Override
    public List<Lesson> findLessonByDay(DayOfWeek day) {
        return modManager.findLessonsByDay(day);
    }

    @Override
    public List<Lesson> getLessons() {
        return modManager.getLessonList();
    }

    @Override
    public ObservableList<Lesson> getLessonListForModule(ModuleCode moduleCode) {
        return modManager.getLessonListForModule(moduleCode);
    }

    @Override
    public void setModuleCodeInLessonList(ModuleCode target, ModuleCode editedModuleCode) {
        modManager.setModuleCodeInLessonList(target, editedModuleCode);
    }

    @Override
    public boolean isTimeSlotFree(Lesson lesson, Optional<Lesson> lessonToExclude) {
        return modManager.isTimeSlotFree(lesson, lessonToExclude);
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
        return modManager.equals(other.modManager)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules)
                && filteredFacilitators.equals(other.filteredFacilitators)
                && facilitatorsForModule.equals(other.facilitatorsForModule);
    }

    //=========== Task ================================================================================

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return modManager.hasTask(task);
    }

    @Override
    public void deleteTask(Task task) {
        modManager.removeTask(task);
    }

    @Override
    public void addTask(Task task) {
        modManager.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        modManager.setTask(target, editedTask);
    }

    @Override
    public void deleteTasksWithModuleCode(ModuleCode moduleCode) {
        requireAllNonNull(moduleCode);

        modManager.removeTasksWithModuleCode(moduleCode);
    }

    @Override
    public void setModuleCodeInTaskList(ModuleCode target, ModuleCode editedModuleCode) {
        modManager.setModuleCodeInTaskList(target, editedModuleCode);
    }

    //=========== Filtered Task List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedModManager}
     */
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Task} backed by the internal list of
     * {@code versionedModManager}
     */
    @Override
    public ObservableList<Task> getTaskListForModule() {
        return tasksForModule;
    }

    @Override
    public void updateTaskListForModule(Predicate<Task> predicate) {
        requireNonNull(predicate);
        tasksForModule.setPredicate(predicate);
    }

    //=========== Calendar =================================================================================

    @Override
    public void updateCalendar(Calendar calendar) {
        requireNonNull(calendar);
        this.calendar = calendar;
    }

    @Override
    public Calendar getCalendar() {
        return calendar;
    }

    //=========== DoableActionList ==========================================================================

    @Override
    public void addAction(DoableAction<?> action) {
        actionList.addAction(action);
    }

    @Override
    public boolean canUndo() {
        return actionList.canUndo();
    }

    @Override
    public boolean canRedo() {
        return actionList.canRedo();
    }

    @Override
    public DoableAction<?> undo() {
        return actionList.undo(this);
    }

    @Override
    public DoableAction<?> redo() {
        return actionList.redo(this);
    }
}
