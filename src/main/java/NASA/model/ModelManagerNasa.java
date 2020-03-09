package NASA.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import NASA.commons.core.GuiSettings;
import NASA.commons.core.index.Index;
import NASA.commons.core.LogsCenter;
import NASA.model.module.Module;
import NASA.model.activity.Activity;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManagerNasa implements ModelNasa {
    private static final Logger logger = LogsCenter.getLogger(ModelManagerNasa.class);

    private final NasaBook nasaBook;
    private final UserPrefsNasa userPrefs;
    private final FilteredList<Module> filteredModules;

    /**
     * Initializes a ModelManager with the given NasaBook and userPrefs.
     */
    public ModelManagerNasa(ReadOnlyNasaBook NasaBook, ReadOnlyUserPrefsNasa userPrefs) {
        super();
        requireAllNonNull(NasaBook, userPrefs);

        logger.fine("Initializing with address book: " + NasaBook + " and user prefs " + userPrefs);

        this.nasaBook = new NasaBook(NasaBook);
        this.userPrefs = new UserPrefsNasa(userPrefs);
        filteredModules = new FilteredList<>(this.nasaBook.getModuleList());
    }

    public ModelManagerNasa() {
        this(new NasaBook(), new UserPrefsNasa());
    }

    //=========== UserPrefsNasa ==================================================================================

    @Override
    public void setUserPrefsNasa(ReadOnlyUserPrefsNasa userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefsNasa getUserPrefsNasa() {
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
    public void setNasaBook(ReadOnlyNasaBook NasaBook) {
        this.nasaBook.resetData(NasaBook);
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
    public void deleteModule(Module target) {
        nasaBook.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        nasaBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        nasaBook.setModule(target, editedModule);
    }

    @Override
    public void addActivity(Module target, Activity activity) {
        nasaBook.addActivity(target, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setActivityByIndex(Module module, Index index, Activity activity) {
        nasaBook.setActivityByIndex(module, index, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void editActivityByIndex(Module module, Index index, Objects... args) {
        nasaBook.editActivityByIndex(module, index, args);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivity(Module target, Activity activity) {
        nasaBook.removeActivity(target, activity);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public boolean hasActivity(Module target, Activity activity) {
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
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManagerNasa)) {
            return false;
        }

        // state check
        ModelManagerNasa other = (ModelManagerNasa) obj;
        return nasaBook.equals(other.nasaBook)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules);
    }

}
