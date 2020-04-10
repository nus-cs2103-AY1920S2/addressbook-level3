package team.easytravel.storage.packinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.commons.util.JsonUtil;
import team.easytravel.model.listmanagers.PackingListManager;
import team.easytravel.testutil.TypicalPackingListItem;

class JsonSerializablePackingListManagerTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "JsonSerializablePackingListManagerTest");
    private static final Path TYPICAL_ITEM_FILE = TEST_DATA_FOLDER.resolve("typicalPackingListManager.json");
    private static final Path INVALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("invalidPackingListManager.json");
    private static final Path DUPLICATE_ITEM_FILE = TEST_DATA_FOLDER.resolve("duplicatePackingListManager.json");


    @Test
    public void toModelType_typicalPackingListItemFile_success() throws Exception {
        JsonSerializablePackingListManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_ITEM_FILE,
                JsonSerializablePackingListManager.class).get();

        PackingListManager packingListManagerFromFile = dataFromFile.toModelType();
        PackingListManager typicalPackingListManager = TypicalPackingListItem.getTypicalPackingListManager();
        assertEquals(packingListManagerFromFile, typicalPackingListManager);
    }

    @Test
    public void toModelType_invalidPackingListItemFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePackingListManager dataFromFile = JsonUtil.readJsonFile(INVALID_ITEM_FILE,
                JsonSerializablePackingListManager.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePackingListItems_throwsIllegalValueException() throws Exception {
        JsonSerializablePackingListManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ITEM_FILE,
                JsonSerializablePackingListManager.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePackingListManager.MESSAGE_DUPLICATE_ITEM,
                dataFromFile::toModelType);
    }

}
