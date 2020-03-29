package seedu.recipe.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyCookedRecord;
import seedu.recipe.model.cooked.CookedRecord;

/**
 * Represents a storage for {@link CookedRecord}.
 */
public interface CookedRecordStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCookedRecordFilePath();

    /**
     * Returns CookedRecord data as a {@link ReadOnlyCookedRecord}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCookedRecord> readCookedRecord() throws DataConversionException, IOException;

    /**
     * @see #getCookedRecordFilePath()
     */
    Optional<ReadOnlyCookedRecord> readCookedRecord(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCookedRecord} to the storage.
     * @param cookedRecord cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCookedRecord(ReadOnlyCookedRecord cookedRecord) throws IOException;

    /**
     * @see #saveCookedRecord(ReadOnlyCookedRecord)
     */
    void saveCookedRecord(ReadOnlyCookedRecord cookedRecord, Path filePath) throws IOException;

}