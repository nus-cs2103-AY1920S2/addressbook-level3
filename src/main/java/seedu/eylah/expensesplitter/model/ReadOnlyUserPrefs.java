package seedu.eylah.expensesplitter.model;

import java.nio.file.Path;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {
    Path getPersonAmountBookFilePath();
}
