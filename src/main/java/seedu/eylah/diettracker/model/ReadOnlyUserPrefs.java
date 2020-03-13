package seedu.eylah.diettracker.model;

import seedu.eylah.commons.core.GuiSettings;

import java.nio.file.Path;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getFoodBookFilePath();

}
