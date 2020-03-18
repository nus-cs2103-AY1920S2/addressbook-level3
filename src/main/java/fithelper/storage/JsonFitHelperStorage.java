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
import fithelper.model.ReadOnlyFitHelper;

/**
 * A class to access FitHelper data stored as a json file on the hard disk.
 */
public class JsonFitHelperStorage implements FitHelperStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFitHelperStorage.class);

    private Path filePath;

    public JsonFitHelperStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFitHelperFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFitHelper> readFitHelper() throws DataConversionException {
        return readFitHelper(filePath);
    }

    /**
     * Similar to {@link #readFitHelper()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFitHelper> readFitHelper(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFitHelper> jsonFitHelper = JsonUtil.readJsonFile(
                filePath, JsonSerializableFitHelper.class);
        if (!jsonFitHelper.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFitHelper.get().toModelType());
        } catch (IllegalArgumentException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        } catch (Exception i) {
            logger.info("Exception happened while reading data" + i.getMessage());
            throw new DataConversionException(i);
        }
    }

    @Override
    public void saveFitHelper(ReadOnlyFitHelper fitHelper) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        saveFitHelper(fitHelper, filePath);
    }

    /**
     * Similar to {@link #saveFitHelper(ReadOnlyFitHelper)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFitHelper(ReadOnlyFitHelper fitHelper, Path filePath) throws IOException {
        requireNonNull(fitHelper);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFitHelper(fitHelper), filePath);
    }

}
