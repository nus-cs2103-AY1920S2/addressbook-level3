package nasa.model;

import java.nio.file.Path;

import nasa.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefsNasa {

    GuiSettings getGuiSettings();

    Path getNasaBookFilePath();

}
