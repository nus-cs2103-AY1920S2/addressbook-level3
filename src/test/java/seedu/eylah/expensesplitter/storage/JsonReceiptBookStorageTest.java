package seedu.eylah.expensesplitter.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.eylah.expensesplitter.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;

public class JsonReceiptBookStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonReceiptBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readReceiptBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readReceiptBook(null));
    }

    private java.util.Optional<ReadOnlyReceiptBook> readReceiptBook(String filePath) throws Exception {
        return new JsonReceiptBookStorage(Paths.get(filePath))
                .readReceiptBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readReceiptBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () ->
                readReceiptBook("notJsonFormatReceiptBook.json"));
    }


    @Test
    public void readReceiptBook_invalidReceiptBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readReceiptBook("invalidReceiptBook.json"));
    }

    @Test
    public void readReceiptBook_invalidAndValidReceiptBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () ->
                readReceiptBook("invalidAndValidReceiptBook.json"));
    }




}
