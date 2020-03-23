package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.returnOrder.ReturnOrderBook;
import seedu.address.testutil.TypicalOrders;

public class JsonSerializableReturnOrderBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableReturnOrderBookTest");
    private static final Path TYPICAL_ORDER_FILE = TEST_DATA_FOLDER.resolve("typicalOrdersReturnOrderBook.json");
    private static final Path INVALID_ORDER_FILE = TEST_DATA_FOLDER.resolve("invalidOrderReturnOrderBook.json");
    private static final Path DUPLICATE_ORDER_FILE = TEST_DATA_FOLDER.resolve("duplicateOrderReturnOrderBook.json");

    @Test
    public void toModelType_typicalReturnOrdersFile_success() throws Exception {
        JsonSerializableReturnOrderBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ORDER_FILE,
                JsonSerializableReturnOrderBook.class).get();
        ReturnOrderBook returnOrderBookFromFile = dataFromFile.toModelType();
        ReturnOrderBook typicalOrdersReturnOrderBook = TypicalOrders.getTypicalReturnOrderBook();
        assertEquals(returnOrderBookFromFile, typicalOrdersReturnOrderBook);
    }

    @Test
    public void toModelType_invalidOrderFile_throwsIllegalValueException() throws Exception {
        JsonSerializableReturnOrderBook dataFromFile = JsonUtil.readJsonFile(INVALID_ORDER_FILE,
                JsonSerializableReturnOrderBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateOrders_throwsIllegalValueException() throws Exception {
        JsonSerializableReturnOrderBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ORDER_FILE,
                JsonSerializableReturnOrderBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableReturnOrderBook.MESSAGE_DUPLICATE_RETURN_ORDER,
                dataFromFile::toModelType);
    }

}
