package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.OrderBook;
import seedu.address.testutil.TypicalOrders;

public class JsonSerializableDeliveryOrderBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableDeliveryOrderBookTest");
    private static final Path TYPICAL_ORDER_FILE = TEST_DATA_FOLDER.resolve("typicalOrdersDeliveryOrderBook.json");
    private static final Path INVALID_ORDER_FILE = TEST_DATA_FOLDER.resolve("invalidOrderDeliveryOrderBook.json");
    private static final Path DUPLICATE_ORDER_FILE = TEST_DATA_FOLDER.resolve("duplicateOrderDeliveryOrderBook.json");

    @Test
    public void toModelType_typicalOrdersFile_success() throws Exception {
        JsonSerializableDeliveryOrderBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ORDER_FILE,
                JsonSerializableDeliveryOrderBook.class).get();
        OrderBook deliveryOrderBookFromFile = dataFromFile.toModelType();
        OrderBook typicalDeliveryOrderBook = TypicalOrders.getTypicalOrderBook();
        assertEquals(deliveryOrderBookFromFile, typicalDeliveryOrderBook);
    }

    @Test
    public void toModelType_invalidOrderFile_throwsIllegalValueException() throws Exception {
        JsonSerializableDeliveryOrderBook dataFromFile = JsonUtil.readJsonFile(INVALID_ORDER_FILE,
                JsonSerializableDeliveryOrderBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateOrders_throwsIllegalValueException() throws Exception {
        JsonSerializableDeliveryOrderBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ORDER_FILE,
                JsonSerializableDeliveryOrderBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableDeliveryOrderBook.MESSAGE_DUPLICATE_ORDER,
                dataFromFile::toModelType);
    }

}
