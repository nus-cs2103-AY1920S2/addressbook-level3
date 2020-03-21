package seedu.zerotoone.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.userprefs.ReadOnlyUserPrefs;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.storage.exercise.ExerciseListStorage;
import seedu.zerotoone.storage.userprefs.UserPrefsStorage;

/**
 * Manages storage of ExerciseList data in local storage.
 */
public class StorageManager implements Storage {
    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private ExerciseListStorage exerciseListStorage;

    public StorageManager(UserPrefsStorage userPrefsStorage, ExerciseListStorage exerciseListStorage) {
        super();
        this.userPrefsStorage = userPrefsStorage;
        this.exerciseListStorage = exerciseListStorage;
    }

    // -----------------------------------------------------------------------------------------
    // Common - User Preferences
    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    // -----------------------------------------------------------------------------------------
    // Exercise List
    @Override
    public Path getExerciseListFilePath() {
        return exerciseListStorage.getExerciseListFilePath();
    }

    @Override
    public Optional<ReadOnlyExerciseList> readExerciseList() throws DataConversionException, IOException {
        return readExerciseList(exerciseListStorage.getExerciseListFilePath());
    }

    @Override
    public Optional<ReadOnlyExerciseList> readExerciseList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return exerciseListStorage.readExerciseList(filePath);
    }

    @Override
    public void saveExerciseList(ReadOnlyExerciseList exerciseList) throws IOException {
        saveExerciseList(exerciseList, exerciseListStorage.getExerciseListFilePath());
    }

    @Override
    public void saveExerciseList(ReadOnlyExerciseList exerciseList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        exerciseListStorage.saveExerciseList(exerciseList, filePath);
    }
}
