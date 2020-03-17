package seedu.zerotoone.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.ReadOnlyExerciseList;
import seedu.zerotoone.model.ReadOnlyUserPrefs;
import seedu.zerotoone.model.UserPrefs;

/**
 * Manages storage of ExerciseList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExerciseListStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ExerciseListStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
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
        return addressBookStorage.getExerciseListFilePath();
    }

    @Override
    public Optional<ReadOnlyExerciseList> readExerciseList() throws DataConversionException, IOException {
        return readExerciseList(addressBookStorage.getExerciseListFilePath());
    }

    @Override
    public Optional<ReadOnlyExerciseList> readExerciseList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readExerciseList(filePath);
    }

    @Override
    public void saveExerciseList(ReadOnlyExerciseList addressBook) throws IOException {
        saveExerciseList(addressBook, addressBookStorage.getExerciseListFilePath());
    }

    @Override
    public void saveExerciseList(ReadOnlyExerciseList addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveExerciseList(addressBook, filePath);
    }

}
