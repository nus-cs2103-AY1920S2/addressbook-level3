package seedu.delino.storage.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.delino.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.delino.commons.exceptions.IllegalValueException;
import seedu.delino.commons.util.JsonUtil;
import seedu.delino.model.OrderBook;
import seedu.delino.testutil.TypicalOrders;

public class JsonSerializableOrderBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableOrderBookTest");
    private static final Path TYPICAL_ORDER_FILE = TEST_DATA_FOLDER.resolve("typicalOrdersOrderBook.json");
    private static final Path INVALID_ORDER_FILE = TEST_DATA_FOLDER.resolve("invalidOrderBook.json");
    private static final Path DUPLICATE_ORDER_FILE = TEST_DATA_FOLDER.resolve("duplicateOrdersOrderBook.json");

    @Test
    public void toModelType_typicalOrdersFile_success() throws Exception {
        JsonSerializableOrderBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ORDER_FILE,
                JsonSerializableOrderBook.class).get();
        OrderBook deliveryOrderBookFromFile = dataFromFile.toModelType();
        OrderBook typicalDeliveryOrderBook = TypicalOrders.getTypicalOrderBook();
        assertEquals(deliveryOrderBookFromFile, typicalDeliveryOrderBook);
    }

    @Test
    public void toModelType_invalidOrderFile_throwsIllegalValueException() throws Exception {
        JsonSerializableOrderBook dataFromFile = JsonUtil.readJsonFile(INVALID_ORDER_FILE,
                JsonSerializableOrderBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateOrders_throwsIllegalValueException() throws Exception {
        JsonSerializableOrderBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ORDER_FILE,
                JsonSerializableOrderBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableOrderBook.MESSAGE_DUPLICATE_ORDER,
                dataFromFile::toModelType);
    }

}
