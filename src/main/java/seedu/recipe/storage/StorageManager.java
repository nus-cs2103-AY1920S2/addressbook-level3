package seedu.recipe.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyCookedRecord;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.ReadOnlyUserPrefs;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.cooked.CookedRecord;

/**
 * Manages storage of RecipeBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private RecipeBookStorage recipeBookStorage;
    private CookedRecordStorage cookedRecordStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(RecipeBookStorage recipeBookStorage, CookedRecordStorage cookedRecordStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.recipeBookStorage = recipeBookStorage;
        this.cookedRecordStorage = cookedRecordStorage;
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


    // ================ RecipeBook methods ==============================

    @Override
    public Path getRecipeBookFilePath() {
        return recipeBookStorage.getRecipeBookFilePath();
    }

    @Override
    public Optional<ReadOnlyRecipeBook> readRecipeBook() throws DataConversionException, IOException {
        return readRecipeBook(recipeBookStorage.getRecipeBookFilePath());
    }

    @Override
    public Optional<ReadOnlyRecipeBook> readRecipeBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return recipeBookStorage.readRecipeBook(filePath);
    }

    @Override
    public void saveRecipeBook(ReadOnlyRecipeBook recipeBook) throws IOException {
        saveRecipeBook(recipeBook, recipeBookStorage.getRecipeBookFilePath());
    }

    @Override
    public void saveRecipeBook(ReadOnlyRecipeBook recipeBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        recipeBookStorage.saveRecipeBook(recipeBook, filePath);
    }

    // ================ CookedRecord methods ==============================

    @Override
    public Path getCookedRecordFilePath() {
        return cookedRecordStorage.getCookedRecordFilePath();
    }

    @Override
    public Optional<ReadOnlyCookedRecord> readCookedRecord() throws DataConversionException, IOException {
        return readCookedRecord(cookedRecordStorage.getCookedRecordFilePath());
    }

    @Override
    public Optional<ReadOnlyCookedRecord> readCookedRecord(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return cookedRecordStorage.readCookedRecord(filePath);
    }

    @Override
    public void saveCookedRecord(ReadOnlyCookedRecord cookedRecord) throws IOException {
        saveCookedRecord(cookedRecord, cookedRecordStorage.getCookedRecordFilePath());
    }

    @Override
    public void saveCookedRecord(ReadOnlyCookedRecord cookedRecord, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        cookedRecordStorage.saveCookedRecord(cookedRecord, filePath);
    }

}
