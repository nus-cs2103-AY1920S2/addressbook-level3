package seedu.zerotoone.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.zerotoone.commons.exceptions.DataConversionException;
import seedu.zerotoone.model.ReadOnlyExerciseList;

/**
 * Represents a storage for {@link seedu.zerotoone.model.ExerciseList}.
 */
public interface ExerciseListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExerciseListFilePath();

    /**
     * Returns ExerciseList data as a {@link ReadOnlyExerciseList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExerciseList> readExerciseList() throws DataConversionException, IOException;

    /**
     * @see #getExerciseListFilePath()
     */
    Optional<ReadOnlyExerciseList> readExerciseList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyExerciseList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExerciseList(ReadOnlyExerciseList addressBook) throws IOException;

    /**
     * @see #saveExerciseList(ReadOnlyExerciseList)
     */
    void saveExerciseList(ReadOnlyExerciseList addressBook, Path filePath) throws IOException;

}
