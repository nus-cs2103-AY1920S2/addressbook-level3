package team.easytravel.storage.accommodationbooking;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.listmanagers.AccommodationBookingManager;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;

/**
 * Represents a storage for {@link AccommodationBookingManager}.
 */
public interface AccommodationBookingStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAccommodationBookingStorageFilePath();

    /**
     * Returns AccommodationBookingManager data as a {@link ReadOnlyAccommodationBookingManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAccommodationBookingManager> readAccommodationBookings()
            throws DataConversionException, IOException;

    /**
     * @see #readAccommodationBookings()
     */
    Optional<ReadOnlyAccommodationBookingManager> readAccommodationBookings(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAccommodationBookingManager} to the storage.
     *
     * @param accommodationBookingManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccommodationBookings(ReadOnlyAccommodationBookingManager accommodationBookingManager) throws IOException;

    /**
     * @see #saveAccommodationBookings(ReadOnlyAccommodationBookingManager)
     */
    void saveAccommodationBookings(
            ReadOnlyAccommodationBookingManager accommodationBookingManager, Path filePath) throws IOException;

}
