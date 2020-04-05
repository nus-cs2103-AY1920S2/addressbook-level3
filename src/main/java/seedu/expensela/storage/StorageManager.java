package seedu.expensela.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.expensela.commons.core.LogsCenter;
import seedu.expensela.commons.exceptions.DataConversionException;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.ReadOnlyGlobalData;
import seedu.expensela.model.ReadOnlyUserPrefs;
import seedu.expensela.model.UserPrefs;

/**
 * Manages storage of ExpenseLa data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ExpenseLaStorage expenseLaStorage;
    private UserPrefsStorage userPrefsStorage;
    private GlobalDataStorage globalDataStorage;


    public StorageManager(ExpenseLaStorage expenseLaStorage, UserPrefsStorage userPrefsStorage,
                          GlobalDataStorage globalDataStorage) {
        super();
        this.expenseLaStorage = expenseLaStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.globalDataStorage = globalDataStorage;
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

    // ================ GlobalData methods ==============================

    @Override
    public Path getGlobalDataFilePath() {
        return globalDataStorage.getGlobalDataFilePath();
    }

    @Override
    public Optional<GlobalData> readGlobalData() throws DataConversionException, IOException {
        return globalDataStorage.readGlobalData();
    }

    @Override
    public Optional<GlobalData> readGlobalData(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return globalDataStorage.readGlobalData(filePath);
    }

    @Override
    public void saveGlobalData(ReadOnlyGlobalData globalData) throws IOException {
        globalDataStorage.saveGlobalData(globalData);
    }

    @Override
    public void saveGlobalData(ReadOnlyGlobalData globalData, Path filePath) throws IOException {

    }


    // ================ ExpenseLa methods ==============================

    @Override
    public Path getExpenseLaFilePath() {
        return expenseLaStorage.getExpenseLaFilePath();
    }

    @Override
    public Optional<ReadOnlyExpenseLa> readExpenseLa() throws DataConversionException, IOException {
        return readExpenseLa(expenseLaStorage.getExpenseLaFilePath());
    }

    @Override
    public Optional<ReadOnlyExpenseLa> readExpenseLa(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return expenseLaStorage.readExpenseLa(filePath);
    }

    @Override
    public void saveExpenseLa(ReadOnlyExpenseLa expenseLa) throws IOException {
        saveExpenseLa(expenseLa, expenseLaStorage.getExpenseLaFilePath());
    }

    @Override
    public void saveExpenseLa(ReadOnlyExpenseLa expenseLa, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        expenseLaStorage.saveExpenseLa(expenseLa, filePath);
    }

}
