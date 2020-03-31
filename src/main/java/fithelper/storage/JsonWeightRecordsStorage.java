package fithelper.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import fithelper.commons.core.LogsCenter;
import fithelper.commons.exceptions.DataConversionException;
import fithelper.commons.util.FileUtil;
import fithelper.commons.util.JsonUtil;
import fithelper.model.ReadOnlyWeightRecords;

/**
 * A class to access WeightRecords weight data stored as a json file on the hard disk.
 */
public class JsonWeightRecordsStorage implements WeightRecordsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonWeightRecordsStorage.class);

    private Path filePath;

    public JsonWeightRecordsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getWeightRecordsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyWeightRecords> readWeightRecords() throws DataConversionException {
        return readWeightRecords(filePath);
    }

    /**
     * Similar to {@link #readWeightRecords()}.
     *
     * @param filePath location of the weight data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyWeightRecords> readWeightRecords(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableWeightRecords> jsonWeightRecords = JsonUtil.readJsonFile(
                filePath, JsonSerializableWeightRecords.class);
        if (!jsonWeightRecords.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonWeightRecords.get().toModelType());
        } catch (IllegalArgumentException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        } catch (Exception i) {
            logger.info("Exception happened while reading weight data" + i.getMessage());
            throw new DataConversionException(i);
        }
    }

    @Override
    public void saveWeightRecords(ReadOnlyWeightRecords weightRecords) throws IOException {
        logger.fine("Attempting to write to weight data file: " + filePath);
        saveWeightRecords(weightRecords, filePath);
    }

    /**
     * Similar to {@link #saveWeightRecords(ReadOnlyWeightRecords)}.
     *
     * @param filePath location of the weight data. Cannot be null.
     */
    public void saveWeightRecords(ReadOnlyWeightRecords weightRecords, Path filePath) throws IOException {
        requireNonNull(weightRecords);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableWeightRecords(weightRecords), filePath);
    }

}
