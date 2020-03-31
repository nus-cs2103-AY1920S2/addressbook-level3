package com.notably.storage;

import static com.notably.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.notably.commons.exceptions.DataConversionException;
import com.notably.commons.path.AbsolutePath;
import com.notably.model.block.BlockTree;
import com.notably.model.block.BlockTreeImpl;
import com.notably.testutil.TypicalBlockTree;

public class JsonBlockStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonBlockStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readBlockTree_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readBlockTree(null));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    private java.util.Optional<BlockTree> readBlockTree(String filePath) throws Exception {
        return new JsonBlockStorage(Paths.get(filePath)).readBlockTree(addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Saves the {@code BlockTree} at the specified path.
     *
     * @param blockTree
     * @param filePath
     */
    private void saveBlockTree(BlockTree blockTree, String filePath) {
        try {
            new JsonBlockStorage(Paths.get(filePath))
                    .saveBlockTree(blockTree, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBlockTree("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readBlockTree("notJsonFormatBlockTree.json"));
    }

    @Test
    public void readBlockTree_invalidBlockTree_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBlockTree("invalidBlockTree.json"));
    }

    @Test
    public void readBlockTree_invalidAndValidBlockTree_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readBlockTree("invalidAndValidBlockTree.json"));
    }

    @Test
    public void readAndSaveBlockTree_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempBlockTree.json");
        BlockTree original = TypicalBlockTree.getTypicalBlockTree();
        JsonBlockStorage jsonBlockStorage = new JsonBlockStorage(filePath);

        // Save in new file and read back
        jsonBlockStorage.saveBlockTree(original);
        BlockTree readBack = jsonBlockStorage.readBlockTree(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.add(AbsolutePath.fromString("/Y2S2"), TypicalBlockTree.CS2107);
        jsonBlockStorage.saveBlockTree(original, filePath);
        readBack = jsonBlockStorage.readBlockTree(filePath).get();
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.add(AbsolutePath.fromString("/Y2S2"), TypicalBlockTree.CS3230);
        jsonBlockStorage.saveBlockTree(original); // file path not specified
        readBack = jsonBlockStorage.readBlockTree().get(); // file path not specified
        assertEquals(original, readBack);
    }

    @Test
    public void saveBlockTree_nullBlockTree_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBlockTree(null, "SomeFile.json"));
    }

    @Test
    public void saveBlockTree_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveBlockTree(new BlockTreeImpl(), null));
    }
}
