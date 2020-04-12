package seedu.eylah.expensesplitter.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.commons.storage.UserPrefsStorage;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;


/**
 * Manages storage of PersonAmountBook and Receipt data in local storage.
 */
public class SplitterStorageManager implements SplitterStorage {

    private static final Logger logger = LogsCenter.getLogger(SplitterStorageManager.class);
    private UserPrefsStorage userPrefsStorage;
    private PersonAmountStorage personAmountStorage;
    private ReceiptStorage receiptStorage;

    public SplitterStorageManager(PersonAmountStorage personAmountStorage, ReceiptStorage receiptStorage,
            UserPrefsStorage userPrefsStorage) {
        this.userPrefsStorage = userPrefsStorage;
        this.personAmountStorage = personAmountStorage;
        this.receiptStorage = receiptStorage;
    }

    // ================ PersonAmount Storage methods ==============================

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

    // ================ Receipt Storage methods ==============================

    @Override
    public Path getReceiptBookFilePath() {
        return receiptStorage.getReceiptBookFilePath();
    }

    @Override
    public Optional<ReadOnlyReceiptBook> readReceiptBook() throws DataConversionException, IOException {
        return readReceiptBook(receiptStorage.getReceiptBookFilePath());
    }

    @Override
    public Optional<ReadOnlyReceiptBook> readReceiptBook(Path filePath) throws DataConversionException,
            IOException {

        logger.fine("Attempting to read data from file: " + filePath);
        return receiptStorage.readReceiptBook(filePath);
    }

    @Override
    public void saveReceiptBook(ReadOnlyReceiptBook receiptBook) throws IOException {
        saveReceiptBook(receiptBook, receiptStorage.getReceiptBookFilePath());
    }

    @Override
    public void saveReceiptBook(ReadOnlyReceiptBook receiptBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        receiptStorage.saveReceiptBook(receiptBook, filePath);
    }


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
}
