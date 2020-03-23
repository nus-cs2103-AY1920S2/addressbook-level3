package seedu.zerotoone.storage.workout;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.workout.WorkoutList;
import seedu.zerotoone.model.workout.ReadOnlyWorkoutList;

/**
 * Represents a storage for {@link WorkoutList}.
 */
public interface WorkoutListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWorkoutListFilePath();

    /**
     * Returns WorkoutList data as a {@link ReadOnlyWorkoutList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWorkoutList> readWorkoutList() throws DataConversionException, IOException;

    /**
     * @see #getWorkoutListFilePath()
     */
    Optional<ReadOnlyWorkoutList> readWorkoutList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWorkoutList} to the storage.
     * @param workoutList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWorkoutList(ReadOnlyWorkoutList workoutList) throws IOException;

    /**
     * @see #saveWorkoutList(ReadOnlyWorkoutList)
     */
    void saveWorkoutList(ReadOnlyWorkoutList workoutList, Path filePath) throws IOException;

}
