package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCookbook;

/**
 * Represents a storage for {@link seedu.address.model.Cookbook}.
 */
public interface CookbookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCookbookFilePath();

    /**
     * Returns Cookbook data as a {@link ReadOnlyCookbook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCookbook> readCookbook() throws DataConversionException, IOException;

    /**
     * @see #getCookbookFilePath()
     */
    Optional<ReadOnlyCookbook> readCookbook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCookbook} to the storage.
     * @param cookbook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCookbook(ReadOnlyCookbook cookbook) throws IOException;

    /**
     * @see #saveCookbook(ReadOnlyCookbook)
     */
    void saveCookbook(ReadOnlyCookbook cookbook, Path filePath) throws IOException;

}
