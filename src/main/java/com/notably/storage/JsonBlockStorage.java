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
import com.notably.model.block.BlockTree;

/**
 * A class to access Block data stored as a json file on the hard disk.
 */
public class JsonBlockStorage implements BlockStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonBlockStorage.class);

    private Path filePath;

    public JsonBlockStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getBlockTreeFilePath() {
        return filePath;
    }

    @Override
    public Optional<BlockTree> readBlockTree() throws DataConversionException {
        return readBlockTree(filePath);
    }

    /**
     * Similar to {@link #readBlockTree()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<BlockTree> readBlockTree(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableBlockTree> jsonBlockTree = JsonUtil.readJsonFile(
                filePath, JsonSerializableBlockTree.class);
        if (!jsonBlockTree.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonBlockTree.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBlockTree(BlockTree blockTree) throws IOException {
        saveBlockTree(blockTree, filePath);
    }

    /**
     * Similar to {@link #saveBlockTree(BlockTree)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveBlockTree(BlockTree blockTree, Path filePath) throws IOException {
        requireNonNull(blockTree);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableBlockTree(blockTree), filePath);
    }

}
