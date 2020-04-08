package nasa.storage;

import static nasa.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import nasa.commons.exceptions.IllegalValueException;
import nasa.commons.util.JsonUtil;


public class JsonSerializableNasaBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableNasaBookTest");
    private static final Path TYPICAL_MODULES_FILE = TEST_DATA_FOLDER.resolve("typicalModulesNasaBook.json");
    private static final Path INVALID_MODULE_FILE = TEST_DATA_FOLDER.resolve("invalidModuleNasaBook.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleNasaBook.json");

    /*
    @Test
    public void toModelType_typicalModulesFile_success() throws Exception {
        JsonSerializableNasaBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULES_FILE,
                JsonSerializableNasaBook.class).get();
        NasaBook nasaBookFromFile = dataFromFile.toModelType();
        NasaBook typicalModulesNasaBook = TypicalModules.getTypicalNasaBook();
        assertEquals(nasaBookFromFile, typicalModulesNasaBook);
    }

     */

    @Test
    public void toModelType_invalidModuleFile_throwsIllegalValueException() throws Exception {
        JsonSerializableNasaBook dataFromFile = JsonUtil.readJsonFile(INVALID_MODULE_FILE,
                JsonSerializableNasaBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        JsonSerializableNasaBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableNasaBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableNasaBook.MESSAGE_DUPLICATE_MODULE,
                dataFromFile::toModelType);
    }

}
