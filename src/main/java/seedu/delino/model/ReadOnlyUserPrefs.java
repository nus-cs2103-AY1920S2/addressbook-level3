package seedu.delino.model;

import java.nio.file.Path;

import seedu.delino.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getOrderBookFilePath();

    Path getReturnOrderBookFilePath();

}
