package fithelper.model;

import java.nio.file.Path;

import fithelper.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getFitHelperFilePath();

    Path getUserProfilePath();

    Path getWeightRecordsPath();

}
