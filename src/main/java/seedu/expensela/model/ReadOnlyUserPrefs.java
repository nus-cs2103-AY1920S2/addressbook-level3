package seedu.expensela.model;

import java.nio.file.Path;

import seedu.expensela.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getExpenseLaFilePath();

    Path getGlobalDataFilePath();

}
