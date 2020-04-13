package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.mod.ReadOnlyMod;

/**
 * Represents a storage for {@link seedu.address.model.mod.Mod}.
 */

public interface ModStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getModsFilePath();

    /**
     * Returns Mod data as a {@link ReadOnlyMod}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMod> readMods() throws DataConversionException, IOException;

    /**
     * @see #getModsFilePath()
     */
    Optional<ReadOnlyMod> readMods(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMod} to the storage.
     *
     * @param mods cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMods(ReadOnlyMod mods) throws IOException;

    /**
     * @see #saveMods(ReadOnlyMod)
     */
    void saveMods(ReadOnlyMod mods, Path filePath) throws IOException;
}

