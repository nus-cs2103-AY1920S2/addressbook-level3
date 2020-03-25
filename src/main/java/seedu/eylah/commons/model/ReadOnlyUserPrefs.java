package seedu.eylah.commons.model;

import java.nio.file.Path;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    Path getFoodBookFilePath();

    Path getPersonAmountFilePath();
}
