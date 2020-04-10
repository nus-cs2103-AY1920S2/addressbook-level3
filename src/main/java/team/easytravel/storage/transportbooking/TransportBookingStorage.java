package team.easytravel.storage.transportbooking;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;
import team.easytravel.model.listmanagers.TransportBookingManager;

/**
 * Represents a storage for {@link TransportBookingManager}.
 */
public interface TransportBookingStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTransportBookingStorageFilePath();

    /**
     * Returns TransportBookingManager data as a {@link ReadOnlyTransportBookingManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTransportBookingManager> readTransportBookings() throws DataConversionException, IOException;

    /**
     * @see #readTransportBookings()
     */
    Optional<ReadOnlyTransportBookingManager> readTransportBookings(Path filePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyTransportBookingManager} to the storage.
     *
     * @param transportBookingManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTransportBookings(ReadOnlyTransportBookingManager transportBookingManager) throws IOException;

    /**
     * @see #saveTransportBookings(ReadOnlyTransportBookingManager)
     */
    void saveTransportBookings(
            ReadOnlyTransportBookingManager transportBookingManager, Path filePath) throws IOException;

}
