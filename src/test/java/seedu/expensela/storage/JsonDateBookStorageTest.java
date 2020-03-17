package seedu.expensela.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalPersons.ALICE;
import static seedu.expensela.testutil.TypicalPersons.HOON;
import static seedu.expensela.testutil.TypicalPersons.IDA;
import static seedu.expensela.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.expensela.commons.exceptions.DataConversionException;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.ReadOnlyExpenseLa;

public class JsonDateBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readExpenseLa(null));
    }

    private java.util.Optional<ReadOnlyExpenseLa> readExpenseLa(String filePath) throws Exception {
        return new JsonExpenseLaStorage(Paths.get(filePath)).readExpenseLa(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readExpenseLa("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readExpenseLa("notJsonFormatExpenseLa.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, (
        ) -> readExpenseLa("invalidTransactionExpenseLa.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, (
        ) -> readExpenseLa("invalidAndValidTransactionExpenseLa.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        ExpenseLa original = getTypicalAddressBook();
        JsonExpenseLaStorage jsonAddressBookStorage = new JsonExpenseLaStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveExpenseLa(original, filePath);
        ReadOnlyExpenseLa readBack = jsonAddressBookStorage.readExpenseLa(filePath).get();
        assertEquals(original, new ExpenseLa(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonAddressBookStorage.saveExpenseLa(original, filePath);
        readBack = jsonAddressBookStorage.readExpenseLa(filePath).get();
        assertEquals(original, new ExpenseLa(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonAddressBookStorage.saveExpenseLa(original); // file path not specified
        readBack = jsonAddressBookStorage.readExpenseLa().get(); // file path not specified
        assertEquals(original, new ExpenseLa(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyExpenseLa addressBook, String filePath) {
        try {
            new JsonExpenseLaStorage(Paths.get(filePath))
                    .saveExpenseLa(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new ExpenseLa(), null));
    }
}
