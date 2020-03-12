package seedu.eylah.diettracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.diettracker.model.FoodBook;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;

/**
 * Represents a storage for {@link FoodBook}.
 */
public interface FoodBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFoodBookFilePath();

    /**
     * Returns FoodBook data as a {@link ReadOnlyFoodBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFoodBook> readFoodBook() throws DataConversionException, IOException;

    /**
     * @see #getFoodBookFilePath()
     */
    Optional<ReadOnlyFoodBook> readFoodBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFoodBook} to the storage.
     * @param foodBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFoodBook(ReadOnlyFoodBook foodBook) throws IOException;

    /**
     * @see #saveFoodBook(ReadOnlyFoodBook)
     */
    void saveFoodBook(ReadOnlyFoodBook foodBook, Path filePath) throws IOException;

}
