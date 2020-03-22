package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;

/**
 * Represents a storage for {@link OrderBook}.
 */
public interface OrderBookStorage {

    // ================ Order Book methods ==============================
    /**
     * Returns the file path of the data file.
     */
    Path getOrderBookFilePath();

    /**
     * Returns OrderBook data as a {@link ReadOnlyOrderBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException;

    /**
     * @see #getOrderBookFilePath()
     */
    Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyOrderBook} to the storage.
     * @param orderBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException;

    /**
     * @see #saveOrderBook(ReadOnlyOrderBook)
     */
    void saveOrderBook(ReadOnlyOrderBook orderBook, Path filePath) throws IOException;

    // ================ Return Order Book methods ==============================

    /**
     * Read the order book in the file path and return ReadOnlyOrderBook.
     * @param filePath path that the file stored.
     * @return ReadOnlyOrderBook which the orders get from the file.
     * @throws DataConversionException if there are problem converting the json to order object.
     * @throws IOException if there was any problem reading the file.
     */
    Optional<ReadOnlyOrderBook> readReturnOrderBook(Path filePath) throws DataConversionException, IOException;

    /**
     * @see #saveOrderBook(ReadOnlyOrderBook)
     */
    void saveReturnOrderBook(ReadOnlyOrderBook returnOrderBook, Path filePath) throws IOException;

}
