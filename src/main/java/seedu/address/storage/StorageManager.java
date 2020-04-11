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
    public Path getAddressBookFilePath() {
        return resumeBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyResumeBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(resumeBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyResumeBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return resumeBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyResumeBook addressBook) throws IOException {
        saveAddressBook(addressBook, resumeBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyResumeBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        resumeBookStorage.saveAddressBook(addressBook, filePath);
    }

}
