package tatracker.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import tatracker.commons.core.LogsCenter;
import tatracker.commons.exceptions.DataConversionException;
import tatracker.commons.exceptions.IllegalValueException;
import tatracker.commons.util.FileUtil;
import tatracker.commons.util.JsonUtil;
import tatracker.model.ReadOnlyTaTracker;

/**
 * A class to access TaTracker data stored as a json file on the hard disk.
 */
public class JsonTaTrackerStorage implements TaTrackerStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTaTrackerStorage.class);

    private Path filePath;

    public JsonTaTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getTaTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyTaTracker> readTaTracker() throws DataConversionException {
        return readTaTracker(filePath);
    }

    /**
     * Similar to {@link #readTaTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyTaTracker> readTaTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableTaTracker> jsonTaTracker = JsonUtil.readJsonFile(
                filePath, JsonSerializableTaTracker.class);
        if (!jsonTaTracker.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonTaTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveTaTracker(ReadOnlyTaTracker taTracker) throws IOException {
        saveTaTracker(taTracker, filePath);
    }

    /**
     * Similar to {@link #saveTaTracker(ReadOnlyTaTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTaTracker(ReadOnlyTaTracker taTracker, Path filePath) throws IOException {
        requireNonNull(taTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableTaTracker(taTracker), filePath);
    }

}
