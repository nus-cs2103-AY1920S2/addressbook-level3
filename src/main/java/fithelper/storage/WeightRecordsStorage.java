package fithelper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fithelper.commons.exceptions.DataConversionException;
import fithelper.model.ReadOnlyWeightRecords;

/**
 * Represents a storage for {@link fithelper.model.WeightRecords}.
 */
public interface WeightRecordsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getWeightRecordsFilePath();

    /**
     * Returns WeightRecords data as a {@link ReadOnlyWeightRecords}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyWeightRecords> readWeightRecords() throws DataConversionException, IOException;

    /**
     * @see #getWeightRecordsFilePath()
     */
    Optional<ReadOnlyWeightRecords> readWeightRecords(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyWeightRecords} to the storage.
     * @param weightRecords cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveWeightRecords(ReadOnlyWeightRecords weightRecords) throws IOException;

    /**
     * @see #saveWeightRecords(ReadOnlyWeightRecords)
     */
    void saveWeightRecords(ReadOnlyWeightRecords weightRecords, Path filePath) throws IOException;

}
