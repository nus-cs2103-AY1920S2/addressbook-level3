package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AssignmentSchedule;
import seedu.address.model.ReadOnlyAssignmentSchedule;

/**
 * Represents a storage for {@link AssignmentSchedule}.
 */
public interface AssignmentScheduleStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getSchedulerFilePath();

    /**
     * Returns AssignmentSchedule data as a {@link ReadOnlyAssignmentSchedule}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAssignmentSchedule> readScheduler() throws DataConversionException, IOException;

    /**
     * @see #getSchedulerFilePath()
     */
    Optional<ReadOnlyAssignmentSchedule> readScheduler(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAssignmentSchedule} to the storage.
     * @param scheduler cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveScheduler(ReadOnlyAssignmentSchedule scheduler) throws IOException;

    /**
     * @see #saveScheduler(ReadOnlyAssignmentSchedule) (ReadOnlyAssignmentSchedule)
     */
    void saveScheduler(ReadOnlyAssignmentSchedule scheduler, Path filePath) throws IOException;
}
