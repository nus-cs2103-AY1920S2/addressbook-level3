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
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyOrderBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyOrderBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyOrderBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyOrderBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyOrderBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyOrderBook)
     */
    void saveAddressBook(ReadOnlyOrderBook addressBook, Path filePath) throws IOException;

}
