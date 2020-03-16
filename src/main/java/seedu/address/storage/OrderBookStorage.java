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

    /**
     * Returns the file path of the data file.
     */
    Path getOrderBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyOrderBook}.
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
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveOrderBook(ReadOnlyOrderBook addressBook) throws IOException;

    /**
     * @see #saveOrderBook(ReadOnlyOrderBook)
     */
    void saveOrderBook(ReadOnlyOrderBook addressBook, Path filePath) throws IOException;

}
