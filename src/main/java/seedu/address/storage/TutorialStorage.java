package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.event.tutorial.ReadOnlyTutorial;

/**
 * Represents a storage for {@link seedu.address.model.event.tutorial.Tutorial}.
 */

public interface TutorialStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTutorialsFilePath();

    /**
     * Returns Tutorial data as a {@link ReadOnlyTutorial}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTutorial> readTutorials() throws DataConversionException, IOException;

    /**
     * @see #getTutorialsFilePath()
     */
    Optional<ReadOnlyTutorial> readTutorials(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTutorial} to the storage.
     * @param tutorials cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTutorials(ReadOnlyTutorial tutorials) throws IOException;

    /**
     * @see #saveTutorials(ReadOnlyTutorial)
     */
    void saveTutorials(ReadOnlyTutorial tutorials, Path filePath) throws IOException;
}
