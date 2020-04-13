package seedu.eylah.expensesplitter.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.commons.util.JsonUtil;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.testutil.TypicalPersons;

public class JsonSerializablePersonAmountBookTest {

    private static final Path TEST_DATA_FOLDER = Paths
        .get("src", "test", "data", "JsonSerializablePersonAmountBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAmountBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAmountBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAmountBook.json");


    @Test
    public void toModelType_typicalPersonAmountFile_success() throws Exception {
        JsonSerializablePersonAmountBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
            JsonSerializablePersonAmountBook.class).get();
        PersonAmountBook personAmountBookFromFile = dataFromFile.toModelType();
        PersonAmountBook typicalPersonAmountBook = TypicalPersons.getTypicalPersonAmountBook();
        assertEquals(personAmountBookFromFile, typicalPersonAmountBook);
    }




    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePersonAmountBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
            JsonSerializablePersonAmountBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializablePersonAmountBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
            JsonSerializablePersonAmountBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializablePersonAmountBook.MESSAGE_DUPLICATE_PERSON,
            dataFromFile::toModelType);
    }


}
