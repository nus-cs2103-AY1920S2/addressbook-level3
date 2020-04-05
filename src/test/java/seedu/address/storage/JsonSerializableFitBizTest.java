package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.FitBiz;
import seedu.address.testutil.TypicalClients;

public class JsonSerializableFitBizTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFitBizTest");
    private static final Path TYPICAL_CLIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalClientsFitBiz.json");
    private static final Path INVALID_CLIENT_FILE = TEST_DATA_FOLDER.resolve("invalidClientFitBiz.json");
    private static final Path DUPLICATE_CLIENT_FILE = TEST_DATA_FOLDER.resolve("duplicateClientFitBiz.json");

    @Test
    public void toModelType_typicalClientsFile_success() throws Exception {
        JsonSerializableFitBiz dataFromFile = JsonUtil.readJsonFile(TYPICAL_CLIENTS_FILE,
                JsonSerializableFitBiz.class).get();
        FitBiz fitBizFromFile = dataFromFile.toModelType();
        FitBiz typicalClientsFitBiz = TypicalClients.getTypicalAddressBook();
        assertEquals(fitBizFromFile, typicalClientsFitBiz);
    }

    @Test
    public void toModelType_invalidClientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFitBiz dataFromFile = JsonUtil.readJsonFile(INVALID_CLIENT_FILE,
                JsonSerializableFitBiz.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateClients_throwsIllegalValueException() throws Exception {
        JsonSerializableFitBiz dataFromFile = JsonUtil.readJsonFile(DUPLICATE_CLIENT_FILE,
                JsonSerializableFitBiz.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableFitBiz.MESSAGE_DUPLICATE_CLIENT,
                dataFromFile::toModelType);
    }

}
