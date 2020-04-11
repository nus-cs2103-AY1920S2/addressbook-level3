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
import seedu.address.model.ReadOnlySchoolworkTracker;

/**
 * A class to access SchoolworkTracker data stored in a JSON file on the hard disk.
 */
public class JsonSchoolworkTrackerStorage implements SchoolworkTrackerStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonSchoolworkTrackerStorage.class);

    private Path filePath;

    public JsonSchoolworkTrackerStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getSchoolworkTrackerFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySchoolworkTracker> readSchoolworkTracker() throws DataConversionException {
        return readSchoolworkTracker(filePath);
    }

    /**
     * Similar to {@link #readSchoolworkTracker()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySchoolworkTracker> readSchoolworkTracker(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableSchoolworkTracker> jsonSchoolworkTracker = JsonUtil.readJsonFile(
            filePath, JsonSerializableSchoolworkTracker.class);
        if (!jsonSchoolworkTracker.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonSchoolworkTracker.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveSchoolworkTracker(ReadOnlySchoolworkTracker schoolworkTracker) throws IOException {
        saveSchoolworkTracker(schoolworkTracker, filePath);
    }

    /**
     * Similar to {@link #saveSchoolworkTracker(ReadOnlySchoolworkTracker)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSchoolworkTracker(ReadOnlySchoolworkTracker schoolworkTracker, Path filePath) throws IOException {
        requireNonNull(schoolworkTracker);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableSchoolworkTracker(schoolworkTracker), filePath);
    }
}
