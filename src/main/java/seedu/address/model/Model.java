package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.facilitator.Facilitator;
import seedu.address.model.module.Module;
import seedu.address.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Facilitator> PREDICATE_SHOW_ALL_FACILITATORS = unused -> true;
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS= unused -> true;

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

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

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

    /** Returns an unmodifiable view of the filtered facilitator list */
    ObservableList<Facilitator> getFilteredFacilitatorList();

    /**
     * Returns true if a module with the same identity as {@code module} exists in Mod Manager.
     */
    boolean hasTask(Task module);

    /**
     * Deletes the given module.
     * The module must exist in Mod Manager.
     */
    void deleteTask(Task target);

    /**
     * Adds the given module.
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
     * Updates the filter of the filtered facilitator list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFacilitatorList(Predicate<Facilitator> predicate);

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);
}
