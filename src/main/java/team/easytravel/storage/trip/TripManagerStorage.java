package team.easytravel.storage.trip;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.trip.TripManager;

/**
 * Represents a storage for {@link TripManager}.
 */
public interface TripManagerStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTripStorageFilePath();

    /**
     * Returns TripManager data as a {@link TripManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<TripManager> readTripManager() throws DataConversionException, IOException;

    /**
     * @see #getTripStorageFilePath()
     */
    Optional<TripManager> readTripManager(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link TripManager} to the storage.
     *
     * @param tripManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTripManager(TripManager tripManager) throws IOException;

    /**
     * @see #saveTripManager(TripManager)
     */
    void saveTripManager(TripManager tripManager, Path filePath) throws IOException;

}
