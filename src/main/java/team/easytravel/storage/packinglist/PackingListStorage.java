package team.easytravel.storage.packinglist;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;

/**
 * Represents a storage for {@link PackingListStorage}.
 */
public interface PackingListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPackingListStorageFilePath();

    /**
     * Returns PackingListManager data as a {@link ReadOnlyPackingListManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPackingListManager> readPackingList() throws DataConversionException, IOException;

    /**
     * @see #readPackingList()
     */
    Optional<ReadOnlyPackingListManager> readPackingList(
            Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPackingListManager} to the storage.
     *
     * @param packingListManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePackingList(ReadOnlyPackingListManager packingListManager) throws IOException;

    /**
     * @see #savePackingList(ReadOnlyPackingListManager)
     */
    void savePackingList(ReadOnlyPackingListManager packingListManager, Path filePath) throws IOException;
}
