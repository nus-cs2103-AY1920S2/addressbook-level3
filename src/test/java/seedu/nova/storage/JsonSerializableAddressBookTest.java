package seedu.nova.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.nova.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.nova.commons.exceptions.IllegalValueException;
import seedu.nova.commons.util.JsonUtil;
import seedu.nova.model.VersionedAddressBook;
import seedu.nova.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableNova dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableNova.class).get();
        VersionedAddressBook addressBookFromFile = dataFromFile.toModelTypeAb();
        VersionedAddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalVersionedAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableNova dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableNova.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelTypeAb);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableNova dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableNova.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableNova.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
