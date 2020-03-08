package fithelper.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import fithelper.commons.core.GuiSettings;
import fithelper.model.entry.Entry;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Entry> PREDICATE_SHOW_ALL_ENTRIES = unused -> true;

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
     * Returns the user prefs' FitHelper file path.
     */
    Path getFitHelperFilePath();

    /**
     * Sets the user prefs' FitHelper file path.
     */
    void setFitHelperFilePath(Path fitHelperFilePath);

    /**
     * Replaces FitHelper data with the data in {@code fitHelper}.
     */
    void setFitHelper(ReadOnlyFitHelper fitHelper);

    /** Returns the FitHelper */
    ReadOnlyFitHelper getFitHelper();

    /**
     * Returns true if an Entry with the same identity as {@code entry} exists in the FitHelper.
     */
    boolean hasEntry(Entry entry);

    /**
     * Deletes the given entry.
     * The entry must exist in the log book.
     */
    void deleteEntry(Entry target);

    /**
     * Adds the given Entry.
     * {@code entry} must not already exist in the log book.
     */
    void addEntry(Entry entry);

    /**
     * Replaces the given entry {@code target} with {@code editedEntry}.
     * {@code target} must exist in the log book.
     * The entry identity of {@code editedEntry} must not be the same as another existing entry in the log book.
     */
    void setEntry(Entry target, Entry editedEntry);

    /** Returns an unmodifiable view of the filtered food entry list */
    ObservableList<Entry> getFilteredFoodEntryList();

    /** Returns an unmodifiable view of the filtered sports entry list */
    ObservableList<Entry> getFilteredSportsEntryList();

    /**
     * Updates the filter of the filtered entry list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEntryList(Predicate<Entry> predicate);
}

