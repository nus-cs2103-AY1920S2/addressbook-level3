package seedu.eylah.expensesplitter.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.eylah.expensesplitter.testutil.Assert.assertThrows;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.ALICE;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.HOON;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.IDA;
import static seedu.eylah.expensesplitter.testutil.TypicalPersons.getTypicalPersonAmountBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.expensesplitter.model.PersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;

public class JsonPersonAmountBookSplitterStorageTest {

    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonPersonAmountBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPersonAmountBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPersonAmountBook(null));
    }

    private java.util.Optional<ReadOnlyPersonAmountBook> readPersonAmountBook(String filePath) throws Exception {
        return new JsonPersonAmountBookStorage(Paths.get(filePath))
            .readPersonAmountBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPersonAmountBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
            readPersonAmountBook("notJsonFormatPersonAmountBook.json"));
    }


    @Test
    public void readPersonAmountBook_invalidPersonAmountBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readPersonAmountBook("invalidPersonAmountBook.json"));
    }

    @Test
    public void readPersonAmountBook_invalidAndValidPersonAmountBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
            readPersonAmountBook("invalidAndValidPersonAmountBook.json"));
    }

    @Test
    public void readAndSavePersonAmountBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPersonAmountBook.json");
        PersonAmountBook original = getTypicalPersonAmountBook();
        JsonPersonAmountBookStorage jsonPersonAmountBookStorage = new JsonPersonAmountBookStorage(filePath);

        // Save in new file and read back
        jsonPersonAmountBookStorage.savePersonAmountBook(original, filePath);
        ReadOnlyPersonAmountBook readBack = jsonPersonAmountBookStorage.readPersonAmountBook(filePath).get();
        assertEquals(original, new PersonAmountBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonPersonAmountBookStorage.savePersonAmountBook(original, filePath);
        readBack = jsonPersonAmountBookStorage.readPersonAmountBook(filePath).get();
        assertEquals(original, new PersonAmountBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonPersonAmountBookStorage.savePersonAmountBook(original); // file path not specified
        readBack = jsonPersonAmountBookStorage.readPersonAmountBook().get(); // file path not specified
        assertEquals(original, new PersonAmountBook(readBack));

    }

    @Test
    public void savePesonAmountBook_nullPersonAmountBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
            savePersonAmountBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook, String filePath) {
        try {
            new JsonPersonAmountBookStorage(Paths.get(filePath))
                .savePersonAmountBook(personAmountBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePersonAmountBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePersonAmountBook(new PersonAmountBook(), null));
    }



}
