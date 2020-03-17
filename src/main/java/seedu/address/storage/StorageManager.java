package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.readOnlyExerciseList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ExerciseList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExerciseListStorage exerciseListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ExerciseListStorage exerciseListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.exerciseListStorage = exerciseListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

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


    // ================ ExerciseList methods ==============================

    @Override
    public Path getExerciseListFilePath() {
        return exerciseListStorage.getExerciseListFilePath();
    }

    @Override
    public Optional<readOnlyExerciseList> readExerciseList() throws DataConversionException, IOException {
        return readExerciseList(exerciseListStorage.getExerciseListFilePath());
    }

    @Override
    public Optional<readOnlyExerciseList> readExerciseList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return exerciseListStorage.readExerciseList(filePath);
    }

    @Override
    public void saveExerciseList(readOnlyExerciseList exerciseList) throws IOException {
        saveExerciseList(exerciseList, exerciseListStorage.getExerciseListFilePath());
    }

    @Override
    public void saveExerciseList(readOnlyExerciseList exerciseList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        exerciseListStorage.saveExerciseList(exerciseList, filePath);
    }

}
