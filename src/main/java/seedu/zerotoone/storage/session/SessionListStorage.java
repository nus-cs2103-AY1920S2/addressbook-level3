package seedu.zerotoone.storage.session;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.session.ReadOnlySessionList;
import seedu.zerotoone.model.session.SessionList;

/**
 * Represents a storage for {@link SessionList}.
 */
public interface SessionListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSessionListFilePath();

    /**
     * Returns SessionList data as a {@link SessionList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlySessionList> readSessionList() throws DataConversionException, IOException;

    /**
     * @see #getSessionListFilePath()
     */
    Optional<ReadOnlySessionList> readSessionList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link SessionList} to the storage.
     * @param sessionList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveSessionList(ReadOnlySessionList sessionList) throws IOException;

    /**
     * @see #saveSessionList(ReadOnlySessionList)
     *
     */
    void saveSessionList(ReadOnlySessionList sessionList, Path filePath) throws IOException;

}
