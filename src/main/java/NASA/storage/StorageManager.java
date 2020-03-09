package NASA.storage;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import com.google.gson.Gson;
import NASA.commons.core.LogsCenter;
import NASA.commons.exceptions.DataConversionException;
import NASA.model.NasaBook;
import NASA.model.ReadOnlyNasaBook;
import NASA.model.ReadOnlyUserPrefs;
import NASA.model.UserPrefs;
import NASA.storage.NasaBookStorage;
import NASA.storage.Storage;
import NASA.storage.UserPrefsStorage;

/**
 * Manages storage of NasaBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(NASA.storage.StorageManager.class);
    private NasaBookStorage NasaBookStorage;
    private UserPrefsStorage userPrefsStorage;



    public StorageManager(NasaBookStorage NasaBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.NasaBookStorage = NasaBookStorage;
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


    // ================ NasaBook methods ==============================

    @Override
    public Path getNasaBookFilePath() {
        return NasaBookStorage.getNasaBookFilePath();
    }

    @Override
    public Optional<ReadOnlyNasaBook> readNasaBook() throws DataConversionException, IOException {
        return readNasaBook(NasaBookStorage.getNasaBookFilePath());
    }

    @Override
    public Optional<ReadOnlyNasaBook> readNasaBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return NasaBookStorage.readNasaBook(filePath);
    }

    @Override
    public void saveNasaBook(ReadOnlyNasaBook NasaBook) throws IOException {
        saveNasaBook(NasaBook, NasaBookStorage.getNasaBookFilePath());
    }

    @Override
    public void saveNasaBook(ReadOnlyNasaBook NasaBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        NasaBookStorage.saveNasaBook(NasaBook, filePath);
    }

}
