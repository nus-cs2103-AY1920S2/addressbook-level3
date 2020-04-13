package seedu.eylah.expensesplitter.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.eylah.commons.exceptions.IllegalValueException;
import seedu.eylah.commons.util.JsonUtil;
import seedu.eylah.expensesplitter.model.ReceiptBook;
import seedu.eylah.expensesplitter.testutil.TypicalReceipt;

public class JsonSerializableReceiptBookTest {

    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "JsonSerializableReceiptBookTest");
    private static final Path TYPICAL_RECEIPT_FILE = TEST_DATA_FOLDER.resolve("typicalReceiptBook.json");
    private static final Path INVALID_RECEIPT_FILE = TEST_DATA_FOLDER.resolve("invalidReceiptBook.json");
    private static final Path DUPLICATE_RECEIPT_FILE = TEST_DATA_FOLDER.resolve("duplicateReceiptBook.json");

    @Test
    public void toModelType_typicalReceiptFile_success() throws Exception {
        JsonSerializableReceiptBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_RECEIPT_FILE,
                JsonSerializableReceiptBook.class).get();
        ReceiptBook receiptBookFromFile = dataFromFile.toModelType();
        ReceiptBook typicalReceiptBook = TypicalReceipt.getTypicalReceiptBook();
        assertEquals(receiptBookFromFile, typicalReceiptBook);
    }

    @Test
    public void toModelType_invalidReceiptFile_throwsIllegalValueException() throws Exception {
        JsonSerializableReceiptBook dataFromFile = JsonUtil.readJsonFile(INVALID_RECEIPT_FILE,
                JsonSerializableReceiptBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateReceipts_throwsIllegalValueException() throws Exception {
        JsonSerializableReceiptBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RECEIPT_FILE,
                JsonSerializableReceiptBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableReceiptBook.MESSAGE_DUPLICATE_RECEIPT,
                dataFromFile::toModelType);
    }
}
