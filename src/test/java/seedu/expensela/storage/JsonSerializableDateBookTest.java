package seedu.expensela.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.expensela.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.expensela.commons.exceptions.IllegalValueException;
import seedu.expensela.commons.util.JsonUtil;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.testutil.TypicalPersons;

public class JsonSerializableDateBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalTransactionsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidTransactionAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateTransactionAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        ExpenseLa expenseLaFromFile = dataFromFile.toModelType();
        ExpenseLa typicalPersonsExpenseLa = TypicalPersons.getTypicalAddressBook();
        assertEquals(expenseLaFromFile, typicalPersonsExpenseLa);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
