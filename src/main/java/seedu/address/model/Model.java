package seedu.address.model;

import java.nio.file.Path;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.calendar.Calendar;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.task.Task;
import seedu.address.model.util.action.DoableAction;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Facilitator> PREDICATE_SHOW_ALL_FACILITATORS = unused -> true;
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;
    Predicate<Facilitator> PREDICATE_SHOW_NO_FACILITATORS = unused -> false;
    Predicate<Task> PREDICATE_SHOW_NO_TASKS = unused -> false;

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
    Path getModManagerFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setModManagerFilePath(Path modManagerFilePath);

    /**
     * Replaces address book data with the data in {@code modManager}.
     */
    void setModManager(ReadOnlyModManager modManager);

    /** Returns the ModManager */
    ReadOnlyModManager getModManager();

    /**
     * Returns true if a module with the same module code as {@code moduleCode} exists in Mod Manager.
     */
    boolean hasModuleCode(String moduleCode);

    /**
     * Returns true if a module with the same identity as {@code module} exists in Mod Manager.
     */
    boolean hasModule(Module module);

    /**
     * Deletes the given module.
     * The module must exist in Mod Manager.
     */
    void deleteModule(Module target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in Mod Manager.
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in Mod Manager.
     * The module identity of {@code editedModule} must not be the same as another existing module
     * in Mod Manager.
     */
    void setModule(Module target, Module editedModule);

    /** Returns an unmodifiable view of the filtered module list. */
    ObservableList<Module> getFilteredModuleList();

    /** Returns the module to be viewed. */
    Optional<Module> getModule();

    /** Finds the module with the given {@code moduleCode}. */
    Optional<Module> findModule(ModuleCode moduleCode);

    /** Updates the module in the model to the given {@code module}. */
    void updateModule(Optional<Module> module);

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Returns true if a facilitator with the same identity as {@code facilitator} exists in Mod Manager.
     */
    boolean hasFacilitator(Facilitator facilitator);

    /**
     * Deletes the given facilitator.
     * The facilitator must exist in Mod Manager.
     */
    void deleteFacilitator(Facilitator target);

    /**
     * Adds the given facilitator.
     * {@code facilitator} must not already exist in Mod Manager.
     */
    void addFacilitator(Facilitator facilitator);

    /**
     * Replaces the given facilitator {@code target} with {@code editedFacilitator}.
     * {@code target} must exist in Mod Manager.
     * The facilitator identity of {@code editedFacilitator} must not be the same as another existing facilitator
     * in Mod Manager.
     */
    void setFacilitator(Facilitator target, Facilitator editedFacilitator);

    /**
     * Deletes the given module code from the facilitator list.
     * A module with the module code must exist in Mod Manager.
     */
    void deleteModuleCodeFromFacilitatorList(ModuleCode target);

    /**
     * Replaces the given module code {@code target} with {@code editedModuleCode} in the facilitator list.
     * The module code identity of {@code editedModuleCode} must not be the same as another existing module code
     * in Mod Manager.
     */
    void setModuleCodeInFacilitatorList(ModuleCode target, ModuleCode editedModuleCode);

    /** Returns an unmodifiable view of the filtered facilitator list. */
    ObservableList<Facilitator> getFilteredFacilitatorList();

    /**
     * Updates the filter of the filtered facilitator list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFacilitatorList(Predicate<Facilitator> predicate);

    /** Returns an unmodifiable view of the filtered facilitator list. */
    ObservableList<Facilitator> getFacilitatorListForModule();

    /**
     * Updates the filter of the filtered facilitator list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFacilitatorListForModule(Predicate<Facilitator> predicate);

    /**
     * Returns true if a task with the same identity as {@code task} exists in Mod Manager.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given task.
     * The module must exist in Mod Manager.
     */
    void deleteTask(Task target);

    /**
     * Adds the given task.
     * {@code module} must not already exist in Mod Manager.
     */
    void addTask(Task task);

    /**
     * Removes all tasks with the specified ModuleCode.
     * The Module associated with that code is guaranteed to exist before removal.
     */
    void deleteTasksWithModuleCode(ModuleCode target);

    /**
     * Replaces the given module code {@code target} with {@code editedModuleCode} in the task list.
     * The module code identity of {@code editedModuleCode} must not be the same as another existing module code
     * in Mod Manager.
     */
    void setModuleCodeInTaskList(ModuleCode target, ModuleCode editedModuleCode);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in Mod Manager.
     * The task identity of {@code editedTask} must not be the same as another existing task
     * in Mod Manager.
     */
    void setTask(Task target, Task editedTask);

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /** Returns an unmodifiable view of the filtered task list. */
    ObservableList<Task> getTaskListForModule();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateTaskListForModule(Predicate<Task> predicate);

    boolean hasLesson(Lesson lesson);

    void addLesson(Lesson lesson);

    void setLesson(Lesson target, Lesson edited);

    void removeLesson(Lesson lesson);

    void removeLessonFromModule(ModuleCode moduleCode);

    Lesson findNextLesson();

    List<Lesson> findLessonByDay(DayOfWeek day);

    List<Lesson> getLessons();

    ObservableList<Lesson> getLessonListForModule(ModuleCode moduleCode);

    boolean isTimeSlotFree(Lesson lesson, Optional<Lesson> lessonToExclude);

    /**
     * Replaces the given module code {@code target} with {@code editedModuleCode} in the lesson list.
     * The module code identity of {@code editedModuleCode} must not be the same as another existing module code
     * in Mod Manager.
     */
    void setModuleCodeInLessonList(ModuleCode target, ModuleCode editedModuleCode);

    /** Updates the calendar in the model to the given {@code calendar}. */
    void updateCalendar(Calendar calendar);

    Calendar getCalendar();

    void addAction(DoableAction<?> action);

    boolean canUndo();

    boolean canRedo();

    DoableAction<?> undo();

    DoableAction<?> redo();
}
