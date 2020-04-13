package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyResumeBook;
import seedu.address.model.ResumeBook;

/**
 * Represents a storage for {@link ResumeBook}.
 */
public interface ResumeBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getResumeBookFilePath();

    /**
     * Returns ResumeBook data as a {@link ReadOnlyResumeBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyResumeBook> readResumeBook() throws DataConversionException, IOException;

    /**
     * @see #getResumeBookFilePath()
     */
    Optional<ReadOnlyResumeBook> readResumeBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyResumeBook} to the storage.
     * @param resumeBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveResumeBook(ReadOnlyResumeBook resumeBook) throws IOException;

    /**
     * @see #saveResumeBook(ReadOnlyResumeBook)
     */
    void saveResumeBook(ReadOnlyResumeBook resumeBook, Path filePath) throws IOException;

}
