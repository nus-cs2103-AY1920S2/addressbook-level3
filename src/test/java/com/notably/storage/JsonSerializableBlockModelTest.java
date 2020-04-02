package com.notably.storage;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.notably.commons.exceptions.IllegalValueException;
import com.notably.commons.path.AbsolutePath;
import com.notably.commons.util.JsonUtil;
import com.notably.model.block.BlockModel;
import com.notably.testutil.TypicalBlockModel;

public class JsonSerializableBlockModelTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBlockModelTest");
    private static final Path TYPICAL_BLOCKS_FILE = TEST_DATA_FOLDER.resolve("typicalBlockData.json");
    private static final Path INVALID_BLOCK_FILE = TEST_DATA_FOLDER.resolve("invalidBlockData.json");
    private static final Path DUPLICATE_BLOCK_FILE = TEST_DATA_FOLDER.resolve("duplicateBlockData.json");
    private static final Path MISSING_PATH_BLOCK_FILE = TEST_DATA_FOLDER.resolve("missingPathBlockData.json");
    private static final Path PATH_DOESNT_EXIST_BLOCK_FILE = TEST_DATA_FOLDER.resolve("pathDoesntExistBlockData.json");
    private static final Path BLANK_DATA_INVALID_PATH_BLOCK_FILE = TEST_DATA_FOLDER.resolve(
        "blankDataInvalidPathBlockData.json");

    @Test
    public void toModelType_typicalBlocksFile_success() throws Exception {
        JsonSerializableBlockModel dataFromFile = JsonUtil.readJsonFile(TYPICAL_BLOCKS_FILE,
            JsonSerializableBlockModel.class).get();
        BlockModel blockModelFromFile = dataFromFile.toModelType();
        BlockModel typicalBlockModel = TypicalBlockModel.getTypicalBlockModel();
        assertEquals(blockModelFromFile.getBlockTree(), typicalBlockModel.getBlockTree());
        assertEquals(blockModelFromFile.getCurrentlyOpenPath(), typicalBlockModel.getCurrentlyOpenPath());
    }

    @Test
    public void toModelType_invalidBlockFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBlockModel dataFromFile = JsonUtil.readJsonFile(INVALID_BLOCK_FILE,
            JsonSerializableBlockModel.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBlockFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBlockModel dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BLOCK_FILE,
            JsonSerializableBlockModel.class).get();
        assertThrows(IllegalValueException.class, JsonAdaptedBlockTreeItem.MESSAGE_DUPLICATE_BLOCK_CHILD,
            dataFromFile::toModelType);
    }

    @Test
    public void toModelType_missingPathBlockFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBlockModel dataFromFile = JsonUtil.readJsonFile(MISSING_PATH_BLOCK_FILE,
            JsonSerializableBlockModel.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableBlockModel.MISSING_LAST_OPENED_PATH_FIELD,
            dataFromFile::toModelType);
    }

    @Test
    public void toModelType_pathDoesntExistBlockFile_defaultFirstRootChild() throws Exception {
        JsonSerializableBlockModel dataFromFile = JsonUtil.readJsonFile(PATH_DOESNT_EXIST_BLOCK_FILE,
            JsonSerializableBlockModel.class).get();
        BlockModel blockModel = dataFromFile.toModelType();
        assertEquals(blockModel.getCurrentlyOpenPath(), AbsolutePath.fromString("/Y2S2"));
    }

    @Test
    public void toModelType_blankDataInvalidPathBlockFile_defaultFirstRootChild() throws Exception {
        JsonSerializableBlockModel dataFromFile = JsonUtil.readJsonFile(BLANK_DATA_INVALID_PATH_BLOCK_FILE,
            JsonSerializableBlockModel.class).get();
        BlockModel blockModel = dataFromFile.toModelType();
        assertEquals(blockModel.getCurrentlyOpenPath(), AbsolutePath.fromString("/"));
    }
}
