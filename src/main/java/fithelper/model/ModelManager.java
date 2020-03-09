package fithelper.model;

import static fithelper.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import fithelper.commons.core.GuiSettings;
import fithelper.commons.core.LogsCenter;
import fithelper.model.entry.Entry;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the FitHelper data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FitHelper fitHelper;
    private final UserPrefs userPrefs;
    private final FilteredList<Entry> filteredFoodEntries;
    private final FilteredList<Entry> filteredSportsEntries;

    /**
     * Initializes a ModelManager with the given fitHelper and userPrefs.
     */
    public ModelManager(ReadOnlyFitHelper fitHelper, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(fitHelper, userPrefs);

        logger.fine("Initializing with FitHelper: " + fitHelper + " and user prefs " + userPrefs);

        this.fitHelper = new FitHelper(fitHelper);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFoodEntries = new FilteredList<>(this.fitHelper.getFoodList());
        filteredSportsEntries = new FilteredList<>(this.fitHelper.getSportsList());
    }

    public ModelManager() {
        this(new FitHelper(), new UserPrefs());
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
    public Path getFitHelperFilePath() {
        return userPrefs.getFitHelperFilePath();
    }

    @Override
    public void setFitHelperFilePath(Path fitHelperFilePath) {
        requireNonNull(fitHelperFilePath);
        userPrefs.setFitHelperFilePath(fitHelperFilePath);
    }

    //=========== FitHelper ================================================================================

    @Override
    public void setFitHelper(ReadOnlyFitHelper fitHelper) {
        this.fitHelper.resetData(fitHelper);
    }

    @Override
    public ReadOnlyFitHelper getFitHelper() {
        return fitHelper;
    }

    @Override
    public boolean hasEntry(Entry entry) {
        requireNonNull(entry);
        return fitHelper.hasEntry(entry);
    }

    /**
     * Deletes the given entry.
     * The entry must exist in the log book.
     * @param target
     */
    @Override
    public void deleteEntry(Entry target) {
        fitHelper.removeEntry(target);
    }

    @Override
    public void addEntry(Entry entry) {
        fitHelper.addEntry(entry);
        updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
    }

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the log book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the log book.
     *
     * @param target
     * @param editedEntry
     */
    @Override
    public void setEntry(Entry target, Entry editedEntry) {
        requireAllNonNull(target, editedEntry);
        fitHelper.setEntry(target, editedEntry);
    }

    //=========== Filtered Entry List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the food list of {@code Entry} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Entry> getFilteredFoodEntryList() {
        return filteredFoodEntries;
    }

    /**
     * Returns an unmodifiable view of the food list of {@code Entry} backed by the internal list of
     * {@code versionedFitHelper}
     */
    @Override
    public ObservableList<Entry> getFilteredSportsEntryList() {
        return filteredSportsEntries;
    }

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     *
     * @param predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredEntryList(Predicate<Entry> predicate) {
        requireNonNull(predicate);
        filteredFoodEntries.setPredicate(predicate);
        filteredSportsEntries.setPredicate(predicate);
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
        return fitHelper.equals(other.fitHelper)
                && userPrefs.equals(other.userPrefs)
                && filteredFoodEntries.equals(other.filteredFoodEntries)
                && filteredSportsEntries.equals(other.filteredSportsEntries);
    }

}
