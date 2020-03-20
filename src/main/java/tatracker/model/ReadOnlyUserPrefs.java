package tatracker.model;

import java.nio.file.Path;

import tatracker.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getTaTrackerFilePath();

}
