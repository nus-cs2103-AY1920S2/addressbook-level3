package seedu.recipe.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyCookedRecordBook;
import seedu.recipe.model.cooked.CookedRecordBook;

/**
 * Represents a storage for {@link CookedRecordBook}.
 */
public interface CookedRecordBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCookedRecordFilePath();

    /**
     * Returns CookedRecordBook data as a {@link ReadOnlyCookedRecordBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCookedRecordBook> readCookedRecord() throws DataConversionException, IOException;

    /**
     * @see #getCookedRecordFilePath()
     */
    Optional<ReadOnlyCookedRecordBook> readCookedRecord(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCookedRecordBook} to the storage.
     * @param cookedRecord cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCookedRecord(ReadOnlyCookedRecordBook cookedRecord) throws IOException;

    /**
     * @see #saveCookedRecord(ReadOnlyCookedRecordBook)
     */
    void saveCookedRecord(ReadOnlyCookedRecordBook cookedRecord, Path filePath) throws IOException;

}