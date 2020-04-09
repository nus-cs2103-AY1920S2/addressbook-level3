package seedu.eylah.diettracker.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.eylah.commons.core.LogsCenter;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.commons.util.FileUtil;
import seedu.eylah.commons.util.JsonUtil;
import seedu.eylah.diettracker.model.ReadOnlyMyself;

/**
 * A class to access FoodBook data stored as a json file on the hard disk.
 */
public class JsonMyselfStorage implements MyselfStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFoodBookStorage.class);

    private Path filePath;

    public JsonMyselfStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMyselfFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMyself> readMyself() throws DataConversionException {
        return readMyself(filePath);
    }

    /**
     * Similar to {@link #readMyself()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMyself> readMyself(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableMyself> jsonMyself = JsonUtil.readJsonFile(
                filePath, JsonSerializableMyself.class);
        if (!jsonMyself.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonMyself.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveMyself(ReadOnlyMyself myself) throws IOException {
        saveMyself(myself, filePath);
    }

    /**
     * Similar to {@link #saveMyself(ReadOnlyMyself)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMyself(ReadOnlyMyself myself, Path filePath) throws IOException {
        requireNonNull(myself);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMyself(myself), filePath);
    }

}
