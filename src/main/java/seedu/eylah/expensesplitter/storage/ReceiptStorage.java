package seedu.eylah.expensesplitter.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;

/**
 * Represents a storage for {here should put link to receipt!!!}.
 */
public interface ReceiptStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getReceiptBookFilePath();

    /**
     * Returns ReceiptBook data as a {@link ReadOnlyReceiptBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyReceiptBook> readReceiptBook() throws DataConversionException, IOException;

    /**
     * @see @getReceiptBookFilePath()
     */
    Optional<ReadOnlyReceiptBook> readReceiptBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyReceiptBook} to the storage.
     * @param receiptBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveReceiptBook(ReadOnlyReceiptBook receiptBook) throws IOException;

    /**
     * @see @saveReceiptBook(ReadOnlyReceiptBook)
     */
    void saveReceiptBook(ReadOnlyReceiptBook receiptBook, Path filePath) throws IOException;

}
