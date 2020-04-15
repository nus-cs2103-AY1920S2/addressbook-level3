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
import seedu.address.model.ReadOnlyEventSchedule;

/** A class to access Event Schedule data stored on a JSON file on the hard disk. */
public class JsonEventScheduleStorage implements EventScheduleStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonEventScheduleStorage.class);

    private Path filePath;

    public JsonEventScheduleStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEventScheduleFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventSchedule> readEventSchedule() throws DataConversionException {
        return readEventSchedule(filePath);
    }

    /**
     * Similar to {@Link #readEventSchedule()}
     * @param filePath Location of the data, cannot be null.
     * @throws DataConversionException if the file is not in correct format.
     */
    public Optional <ReadOnlyEventSchedule> readEventSchedule(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableEventSchedule> jsonEventSchedule = JsonUtil.readJsonFile(
                filePath, JsonSerializableEventSchedule.class);
        if (!jsonEventSchedule.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonEventSchedule.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEventSchedule(ReadOnlyEventSchedule eventSchedule) throws IOException {
        saveEventSchedule(eventSchedule, filePath);
    }

    /**
     * Similar to {@link #saveEventSchedule(ReadOnlyEventSchedule)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveEventSchedule(ReadOnlyEventSchedule eventSchedule, Path filePath) throws IOException {
        requireNonNull(eventSchedule);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableEventSchedule(eventSchedule), filePath);
    }

}
