package cookbuddy.model;

import java.nio.file.Path;

import cookbuddy.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getDataFilePath();

    Path getImagesPath();

}
