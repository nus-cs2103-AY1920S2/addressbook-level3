package nasa.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import nasa.commons.core.LogsCenter;
import nasa.commons.exceptions.DataConversionException;
import nasa.model.ReadOnlyHistory;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.ReadOnlyUserPrefs;
import nasa.model.UserPrefs;
import nasa.model.module.UniqueModuleList;

/**
 * Manages storage of NASA data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private NasaBookStorage nasaBookStorage;
    private UserPrefsStorage userPrefsStorage;



    public StorageManager(NasaBookStorage nasaBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.nasaBookStorage = nasaBookStorage;
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


    // ================ NASA methods ==============================

    @Override
    public Path getNasaBookFilePath() {
        return nasaBookStorage.getNasaBookFilePath();
    }

    @Override
    public Path getHistoryBookFilePath() {
        return nasaBookStorage.getHistoryBookFilePath();
    }

    @Override
    public Path getUiHistoryBookFilePath() {
        return nasaBookStorage.getUiHistoryBookFilePath();
    }

    @Override
    public Optional<ReadOnlyNasaBook> readNasaBook() throws DataConversionException, IOException {
        return readNasaBook(nasaBookStorage.getNasaBookFilePath());
    }

    @Override
    public Optional<ReadOnlyNasaBook> readNasaBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return nasaBookStorage.readNasaBook(filePath);
    }

    @Override
    public Optional<ReadOnlyHistory> readHistoryBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return nasaBookStorage.readHistoryBook(filePath);
    }

    @Override
    public Optional<ReadOnlyHistory> readHistoryBook() throws DataConversionException, IOException {
        return readHistoryBook(nasaBookStorage.getHistoryBookFilePath());
    }

    @Override
    public Optional<ReadOnlyHistory> readUiHistoryBook() throws DataConversionException, IOException {
        return readUiHistoryBook(nasaBookStorage.getUiHistoryBookFilePath());
    }

    @Override
    public Optional<ReadOnlyHistory> readUiHistoryBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return nasaBookStorage.readUiHistoryBook(filePath);
    }

    @Override
    public void saveNasaBook(ReadOnlyNasaBook nasaBook) throws IOException {
        saveNasaBook(nasaBook, nasaBookStorage.getNasaBookFilePath());
    }

    @Override
    public void saveNasaBook(ReadOnlyNasaBook nasaBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        nasaBookStorage.saveNasaBook(nasaBook, filePath);
    }

    @Override
    public void saveUltimate(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                             ReadOnlyHistory<String> uiHistoryBook)
            throws IOException {
        saveUltimate(nasaBook, historyBook, uiHistoryBook, nasaBookStorage.getNasaBookFilePath(),
                nasaBookStorage.getHistoryBookFilePath(), nasaBookStorage.getUiHistoryBookFilePath());
    }

    @Override
    public void saveUltimate(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                             ReadOnlyHistory<String> uiHistoryBook, Path filePathOne,
                             Path filePathTwo, Path filePathThree) throws IOException {
        logger.fine("Attempting to write to data file: " + filePathOne + filePathTwo);
        nasaBookStorage.saveUltimate(nasaBook, historyBook, uiHistoryBook, filePathOne, filePathTwo, filePathThree);
    }

}
