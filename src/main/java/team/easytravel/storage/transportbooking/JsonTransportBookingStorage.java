package team.easytravel.storage.transportbooking;

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
import team.easytravel.model.listmanagers.ReadOnlyTransportBookingManager;

/**
 * A class to access TransportBookingManager data stored as a json file on the hard disk.
 */
public class JsonTransportBookingStorage implements TransportBookingStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTransportBookingStorage.class);

    private Path filePath;

    public JsonTransportBookingStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTransportBookingStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTransportBookingManager> readTransportBookings() throws DataConversionException {
        return readTransportBookings(filePath);
    }

    /**
     * Similar to {@link #readTransportBookings()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyTransportBookingManager> readTransportBookings(
            Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTransportBookingManager> jsonTransportBookingManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableTransportBookingManager.class);
        if (jsonTransportBookingManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTransportBookingManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTransportBookings(ReadOnlyTransportBookingManager transportBookingManager) throws IOException {
        saveTransportBookings(transportBookingManager, filePath);
    }

    /**
     * Similar to {@link #saveTransportBookings(ReadOnlyTransportBookingManager)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveTransportBookings(
            ReadOnlyTransportBookingManager transportBookingManager, Path filePath) throws IOException {
        requireNonNull(transportBookingManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTransportBookingManager(transportBookingManager), filePath);
    }

}
