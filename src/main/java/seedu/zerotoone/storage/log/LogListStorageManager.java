package seedu.zerotoone.storage.log;

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
import seedu.zerotoone.model.log.ReadOnlyLogList;
import seedu.zerotoone.storage.log.util.JacksonLogList;

/**
 * A class to access SessionList data stored as a json file on the hard disk.
 */
public class LogListStorageManager implements LogListStorage {

    private static final Logger logger = LogsCenter.getLogger(LogListStorageManager.class);

    private Path filePath;

    public LogListStorageManager(Path filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    public Path getLogListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLogList> readLogList() throws DataConversionException {
        return readLogList(filePath);
    }

    /**
     * Similar to {@link #readLogList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLogList> readLogList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        logger.fine(String.format("Attempting to read log list from file at %s.", filePath.toString()));

        Optional<JacksonLogList> jacksonSessionList = JsonUtil.readJsonFile(filePath, JacksonLogList.class);
        if (jacksonSessionList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jacksonSessionList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLogList(ReadOnlyLogList sessionList) throws IOException {
        logger.info(String.format("Attempting to save log list to file in %s.", filePath.toString()));
        saveLogList(sessionList, filePath);
    }

    /**
     * Similar to {@link #saveLogList(ReadOnlyLogList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveLogList(ReadOnlyLogList sessionList, Path filePath) throws IOException {
        requireNonNull(sessionList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JacksonLogList(sessionList), filePath);
    }

}
