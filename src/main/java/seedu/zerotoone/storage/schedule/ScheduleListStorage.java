package seedu.zerotoone.storage.schedule;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.schedule.ScheduleList;

/**
 * Represents a storage for {@link ScheduleList}.
 */
public interface ScheduleListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getScheduleListFilePath();

    /**
     * Returns ScheduleList data as a {@link ScheduleList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ScheduleList> readScheduleList() throws DataConversionException, IOException;

    /**
     * @see #getScheduleListFilePath()
     */
    Optional<ScheduleList> readScheduleList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ScheduleList} to the storage.
     * @param scheduleList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveScheduleList(ScheduleList scheduleList) throws IOException;

    /**
     * @see #saveScheduleList(ScheduleList)
     */
    void saveScheduleList(ScheduleList scheduleList, Path filePath) throws IOException;

}
