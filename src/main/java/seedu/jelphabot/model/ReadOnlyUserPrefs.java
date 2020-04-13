package seedu.jelphabot.model;

import java.nio.file.Path;

import seedu.jelphabot.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getJelphaBotFilePath();

    Path getRemindersFilePath();

}
