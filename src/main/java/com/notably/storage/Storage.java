package com.notably.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.notably.commons.exceptions.DataConversionException;
import com.notably.model.block.BlockTree;
import com.notably.model.userpref.ReadOnlyUserPrefModel;
import com.notably.model.userpref.UserPrefModel;

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
    Optional<UserPrefModel> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefModel userPrefs) throws IOException;
}
