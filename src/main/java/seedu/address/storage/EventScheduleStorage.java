package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEventSchedule;

/**
 * Represents a storage for {@link seedu.address.model.EventSchedule}
 */
public interface EventScheduleStorage {

    /** Returns the file path of the data file */
    Path getEventScheduleFilePath();

    /**
     * Returns EventSchedule data as a {@link ReadOnlyEventSchedule}
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in the storage is not in expected format
     * @throws IOException If there was any problem when reading from storage.
     */
    Optional<ReadOnlyEventSchedule> readEventSchedule() throws DataConversionException, IOException;

    /** See #getEventScheduleFilePath() */
    Optional <ReadOnlyEventSchedule> readEventSchedule(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEventSchedule} to the storage.
     * @param eventSchedule cannot be null
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventSchedule(ReadOnlyEventSchedule eventSchedule) throws IOException;

    /** @see #saveEventSchedule(ReadOnlyEventSchedule) */
    void saveEventSchedule(ReadOnlyEventSchedule eventSchedule, Path filePath) throws IOException;
}
