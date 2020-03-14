package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.InventorySystem;
import seedu.address.model.ReadOnlyInventorySystem;

/**
 * Represents a storage for {@link InventorySystem}.
 */
public interface InventorySystemStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns InventorySystem data as a {@link ReadOnlyInventorySystem}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyInventorySystem> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyInventorySystem> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyInventorySystem} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyInventorySystem addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyInventorySystem)
     */
    void saveAddressBook(ReadOnlyInventorySystem addressBook, Path filePath) throws IOException;

}
