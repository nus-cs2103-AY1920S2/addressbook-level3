package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModManager;
import seedu.address.model.ReadOnlyModManager;

/**
 * Represents a storage for {@link ModManager}.
 */
public interface ModManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getModManagerFilePath();

    /**
     * Returns ModManager data as a {@link ReadOnlyModManager}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyModManager> readModManager() throws DataConversionException, IOException;

    /**
     * @see #getModManagerFilePath()
     */
    Optional<ReadOnlyModManager> readModManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyModManager} to the storage.
     * @param modManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveModManager(ReadOnlyModManager modManager) throws IOException;

    /**
     * @see #saveModManager(ReadOnlyModManager)
     */
    void saveModManager(ReadOnlyModManager modManager, Path filePath) throws IOException;

}
