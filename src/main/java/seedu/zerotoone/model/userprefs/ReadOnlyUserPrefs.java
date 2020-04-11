package seedu.zerotoone.model.userprefs;

import java.nio.file.Path;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {
    Path getExerciseListFilePath();
    Path getWorkoutListFilePath();
    Path getScheduleListFilePath();
    Path getLogListFilePath();
}
