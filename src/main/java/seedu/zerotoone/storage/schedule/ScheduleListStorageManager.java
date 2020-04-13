package seedu.zerotoone.storage.schedule;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.zerotoone.commons.core.LogsCenter;
import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.commons.exceptions.IllegalValueException;
import seedu.zerotoone.commons.util.FileUtil;
import seedu.zerotoone.commons.util.JsonUtil;
import seedu.zerotoone.model.schedule.ScheduleList;
import seedu.zerotoone.storage.schedule.util.JacksonScheduleList;

/**
 * A class to access ScheduleList data stored as a json file on the hard disk.
 */
public class ScheduleListStorageManager implements ScheduleListStorage {

    private static final Logger logger = LogsCenter.getLogger(ScheduleListStorageManager.class);

    private Path filePath;

    public ScheduleListStorageManager(Path filePath) {
        this.filePath = filePath;
    }

    public Path getScheduleListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ScheduleList> readScheduleList() throws DataConversionException {
        return readScheduleList(filePath);
    }

    /**
     * Similar to {@link #readScheduleList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ScheduleList> readScheduleList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        logger.fine(String.format("Attempting to read schedule list from file at %s.", filePath.toString()));

        Optional<JacksonScheduleList> jacksonScheduleList = JsonUtil.readJsonFile(
                filePath, JacksonScheduleList.class);
        if (!jacksonScheduleList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jacksonScheduleList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveScheduleList(ScheduleList scheduleList) throws IOException {
        logger.fine(String.format("Attempting to read schedule list from file at %s.", filePath.toString()));

        saveScheduleList(scheduleList, filePath);
    }

    /**
     * Similar to {@link #saveScheduleList(ScheduleList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveScheduleList(ScheduleList scheduleList, Path filePath) throws IOException {
        requireNonNull(scheduleList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JacksonScheduleList(scheduleList), filePath);
    }

}
