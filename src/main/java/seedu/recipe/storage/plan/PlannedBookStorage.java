package seedu.recipe.storage.plan;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recipe.commons.exceptions.DataConversionException;
import seedu.recipe.model.ReadOnlyPlannedBook;

/**
 * Represents a storage for {@link seedu.recipe.model.plan.PlannedBook}.
 */
public interface PlannedBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPlannedBookFilePath();

    /**
     * Returns PlannedBook data as a {@link ReadOnlyPlannedBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPlannedBook> readPlannedBook() throws DataConversionException, IOException;

    /**
     * @see #getPlannedBookFilePath()
     */
    Optional<ReadOnlyPlannedBook> readPlannedBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPlannedBook} to the storage.
     * @param plannedBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePlannedBook(ReadOnlyPlannedBook plannedBook) throws IOException;

    /**
     * @see #savePlannedBook(ReadOnlyPlannedBook)
     */
    void savePlannedBook(ReadOnlyPlannedBook plannedBook, Path filePath) throws IOException;
}
