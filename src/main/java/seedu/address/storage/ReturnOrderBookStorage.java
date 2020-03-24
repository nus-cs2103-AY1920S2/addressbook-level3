package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyReturnOrderBook;
import seedu.address.model.ReturnOrderBook;

/**
 * Represents a storage for {@link ReturnOrderBook}.
 */
public interface ReturnOrderBookStorage {

    // ================ Return Order Book methods ==============================
    /**
     * Returns the file path of the data file.
     */
    Path getReturnOrderBookFilePath();

    /**
     * Returns returnOrderBook data as a {@link ReadOnlyReturnOrderBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyReturnOrderBook> readReturnOrderBook() throws DataConversionException, IOException;

    /**
     * @see #getReturnOrderBookFilePath()
     */
    Optional<ReadOnlyReturnOrderBook> readReturnOrderBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyReturnOrderBook} to the storage.
     * @param returnOrderBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook) throws IOException;

    /**
     * @see #saveReturnOrderBook(ReadOnlyReturnOrderBook)
     */
    void saveReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook, Path filePath) throws IOException;

}
