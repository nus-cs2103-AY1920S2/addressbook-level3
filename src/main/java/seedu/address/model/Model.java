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

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Facilitator> PREDICATE_SHOW_ALL_FACILITATORS = unused -> true;
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;
    Predicate<Facilitator> PREDICATE_SHOW_NO_FACILITATORS = unused -> false;

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
    void updateModule(Module module);

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
     * Returns true if a task with the same identity as {@code module} exists in Mod Manager.
     */
    boolean hasTask(Task module);

    /**
     * Deletes the given task.
     * The module must exist in Mod Manager.
     */
    void deleteTask(Task target);

    /**
     * Adds the given task.
     * {@code module} must not already exist in Mod Manager.
     */
    void addTask(Task module);

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

    boolean hasLesson(Lesson lesson);

    void addLesson(Lesson lesson);

    void setLesson(Lesson target, Lesson edited);

    void removeLesson(Lesson lesson);

    Lesson findNextLesson();

    List<Lesson> findLessonByDay(DayOfWeek day);

    List<Lesson> getLessons();

    ObservableList<Lesson> getLessonListForModule(ModuleCode moduleCode);

    /** Updates the calendar in the model to the given {@code calendar}. */
    void updateCalendar(Calendar calendar);

    Calendar getCalendar();
}
