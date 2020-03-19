package nasa.model;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import nasa.commons.core.GuiSettings;
import nasa.commons.core.LogsCenter;
import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final NasaBook nasaBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given NasaBook and userPrefs.
     */
    public ModelManager(ReadOnlyNasaBook nasaBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(nasaBook, userPrefs);

        logger.fine("Initializing with address book: " + nasaBook + " and user prefs " + userPrefs);

        this.nasaBook = new NasaBook(nasaBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModules = new FilteredList<>(this.nasaBook.getModuleList());
    }

    public ModelManager() {
        this(new NasaBook(), new UserPrefs());
    }

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
    public Path getNasaBookFilePath() {
        return userPrefs.getNasaBookFilePath();
    }

    @Override
    public void setNasaBookFilePath(Path nasaBookFilePath) {
        requireNonNull(nasaBookFilePath);
        userPrefs.setNasaBookFilePath(nasaBookFilePath);
    }

    //=========== NasaBook ================================================================================

    @Override
    public void setNasaBook(ReadOnlyNasaBook nasaBook) {
        this.nasaBook.resetData(nasaBook);
    }

    @Override
    public ReadOnlyNasaBook getNasaBook() {
        return nasaBook;
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return nasaBook.hasModule(module);
    }

    @Override
    public boolean hasModule(ModuleCode module) {
        requireNonNull(module);
        return nasaBook.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        nasaBook.removeModule(target);
    }

    @Override
    public void deleteModule(ModuleCode target) {
        nasaBook.removeModule(target);
    }

    @Override
    public void removeModuleByIndex(Index index) {
        nasaBook.removeModuleByIndex(index);
    }

    @Override
    public void addModule(Module module) {
        nasaBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addModule(ModuleCode moduleCode, ModuleName moduleName) {
        nasaBook.addModule(moduleCode, moduleName);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        nasaBook.setModule(target, editedModule);
    }

    @Override
    public void setModule(ModuleCode target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        nasaBook.setModule(target, editedModule);
    }

    @Override
    public void addActivity(Module target, Activity activity) {
        nasaBook.addActivity(target, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addActivity(ModuleCode target, Activity activity) {
        nasaBook.addActivity(target, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setActivityByIndex(Module module, Index index, Activity activity) {
        nasaBook.setActivityByIndex(module, index, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setActivityByIndex(ModuleCode module, Index index, Activity activity) {
        nasaBook.setActivityByIndex(module, index, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void editActivityByIndex(Module module, Index index, Object... args) {
        nasaBook.editActivityByIndex(module, index, args);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void editActivityByIndex(ModuleCode module, Index index, Object... args) {
        nasaBook.editActivityByIndex(module, index, args);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivity(Module target, Activity activity) {
        nasaBook.removeActivity(target, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivity(ModuleCode target, Activity activity) {
        nasaBook.removeActivity(target, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivityByIndex(Module target, Index index) {
        nasaBook.removeActivityByIndex(target, index);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivityByIndex(ModuleCode target, Index index) {
        nasaBook.removeActivityByIndex(target, index);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public boolean hasActivity(Module target, Activity activity) {
        requireNonNull(activity);
        return nasaBook.hasActivity(target, activity);
    }

    @Override
    public boolean hasActivity(ModuleCode target, Activity activity) {
        requireNonNull(activity);
        return nasaBook.hasActivity(target, activity);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedNasaBook}
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

    @Override
    public ObservableList<Activity> getFilteredActivityList(Index index) {
        Module module = filteredModules.get(index.getZeroBased());
        return module.getFilteredActivityList();
    }

    @Override
    public void updateFilteredActivityList(Index index, Predicate<Activity> predicate) {
        Module module = filteredModules.get(index.getZeroBased());
        module.updateFilteredActivityList(predicate);
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
        return nasaBook.equals(other.nasaBook)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules);
    }

}
