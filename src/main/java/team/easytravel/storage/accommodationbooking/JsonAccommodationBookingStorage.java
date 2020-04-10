package team.easytravel.storage.accommodationbooking;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import team.easytravel.commons.core.LogsCenter;
import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.commons.util.FileUtil;
import team.easytravel.commons.util.JsonUtil;
import team.easytravel.model.listmanagers.ReadOnlyAccommodationBookingManager;

/**
 * A class to access AccommodationBookingManager data stored as a json file on the hard disk.
 */
public class JsonAccommodationBookingStorage implements AccommodationBookingStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAccommodationBookingStorage.class);

    private Path filePath;

    public JsonAccommodationBookingStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAccommodationBookingStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAccommodationBookingManager> readAccommodationBookings() throws DataConversionException {
        return readAccommodationBookings(filePath);
    }

    /**
     * Similar to {@link #readAccommodationBookings()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAccommodationBookingManager> readAccommodationBookings(
            Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAccommodationBookingManager> jsonAccommodationBookingManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableAccommodationBookingManager.class);
        if (jsonAccommodationBookingManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAccommodationBookingManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAccommodationBookings(ReadOnlyAccommodationBookingManager accommodationBookingManager)
            throws IOException {
        saveAccommodationBookings(accommodationBookingManager, filePath);
    }

    /**
     * Similar to {@link #saveAccommodationBookings(ReadOnlyAccommodationBookingManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAccommodationBookings(
            ReadOnlyAccommodationBookingManager accommodationBookingManager, Path filePath) throws IOException {
        requireNonNull(accommodationBookingManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAccommodationBookingManager(accommodationBookingManager), filePath);
    }

}
