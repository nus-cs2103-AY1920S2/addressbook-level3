package seedu.eylah.diettracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.ReadOnlyUserPrefs;
import seedu.eylah.diettracker.model.UserPrefs;

/**
 * Manages storage of FoodBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FoodBookStorage foodBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(FoodBookStorage foodBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.foodBookStorage = foodBookStorage;
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


    // ================ FoodBook methods ==============================

    @Override
    public Path getFoodBookFilePath() {
        return foodBookStorage.getFoodBookFilePath();
    }

    @Override
    public Optional<ReadOnlyFoodBook> readFoodBook() throws DataConversionException, IOException {
        return readFoodBook(foodBookStorage.getFoodBookFilePath());
    }

    @Override
    public Optional<ReadOnlyFoodBook> readFoodBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return foodBookStorage.readFoodBook(filePath);
    }

    @Override
    public void saveFoodBook(ReadOnlyFoodBook foodBook) throws IOException {
        saveFoodBook(foodBook, foodBookStorage.getFoodBookFilePath());
    }

    @Override
    public void saveFoodBook(ReadOnlyFoodBook foodBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        foodBookStorage.saveFoodBook(foodBook, filePath);
    }

}
