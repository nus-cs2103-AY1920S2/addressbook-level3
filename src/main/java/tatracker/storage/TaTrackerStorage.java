package tatracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tatracker.commons.exceptions.DataConversionException;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.TaTracker;

/**
 * Represents a storage for {@link TaTracker}.
 */
public interface TaTrackerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTaTrackerFilePath();

    /**
     * Returns TaTracker data as a {@link ReadOnlyTaTracker}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTaTracker> readTaTracker() throws DataConversionException, IOException;

    /**
     * @see #getTaTrackerFilePath()
     */
    Optional<ReadOnlyTaTracker> readTaTracker(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTaTracker} to the storage.
     * @param taTracker cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTaTracker(ReadOnlyTaTracker taTracker) throws IOException;

    /**
     * @see #saveTaTracker(ReadOnlyTaTracker)
     */
    void saveTaTracker(ReadOnlyTaTracker taTracker, Path filePath) throws IOException;

}
