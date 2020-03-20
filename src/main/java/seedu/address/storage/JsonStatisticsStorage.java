package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyPomodoro;
import seedu.address.model.ReadOnlyStatistics;

/** A class to access TaskList data stored as a json file on the hard disk. */
public class JsonStatisticsStorage implements StatisticsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStatisticsStorage.class);

    private Path filePath;

    public JsonStatisticsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getStatisticsFilePath() {
        return filePath;
    }

    /**
     * Similar to {@link #readTaskList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyStatistics> readStatistics() throws DataConversionException {
        requireNonNull(this.filePath);

        Optional<JsonAdaptedStatistics> jsonStatistics =
                JsonUtil.readJsonFile(this.filePath, JsonAdaptedStatistics.class);
        if (!jsonStatistics.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonStatistics.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Similar to {@link #saveStatistics(ReadOnlyStatistics)}.
     *
     * @param statistics location of the data. Cannot be null.
     */
    @Override
    public void saveStatistics(ReadOnlyStatistics statistics) throws IOException {
        requireNonNull(statistics);
        requireNonNull(this.filePath);

        FileUtil.createIfMissing(this.filePath);
        JsonUtil.saveJsonFile(new JsonAdaptedStatistics(statistics), filePath);
    }
}