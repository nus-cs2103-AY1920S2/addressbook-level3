package seedu.zerotoone.storage.session;

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
import seedu.zerotoone.model.session.ReadOnlySessionList;
import seedu.zerotoone.storage.session.util.JacksonSessionList;

/**
 * A class to access SessionList data stored as a json file on the hard disk.
 */
public class SessionListStorageManager implements SessionListStorage {

    private static final Logger logger = LogsCenter.getLogger(SessionListStorageManager.class);

    private Path filePath;

    public SessionListStorageManager(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSessionListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlySessionList> readSessionList() throws DataConversionException {
        return readSessionList(filePath);
    }

    /**
     * Similar to {@link #readSessionList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlySessionList> readSessionList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JacksonSessionList> jacksonSessionList = JsonUtil.readJsonFile(
                filePath, JacksonSessionList.class);
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
    public void saveSessionList(ReadOnlySessionList sessionList) throws IOException {
        saveSessionList(sessionList, filePath);
    }

    /**
     * Similar to {@link #saveSessionList(ReadOnlySessionList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveSessionList(ReadOnlySessionList sessionList, Path filePath) throws IOException {
        requireNonNull(sessionList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JacksonSessionList(sessionList), filePath);
    }

}
