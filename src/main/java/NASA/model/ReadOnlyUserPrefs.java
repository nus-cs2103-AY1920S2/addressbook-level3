package NASA.model;

import java.nio.file.Path;

import NASA.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getNasaBookFilePath();


}
