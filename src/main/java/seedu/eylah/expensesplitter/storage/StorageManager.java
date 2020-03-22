package seedu.eylah.expensesplitter.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyUserPrefs;
import seedu.eylah.expensesplitter.model.UserPrefs;


/**
 * Manages storage of PersonAmountBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(seedu.eylah.addressbook.storage.StorageManager.class);
    private PersonAmountStorage personAmountStorage;
    private UserPrefsStorage userPrefsStorage;

    public StorageManager(PersonAmountStorage personAmountStorage, UserPrefsStorage userPrefsStorage) {

        super();
        this.personAmountStorage = personAmountStorage;
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


    // === PersonAmount Storage

    @Override
    public Path getPersonAmountBookFilePath() {
        return personAmountStorage.getPersonAmountBookFilePath();
    }

    @Override
    public Optional<ReadOnlyPersonAmountBook> readPersonAmountBook() throws DataConversionException, IOException {
        return readPersonAmountBook(personAmountStorage.getPersonAmountBookFilePath());
    }

    @Override
    public Optional<ReadOnlyPersonAmountBook> readPersonAmountBook(Path filePath) throws DataConversionException,
        IOException {

        logger.fine("Attempting to read data from file: " + filePath);
        return personAmountStorage.readPersonAmountBook(filePath);
    }

    @Override
    public void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook) throws IOException {
        savePersonAmountBook(personAmountBook, personAmountStorage.getPersonAmountBookFilePath());
    }

    @Override
    public void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        personAmountStorage.savePersonAmountBook(personAmountBook, filePath);
    }



}
