package com.notably.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.notably.commons.GuiSettings;
import com.notably.model.block.BlockTree;
import com.notably.model.userpref.UserPrefModel;
import com.notably.model.userpref.UserPrefModelImpl;
import com.notably.testutil.TypicalBlockTree;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonBlockStorage blockStorage = new JsonBlockStorage(getTempFilePath("blocks"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(blockStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefModel original = new UserPrefModelImpl();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefModel retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void blockReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonBlockStorage} class.
         * More extensive testing of BlockTree saving/reading is done in {@link JsonBlockStorageTest} class.
         */
        BlockTree original = TypicalBlockTree.getTypicalBlockTree();
        storageManager.saveBlockTree(original);
        BlockTree retrieved = storageManager.readBlockTree().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void getBlockTreeFilePath() {
        assertNotNull(storageManager.getBlockTreeFilePath());
    }
}
