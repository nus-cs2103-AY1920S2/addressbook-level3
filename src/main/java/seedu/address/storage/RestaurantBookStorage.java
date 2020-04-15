package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestaurantBook;

/**
 * Represents a storage for {@link seedu.address.model.RestaurantBook}.
 */
public interface RestaurantBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getRestaurantBookFilePath();

    /**
     * Returns RestaurantBook data as a {@link ReadOnlyRestaurantBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRestaurantBook> readRestaurantBook() throws DataConversionException, IOException;

    /**
     * @see #getRestaurantBookFilePath()
     */
    Optional<ReadOnlyRestaurantBook> readRestaurantBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRestaurantBook} to the storage.
     * @param restaurantBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook) throws IOException;

    /**
     * @see #saveRestaurantBook(ReadOnlyRestaurantBook)
     */
    void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook, Path filePath) throws IOException;

}
