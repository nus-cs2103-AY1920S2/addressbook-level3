package nasa.model;

import java.nio.file.Path;

import nasa.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getNasaBookFilePath();

    Path getHistoryBookFilePath();

    Path getUiHistoryBookFilePath();

    Path getCalendarExportPath();
}
