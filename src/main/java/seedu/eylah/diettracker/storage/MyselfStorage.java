package seedu.eylah.diettracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.diettracker.model.Myself;
import seedu.eylah.diettracker.model.ReadOnlyMyself;

/**
 * Represents a storage for {@link Myself}.
 */
public interface MyselfStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getMyselfFilePath();

    /**
     * Returns Myself data as a {@link ReadOnlyMyself}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyMyself> readMyself() throws DataConversionException, IOException;

    /**
     * @see #getMyselfFilePath()
     */
    Optional<ReadOnlyMyself> readMyself(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyMyself} to the storage.
     * @param myself cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveMyself(ReadOnlyMyself myself) throws IOException;

    /**
     * @see #saveMyself(ReadOnlyMyself)
     */
    void saveMyself(ReadOnlyMyself myself, Path filePath) throws IOException;

}
