package com.notably.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.notably.commons.exceptions.DataConversionException;
import com.notably.model.block.BlockModel;

/**
 * Represents a storage for {@link com.notably.model.BlockModel}.
 */
public interface BlockStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBlockDataFilePath();

    /**
     * Returns BlockModel data as a {@link BlockModel}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<BlockModel> readBlockModel() throws DataConversionException, IOException;

    /**
     * @see #getBlockModelFilePath()
     */
    Optional<BlockModel> readBlockModel(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link BlockModel} to the storage.
     * @param blockModel cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBlockModel(BlockModel blockModel) throws IOException;

    /**
     * @see #saveBlockModel(BlockModel)
     */
    void saveBlockModel(BlockModel blockModel, Path filePath) throws IOException;

}
