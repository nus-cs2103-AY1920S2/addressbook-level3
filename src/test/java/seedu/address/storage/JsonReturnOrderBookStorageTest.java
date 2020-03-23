package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ALICE_RETURN;
import static seedu.address.testutil.TypicalOrders.HOON_RETURN;
import static seedu.address.testutil.TypicalOrders.IDA_RETURN;
import static seedu.address.testutil.TypicalOrders.getTypicalReturnOrderBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.returnorder.ReadOnlyReturnOrderBook;
import seedu.address.model.returnorder.ReturnOrderBook;

public class JsonReturnOrderBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonReturnOrderBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readReturnOrderBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readReturnOrderBook(null));
    }

    private java.util.Optional<ReadOnlyReturnOrderBook> readReturnOrderBook(String filePath) throws Exception {
        return new JsonReturnOrderBookStorage(Paths.get(filePath))
                .readReturnOrderBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readReturnOrderBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readReturnOrderBook("notJsonFormatReturnOrderBook.json"));
    }

    @Test
    public void readReturnOrderBook_invalidOrderReturnOrderBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readReturnOrderBook("invalidOrderReturnOrderBook.json"));
    }

    @Test
    public void readAndSaveOrderBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempOrderBook.json");
        ReturnOrderBook original = getTypicalReturnOrderBook();
        JsonReturnOrderBookStorage jsonReturnOrderBookStorage = new JsonReturnOrderBookStorage(filePath);

        // Save in new file and read back
        jsonReturnOrderBookStorage.saveReturnOrderBook(original, filePath);
        ReadOnlyReturnOrderBook readBack = jsonReturnOrderBookStorage.readReturnOrderBook(filePath).get();
        assertEquals(original, new ReturnOrderBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addReturnOrder(HOON_RETURN);
        original.removeReturnOrder(ALICE_RETURN);
        jsonReturnOrderBookStorage.saveReturnOrderBook(original, filePath);
        readBack = jsonReturnOrderBookStorage.readReturnOrderBook(filePath).get();
        assertEquals(original, new ReturnOrderBook(readBack));

        // Save and read without specifying file path
        original.addReturnOrder(IDA_RETURN);
        jsonReturnOrderBookStorage.saveReturnOrderBook(original); // file path not specified
        readBack = jsonReturnOrderBookStorage.readReturnOrderBook().get(); // file path not specified
        assertEquals(original, new ReturnOrderBook(readBack));

    }

    @Test
    public void saveReturnOrderBook_nullReturnOrderBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveReturnOrderBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code returnOrderBook} at the specified {@code filePath}.
     */
    private void saveReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook, String filePath) {
        try {
            new JsonReturnOrderBookStorage(Paths.get(filePath))
                    .saveReturnOrderBook(returnOrderBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }


    @Test
    public void saveReturnOrderBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveReturnOrderBook(new ReturnOrderBook(), null));
    }
}
