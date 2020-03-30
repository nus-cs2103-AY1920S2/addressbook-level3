package com.notably.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.notably.commons.exceptions.DataConversionException;
import com.notably.model.ReadOnlyUserPrefs;
import com.notably.model.UserPrefs;
import com.notably.model.block.BlockTree;

/**
 * API of the Storage component
 */
public interface Storage extends BlockStorage, UserPrefsStorage {
    @Override
    Path getBlockTreeFilePath();

    @Override
    Optional<BlockTree> readBlockTree() throws DataConversionException, IOException;

    @Override
    Optional<BlockTree> readBlockTree(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveBlockTree(BlockTree blockTree) throws IOException;

    @Override
    void saveBlockTree(BlockTree blockTree, Path filePath) throws IOException;

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;
}
