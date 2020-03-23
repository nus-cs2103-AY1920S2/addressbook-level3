package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ALICE;
import static seedu.address.testutil.TypicalOrders.HOON;
import static seedu.address.testutil.TypicalOrders.IDA;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;

public class JsonOrderBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonOrderBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readOrderBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readOrderBook(null));
    }

    private java.util.Optional<ReadOnlyOrderBook> readOrderBook(String filePath) throws Exception {
        return new JsonOrderBookStorage(Paths.get(filePath)).readOrderBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readOrderBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readOrderBook("notJsonFormatOrderBook.json"));
    }

    @Test
    public void readOrderBook_invalidOrderDeliveryOrderBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readOrderBook("invalidOrderDeliveryOrderBook.json"));
    }

    @Test
    public void readAndSaveOrderBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempOrderBook.json");
        OrderBook original = getTypicalOrderBook();
        JsonOrderBookStorage jsonOrderBookStorage = new JsonOrderBookStorage(filePath);

        // Save in new file and read back
        jsonOrderBookStorage.saveOrderBook(original, filePath);
        ReadOnlyOrderBook readBack = jsonOrderBookStorage.readOrderBook(filePath).get();
        assertEquals(original, new OrderBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addOrder(HOON);
        original.removeOrder(ALICE);
        jsonOrderBookStorage.saveOrderBook(original, filePath);
        readBack = jsonOrderBookStorage.readOrderBook(filePath).get();
        assertEquals(original, new OrderBook(readBack));

        // Save and read without specifying file path
        original.addOrder(IDA);
        jsonOrderBookStorage.saveOrderBook(original); // file path not specified
        readBack = jsonOrderBookStorage.readOrderBook().get(); // file path not specified
        assertEquals(original, new OrderBook(readBack));

    }

    @Test
    public void saveOrderBook_nullOrderBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveOrderBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code orderBook} at the specified {@code filePath}.
     */
    private void saveOrderBook(ReadOnlyOrderBook orderBook, String filePath) {
        try {
            new JsonOrderBookStorage(Paths.get(filePath))
                    .saveOrderBook(orderBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }


    @Test
    public void saveOrderBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveOrderBook(new OrderBook(), null));
    }
}
