package nasa.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import nasa.commons.core.GuiSettings;
import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.activity.Deadline;
import nasa.model.activity.Event;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.SortMethod;
import nasa.model.module.UniqueModuleList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} for modules that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /** {@code Predicate} for activities that always evaluate to true */
    Predicate<Activity> PREDICATE_SHOW_ALL_ACTIVITIES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     * @param userPrefs ReadOnlyUserPrefs
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     * @return ReadOnlyUserPrefs
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     * @return GuiSettings
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     * @param guiSettings GuiSettings
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     * @return Path
     */
    Path getNasaBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     * @param nasaBookFilePath Path
     */
    void setNasaBookFilePath(Path nasaBookFilePath);

    /**
     * Replaces address book data with the data in {@code nasaBook}.
     * @param nasaBook ReadOnlyNasaBook
     */
    void setNasaBook(ReadOnlyNasaBook nasaBook);

    /** Returns the NasaBook
     * @return ReadOnlyNasaBook
     */
    ReadOnlyNasaBook getNasaBook();

    /** Returns the HistoryBook
     * @return ReadOnlyHistory
     */
    ReadOnlyHistory getHistoryBook();

    /** Returns the UiHistoryBook
     * @return ReadOnlyHistory
     */
    ReadOnlyHistory getUiHistoryBook();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     * @param moduleCode ModuleCode
     * @return boolean
     */
    boolean hasModule(ModuleCode moduleCode);

    /**
     * Getter method for existing module in model's {@code UniqueModuleList} by moduleCode
     * @param moduleCode ModuleCode
     * @return module with module code {@code moduleCode}
     */
    Module getModule(ModuleCode moduleCode);

    /**
     * Deletes the given module.
     * The module must exist in the nasa book.
     * @param target ModuleCode
     */
    void deleteModule(ModuleCode target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the nasa book.
     * @param module Module
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the nasa book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     * @param target Module code of the module that exists in model's nasaBook
     * @param editedModule newly edited Module
     */
    void setModule(ModuleCode target, Module editedModule);

    void addDeadline(ModuleCode moduleCode, Deadline deadline);

    void addEvent(ModuleCode moduleCode, Event event);

    void removeEvent(ModuleCode moduleCode, Event event);

    void removeDeadline(ModuleCode moduleCode, Deadline deadline);

    void setDeadline(ModuleCode moduleCode, Deadline target, Deadline editedDeadline);

    void setEvent(ModuleCode moduleCode, Event target, Event editedEvent);

    ObservableList<Module> getFilteredModuleList();

    void updateFilteredModuleList(Predicate<Module> predicate);

    ObservableList<Deadline> getFilteredDeadlineList(ModuleCode moduleCode);

    void sortActivityList(SortMethod sortMethod);

    ObservableList<Event> getFilteredEventList(ModuleCode moduleCode);

    void updateFilteredActivityList(Predicate<Activity> predicate);

    void updateSchedule();

    String quote();

    String currentUiLocation();

    void updateHistory(String type);

    void updateHistory(List<String> input, String type);

    void undoHistory();

    boolean redoHistory();

    public HistoryManager<UniqueModuleList> getHistoryManager();

    boolean setDeadlineSchedule(ModuleCode module, Index index, Index schedule);

    boolean setEventSchedule(ModuleCode module, Index index, Index schedule);

    boolean hasActivity(ModuleCode module, Activity activity);
}
