package com.notably.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.notably.commons.LogsCenter;
import com.notably.commons.exceptions.DataConversionException;
import com.notably.commons.exceptions.IllegalValueException;
import com.notably.commons.util.FileUtil;
import com.notably.commons.util.JsonUtil;
import com.notably.model.block.BlockModel;

/**
 * A class to access Block data stored as a json file on the hard disk.
 */
public class JsonBlockStorage implements BlockStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBlockStorage.class);

    private Path filePath;

    public JsonBlockStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBlockDataFilePath() {
        return filePath;
    }

    @Override
    public Optional<BlockModel> readBlockModel() throws DataConversionException {
        return readBlockModel(filePath);
    }

    /**
     * Similar to {@link #readBlockModel()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<BlockModel> readBlockModel(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBlockModel> jsonBlockModel = JsonUtil.readJsonFile(
                filePath, JsonSerializableBlockModel.class);
        if (!jsonBlockModel.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBlockModel.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBlockModel(BlockModel blockModel) throws IOException {
        saveBlockModel(blockModel, filePath);
    }

    /**
     * Similar to {@link #saveBlockModel(BlockModel)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBlockModel(BlockModel blockModel, Path filePath) throws IOException {
        requireNonNull(blockModel);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBlockModel(blockModel), filePath);
    }

}
