package seedu.expensela.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.expensela.commons.exceptions.DataConversionException;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.ReadOnlyGlobalData;

/**
 * Represents a storage for {@link seedu.expensela.model.GlobalData}.
 */
public interface GlobalDataStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getGlobalDataFilePath();

    /**
     * Returns GlobalData data as a {@link ReadOnlyGlobalData}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<GlobalData> readGlobalData() throws DataConversionException, IOException;

    /**
     * @see #getGlobalDataFilePath()
     */
    Optional<GlobalData> readGlobalData(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyGlobalData} to the storage.
     * @param globalData cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveGlobalData(ReadOnlyGlobalData globalData) throws IOException;

    /**
     * @see #saveGlobalData(ReadOnlyGlobalData)
     */
    void saveGlobalData(ReadOnlyGlobalData globalData, Path filePath) throws IOException;

}
