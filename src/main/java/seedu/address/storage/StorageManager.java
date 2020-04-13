package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyResumeBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ResumeBookStorage resumeBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ResumeBookStorage resumeBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.resumeBookStorage = resumeBookStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getResumeBookFilePath() {
        return resumeBookStorage.getResumeBookFilePath();
    }

    @Override
    public Optional<ReadOnlyResumeBook> readResumeBook() throws DataConversionException, IOException {
        return readResumeBook(resumeBookStorage.getResumeBookFilePath());
    }

    @Override
    public Optional<ReadOnlyResumeBook> readResumeBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return resumeBookStorage.readResumeBook(filePath);
    }

    @Override
    public void saveResumeBook(ReadOnlyResumeBook addressBook) throws IOException {
        saveResumeBook(addressBook, resumeBookStorage.getResumeBookFilePath());
    }

    @Override
    public void saveResumeBook(ReadOnlyResumeBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        resumeBookStorage.saveResumeBook(addressBook, filePath);
    }

}
