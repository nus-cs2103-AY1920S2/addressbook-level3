package seedu.zerotoone.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.model.session.ReadOnlySessionList;
import seedu.zerotoone.model.userprefs.ReadOnlyUserPrefs;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;
import seedu.zerotoone.storage.exercise.ExerciseListStorage;
import seedu.zerotoone.storage.schedule.ScheduleListStorage;
import seedu.zerotoone.storage.session.SessionListStorage;
import seedu.zerotoone.storage.userprefs.UserPrefsStorage;
import seedu.zerotoone.storage.workout.WorkoutListStorage;

/**
 * API of the Storage component
 */
public interface Storage extends UserPrefsStorage, ExerciseListStorage, WorkoutListStorage, ScheduleListStorage,
        SessionListStorage {
    // -----------------------------------------------------------------------------------------
    // Common - User Preferences
    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    // -----------------------------------------------------------------------------------------
    // Exercise List
    @Override
    Path getExerciseListFilePath();

    @Override
    Optional<ReadOnlyExerciseList> readExerciseList() throws DataConversionException, IOException;

    @Override
    void saveExerciseList(ReadOnlyExerciseList exerciseList) throws IOException;

    // -----------------------------------------------------------------------------------------
    // Workout List
    @Override
    Path getWorkoutListFilePath();

    @Override
    Optional<ReadOnlyWorkoutList> readWorkoutList() throws DataConversionException, IOException;

    @Override
    void saveWorkoutList(ReadOnlyWorkoutList workoutList) throws IOException;

    // -----------------------------------------------------------------------------------------
    // Schedule List
    @Override
    Path getScheduleListFilePath();

    @Override
    Optional<ScheduleList> readScheduleList() throws DataConversionException, IOException;

    @Override
    void saveScheduleList(ScheduleList scheduleList) throws IOException;

    // -----------------------------------------------------------------------------------------
    // Session List
    @Override
    Path getSessionListFilePath();

    @Override
    Optional<ReadOnlySessionList> readSessionList() throws DataConversionException, IOException;

    @Override
    void saveSessionList(ReadOnlySessionList sessionList) throws IOException;

}
