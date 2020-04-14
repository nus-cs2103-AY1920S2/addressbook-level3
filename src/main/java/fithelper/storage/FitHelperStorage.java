package fithelper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fithelper.commons.exceptions.DataConversionException;
import fithelper.model.ReadOnlyFitHelper;

/**
 * Represents a storage for {@link fithelper.model.FitHelper}.
 */
public interface FitHelperStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFitHelperFilePath();

    /**
     * Returns FitHelper data as a {@link ReadOnlyFitHelper}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFitHelper> readFitHelper() throws DataConversionException, IOException;

    /**
     * @see #getFitHelperFilePath()
     */
    Optional<ReadOnlyFitHelper> readFitHelper(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFitHelper} to the storage.
     * @param fitHelper cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFitHelper(ReadOnlyFitHelper fitHelper) throws IOException;

    /**
     * @see #saveFitHelper(ReadOnlyFitHelper)
     */
    void saveFitHelper(ReadOnlyFitHelper fitHelper, Path filePath) throws IOException;

}
