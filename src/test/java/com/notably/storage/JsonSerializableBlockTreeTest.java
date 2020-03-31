package com.notably.storage;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.notably.commons.exceptions.IllegalValueException;
import com.notably.commons.util.JsonUtil;
import com.notably.model.block.BlockTree;
import com.notably.testutil.TypicalBlockTree;

public class JsonSerializableBlockTreeTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableBlockTreeTest");
    private static final Path TYPICAL_BLOCKS_FILE = TEST_DATA_FOLDER.resolve("typicalBlockTree.json");
    private static final Path INVALID_BLOCK_FILE = TEST_DATA_FOLDER.resolve("invalidBlockTree.json");
    private static final Path DUPLICATE_BLOCK_FILE = TEST_DATA_FOLDER.resolve("duplicateBlockTree.json");

    @Test
    public void toModelType_typicalBlocksFile_success() throws Exception {
        JsonSerializableBlockTree dataFromFile = JsonUtil.readJsonFile(TYPICAL_BLOCKS_FILE,
            JsonSerializableBlockTree.class).get();
        BlockTree blockTreeFromFile = dataFromFile.toModelType();
        BlockTree typicalBlockTree = TypicalBlockTree.getTypicalBlockTree();
        assertEquals(blockTreeFromFile, typicalBlockTree);
    }

    @Test
    public void toModelType_invalidBlockFile_throwsIllegalValueException() throws Exception {
        JsonSerializableBlockTree dataFromFile = JsonUtil.readJsonFile(INVALID_BLOCK_FILE,
            JsonSerializableBlockTree.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateBlock_throwsIllegalValueException() throws Exception {
        JsonSerializableBlockTree dataFromFile = JsonUtil.readJsonFile(DUPLICATE_BLOCK_FILE,
            JsonSerializableBlockTree.class).get();
        assertThrows(IllegalValueException.class, JsonAdaptedBlockTreeItem.MESSAGE_DUPLICATE_BLOCK_CHILD,
            dataFromFile::toModelType);
    }
}
