package team.easytravel.storage.packinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.TypicalPackingListItem.PACKING_LIST_CONDITIONER;
import static team.easytravel.testutil.TypicalPackingListItem.PACKING_LIST_SANDALS;
import static team.easytravel.testutil.TypicalPackingListItem.PACKING_LIST_SHAVER;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import team.easytravel.commons.exceptions.DataConversionException;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.model.listmanagers.ReadOnlyActivityManager;
import team.easytravel.model.listmanagers.ReadOnlyPackingListManager;
import team.easytravel.storage.activity.JsonActivityStorage;
import team.easytravel.testutil.TypicalPackingListItem;

class JsonPackingListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonPackingListItemStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPackingListItem_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPackingListItemManager(null));
    }

    private Optional<ReadOnlyActivityManager> readPackingListItemManager(String filePath) throws Exception {
        return new JsonActivityStorage(Paths.get(filePath)).readActivityManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPackingListItemManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPackingListItemManager(
                "notJsonFormatPackingListItemManager.json"));
    }

    @Test
    public void readPackingListManager_invalidPackingListManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPackingListItemManager(
                "invalidPackingListItemManager.json"));
    }

    @Test
    public void readPackingListManager_invalidAndValidPackingListManager_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readPackingListItemManager(
                "invalidAndValidPackingListItemManager.json"));
    }

    @Test
    public void readAndSavePackingListManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPackingListItemManager.json");
        PackingListManager original = TypicalPackingListItem.getTypicalPackingListManager();
        JsonPackingListStorage jsonPackingListStorage = new JsonPackingListStorage(filePath);

        //Save in new file and read back
        jsonPackingListStorage.savePackingList(original, filePath);
        ReadOnlyPackingListManager readBack = jsonPackingListStorage.readPackingList(filePath).get();
        assertEquals(original, new PackingListManager(readBack));

        //Modify data, overwrite exiting file and read back
        original.addPackingListItem(PACKING_LIST_SANDALS);
        original.removePackingListItem(PACKING_LIST_CONDITIONER);
        jsonPackingListStorage.savePackingList(original, filePath);
        readBack = jsonPackingListStorage.readPackingList(filePath).get();
        assertEquals(original, new PackingListManager(readBack));

        // Save and read without specifying file path
        original.addPackingListItem(PACKING_LIST_SHAVER);
        jsonPackingListStorage.savePackingList(original);
        readBack = jsonPackingListStorage.readPackingList().get();
        assertEquals(original, new PackingListManager(readBack));
    }

    @Test
    public void savePackingListManager_nullPackingListManager_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePackingListManager(
                null, "SomeFile.json"));
    }

    /**
     * Saves {@code packingListManager} at specified {@code filePath}.
     */
    private void savePackingListManager(ReadOnlyPackingListManager packingListManager, String filePath) {
        try {
            new JsonPackingListStorage(Paths.get(filePath))
                    .savePackingList(packingListManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file ", ioe);
        }
    }

    @Test
    public void savePackingListManager_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePackingListManager(new PackingListManager(), null));
    }
}
