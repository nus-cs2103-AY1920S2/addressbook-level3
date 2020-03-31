package com.notably.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.notably.commons.exceptions.DataConversionException;
import com.notably.model.block.BlockTree;

/**
 * Represents a storage for {@link com.notably.model.BlockTree}.
 */
public interface BlockStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getBlockTreeFilePath();

    /**
     * Returns BlockTree data as a {@link BlockTree}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<BlockTree> readBlockTree() throws DataConversionException, IOException;

    /**
     * @see #getBlockTreeFilePath()
     */
    Optional<BlockTree> readBlockTree(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link BlockTree} to the storage.
     * @param blockTree cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveBlockTree(BlockTree blockTree) throws IOException;

    /**
     * @see #saveBlockTree(BlockTree)
     */
    void saveBlockTree(BlockTree blockTree, Path filePath) throws IOException;

}
