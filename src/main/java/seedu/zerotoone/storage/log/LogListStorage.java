package seedu.zerotoone.storage.log;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.log.ReadOnlyLogList;
import seedu.zerotoone.model.log.LogList;

/**
 * Represents a storage for {@link LogList}.
 */
public interface LogListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLogListFilePath();

    /**
     * Returns SessionList data as a {@link LogList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLogList> readLogList() throws DataConversionException, IOException;

    /**
     * @see #getLogListFilePath()
     */
    Optional<ReadOnlyLogList> readLogList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link LogList} to the storage.
     * @param sessionList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLogList(ReadOnlyLogList sessionList) throws IOException;

    /**
     * @see #saveLogList(ReadOnlyLogList)
     *
     */
    void saveLogList(ReadOnlyLogList sessionList, Path filePath) throws IOException;

}
