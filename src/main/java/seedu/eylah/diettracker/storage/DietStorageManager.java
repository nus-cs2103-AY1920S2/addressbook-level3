package seedu.eylah.diettracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.storage.StorageManager;
import seedu.eylah.commons.storage.UserPrefsStorage;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;

/**
 * Manages storage of FoodBook data in local storage.
 */
public class DietStorageManager extends StorageManager implements DietStorage {

    private static final Logger logger = LogsCenter.getLogger(DietStorageManager.class);
    private FoodBookStorage foodBookStorage;

    public DietStorageManager(FoodBookStorage foodBookStorage, UserPrefsStorage userPrefsStorage) {
        super(userPrefsStorage);
        this.foodBookStorage = foodBookStorage;
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
