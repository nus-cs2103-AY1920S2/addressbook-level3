package tatracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tatracker.commons.exceptions.DataConversionException;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;

/**
 * Represents a storage for {@link TaTracker}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns TaTracker data as a {@link ReadOnlyTaTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaTracker> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyTaTracker> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaTracker} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyTaTracker addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyTaTracker)
     */
    void saveAddressBook(ReadOnlyTaTracker addressBook, Path filePath) throws IOException;

}
