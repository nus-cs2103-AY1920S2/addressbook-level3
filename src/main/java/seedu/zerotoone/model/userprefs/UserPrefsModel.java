package seedu.zerotoone.model.userprefs;

/**
 * Model for User Prefs.
 */
public interface UserPrefsModel {
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();
}
