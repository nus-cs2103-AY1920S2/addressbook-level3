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
import seedu.address.model.ReadOnlyAssignmentSchedule;

/**
 * A class to access AssignmentSchedule data stored in a JSON file on the hard disk.
 */
public class JsonAssignmentScheduleStorage implements AssignmentScheduleStorage {
    private static final Logger logger = LogsCenter.getLogger(JsonAssignmentScheduleStorage.class);

    private Path filePath;

    public JsonAssignmentScheduleStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAssignmentScheduleFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAssignmentSchedule> readAssignmentSchedule() throws DataConversionException {
        return readAssignmentSchedule(filePath);
    }

    /**
     * Similar to {@link #readAssignmentSchedule()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAssignmentSchedule> readAssignmentSchedule(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAssignmentSchedule> jsonScheduler = JsonUtil.readJsonFile(
                filePath, JsonSerializableAssignmentSchedule.class);
        if (!jsonScheduler.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonScheduler.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());

            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAssignmentSchedule(ReadOnlyAssignmentSchedule scheduler) throws IOException {
        saveAssignmentSchedule(scheduler, filePath);
    }

    /**
     * Similar to {@link #saveAssignmentSchedule(ReadOnlyAssignmentSchedule)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAssignmentSchedule(ReadOnlyAssignmentSchedule scheduler, Path filePath) throws IOException {
        requireNonNull(scheduler);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAssignmentSchedule(scheduler), filePath);
    }

}
