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
    Path getInventorySystemFilePath();

    /**
     * Returns InventorySystem data as a {@link ReadOnlyInventorySystem}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyInventorySystem> readInventorySystem() throws DataConversionException, IOException;

    /**
     * @see #getInventorySystemFilePath()
     */
    Optional<ReadOnlyInventorySystem> readInventorySystem(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyInventorySystem} to the storage.
     * @param inventorySystem cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveInventorySystem(ReadOnlyInventorySystem inventorySystem) throws IOException;

    /**
     * @see #saveInventorySystem(ReadOnlyInventorySystem)
     */
    void saveInventorySystem(ReadOnlyInventorySystem addressBook, Path filePath) throws IOException;

}
