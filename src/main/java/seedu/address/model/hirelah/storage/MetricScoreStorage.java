package seedu.address.model.hirelah.storage;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
//import seedu.address.model.hirelah.MetricScoreList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

public class MetricScoreStorage {
   /* private final Path path;
    private static final Logger logger = LogsCenter.getLogger(MetricScoreStorage.class);

    public MetricScoreStorage(Path newPath) {
        this.path = newPath;
    }

    public Path getPath() {
        return this.path;
    }

    /**
     * reads the data from the current Path to
     * retrieve all the information regarding MetricScore.
     * @return Optional<MetricScoreList>
     * @throws DataConversionException

    public Optional<MetricScore> readMetricScore(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        logger.info("starting to read");
        Optional<JsonSerializableMetricScore> jsonMetricScore = JsonUtil.readJsonFile(
                filePath, JsonSerializableMetricScore.class);
        logger.info("finish reading");
        if (jsonMetricScore.isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonMetricScore.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Save the information of the MetricScore
     * @param  source of the data. Cannot be null.

    public void saveInterview(MetricScoreList source) throws IOException {
        requireNonNull(source);
        requireNonNull(path);
        FileUtil.createIfMissing(path);
        JsonUtil.saveJsonFile(new JsonSerializableMetricScore(source), path);
    }*/
}

