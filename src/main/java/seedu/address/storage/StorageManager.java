package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.ReadOnlyReturnOrderBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of OrderBook and ReturnOrderBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private OrderBookStorage orderBookStorage;
    private ReturnOrderBookStorage returnOrderBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(OrderBookStorage orderBookStorage, ReturnOrderBookStorage returnOrderBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.orderBookStorage = orderBookStorage;
        this.returnOrderBookStorage = returnOrderBookStorage;
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


    // ================ OrderBook methods ==============================

    @Override
    public Path getOrderBookFilePath() {
        return orderBookStorage.getOrderBookFilePath();
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException {
        return readOrderBook(orderBookStorage.getOrderBookFilePath());
    }

    @Override
    public Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return orderBookStorage.readOrderBook(filePath);
    }

    @Override
    public void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException {
        saveOrderBook(orderBook, orderBookStorage.getOrderBookFilePath());
    }

    @Override
    public void saveOrderBook(ReadOnlyOrderBook orderBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        orderBookStorage.saveOrderBook(orderBook, filePath);
    }

    // ================ ReturnOrderBook methods ==============================

    @Override
    public Path getReturnOrderBookFilePath() {
        return returnOrderBookStorage.getReturnOrderBookFilePath();
    }

    @Override
    public Optional<ReadOnlyReturnOrderBook> readReturnOrderBook() throws DataConversionException, IOException {
        return readReturnOrderBook(returnOrderBookStorage.getReturnOrderBookFilePath());
    }

    @Override
    public Optional<ReadOnlyReturnOrderBook> readReturnOrderBook(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return returnOrderBookStorage.readReturnOrderBook(filePath);
    }

    @Override
    public void saveReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook) throws IOException {
        saveReturnOrderBook(returnOrderBook, returnOrderBookStorage.getReturnOrderBookFilePath());
    }

    @Override
    public void saveReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        returnOrderBookStorage.saveReturnOrderBook(returnOrderBook, filePath);
    }

}
