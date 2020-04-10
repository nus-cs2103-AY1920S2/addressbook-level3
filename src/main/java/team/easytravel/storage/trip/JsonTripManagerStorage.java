package team.easytravel.storage.trip;

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
import team.easytravel.model.trip.TripManager;

/**
 * A class to access TripManager data stored as a json file on the hard disk.
 */
public class JsonTripManagerStorage implements TripManagerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTripManagerStorage.class);

    private Path filePath;

    public JsonTripManagerStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getTripStorageFilePath() {
        return filePath;
    }

    @Override
    public Optional<TripManager> readTripManager() throws DataConversionException {
        return readTripManager(filePath);
    }

    /**
     * Similar to {@link #readTripManager()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<TripManager> readTripManager(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTripManager> jsonTripManager = JsonUtil.readJsonFile(
                filePath, JsonSerializableTripManager.class);
        if (jsonTripManager.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTripManager.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTripManager(TripManager tripManager) throws IOException {
        saveTripManager(tripManager, filePath);
    }

    @Override
    public void saveTripManager(TripManager tripManager, Path filePath) throws IOException {
        requireNonNull(tripManager);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTripManager(tripManager), filePath);
    }
}
