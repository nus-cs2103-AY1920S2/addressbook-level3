package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ExerciseList;
import seedu.address.model.readOnlyExerciseList;

/**
 * Represents a storage for {@link ExerciseList}.
 */
public interface ExerciseListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getExerciseListFilePath();

    /**
     * Returns ExerciseList data as a {@link readOnlyExerciseList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<readOnlyExerciseList> readExerciseList() throws DataConversionException, IOException;

    /**
     * @see #getExerciseListFilePath()
     */
    Optional<readOnlyExerciseList> readExerciseList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link readOnlyExerciseList} to the storage.
     * @param exerciseList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveExerciseList(readOnlyExerciseList exerciseList) throws IOException;

    /**
     * @see #saveExerciseList(readOnlyExerciseList)
     */
    void saveExerciseList(readOnlyExerciseList exerciseList, Path filePath) throws IOException;

}
