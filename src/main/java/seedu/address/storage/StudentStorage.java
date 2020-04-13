package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.student.ReadOnlyStudent;
import seedu.address.model.student.StudentTAble;

/**
 * Represents a storage for {@link StudentTAble}.
 */
public interface StudentStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getStudentTAbleFilePath();

    /**
     * Returns StudentTAble data as a {@link ReadOnlyStudent}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudent> readStudentTAble() throws DataConversionException, IOException;

    /**
     * @see #getStudentTAbleFilePath()
     */
    Optional<ReadOnlyStudent> readStudentTAble(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStudent} to the storage.
     * @param studentTAble cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStudentTAble(ReadOnlyStudent studentTAble) throws IOException;

    /**
     * @see #saveStudentTAble(ReadOnlyStudent)
     */
    void saveStudentTAble(ReadOnlyStudent studentTAble, Path filePath) throws IOException;

}
