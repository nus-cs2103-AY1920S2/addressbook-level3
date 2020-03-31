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
    Path getCookedRecordBookFilePath();

    /**
     * Returns CookedRecordBook data as a {@link ReadOnlyCookedRecordBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCookedRecordBook> readCookedRecordBook() throws DataConversionException, IOException;

    /**
     * @see #getCookedRecordBookFilePath()
     */
    Optional<ReadOnlyCookedRecordBook> readCookedRecordBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCookedRecordBook} to the storage.
     * @param cookedRecordBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCookedRecordBook(ReadOnlyCookedRecordBook cookedRecordBook) throws IOException;

    /**
     * @see #saveCookedRecordBook(ReadOnlyCookedRecordBook)
     */
    void saveCookedRecordBook(ReadOnlyCookedRecordBook cookedRecordBook, Path filePath) throws IOException;
}
