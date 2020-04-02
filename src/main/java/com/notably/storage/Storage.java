package com.notably.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.notably.commons.exceptions.DataConversionException;
import com.notably.model.block.BlockModel;
import com.notably.model.userpref.ReadOnlyUserPrefModel;
import com.notably.model.userpref.UserPrefModel;

/**
 * API of the Storage component
 */
public interface Storage extends BlockStorage, UserPrefsStorage {
    @Override
    Path getBlockDataFilePath();

    @Override
    Optional<BlockModel> readBlockModel() throws DataConversionException, IOException;

    @Override
    Optional<BlockModel> readBlockModel(Path filePath) throws DataConversionException, IOException;

    @Override
    void saveBlockModel(BlockModel blockModel) throws IOException;

    @Override
    void saveBlockModel(BlockModel blockModel, Path filePath) throws IOException;

    @Override
    Optional<UserPrefModel> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefModel userPrefs) throws IOException;
}
