package seedu.zerotoone.model.userprefs;

import java.nio.file.Path;

import seedu.zerotoone.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getExerciseListFilePath();

}
