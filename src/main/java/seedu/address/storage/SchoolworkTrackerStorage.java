package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySchoolworkTracker;
import seedu.address.model.SchoolworkTracker;

/**
 * Represents a storage for {@link SchoolworkTracker}.
 */
public interface SchoolworkTrackerStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getSchoolworkTrackerFilePath();

    /**
     * Returns SchoolworkTracker data as a {@link ReadOnlySchoolworkTracker}.
     * Returns {@code Optional.empty()} if storage file not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySchoolworkTracker> readSchoolworkTracker() throws DataConversionException, IOException;

    /**
     * @see #getSchoolworkTrackerFilePath()
     */
    Optional<ReadOnlySchoolworkTracker> readSchoolworkTracker(Path filePath)
        throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySchoolworkTracker} to the storage.
     * @param schoolworkTracker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSchoolworkTracker(ReadOnlySchoolworkTracker schoolworkTracker) throws IOException;

    /**
     * @see #saveSchoolworkTracker(ReadOnlySchoolworkTracker) (ReadOnlySchoolworkTracker)
     */
    void saveSchoolworkTracker(ReadOnlySchoolworkTracker schoolworkTracker, Path filePath) throws IOException;
}
