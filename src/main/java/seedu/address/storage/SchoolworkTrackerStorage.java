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
    Path getSchedulerFilePath();

    /**
     * Returns SchoolworkTracker data as a {@link ReadOnlySchoolworkTracker}.
     * Returns {@code Optional.empty()} if storage file not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySchoolworkTracker> readScheduler() throws DataConversionException, IOException;

    /**
     * @see #getSchedulerFilePath()
     */
    Optional<ReadOnlySchoolworkTracker> readScheduler(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlySchoolworkTracker} to the storage.
     * @param scheduler cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveScheduler(ReadOnlySchoolworkTracker scheduler) throws IOException;

    /**
     * @see #saveScheduler(ReadOnlySchoolworkTracker) (ReadOnlySchoolworkTracker)
     */
    void saveScheduler(ReadOnlySchoolworkTracker scheduler, Path filePath) throws IOException;
}
