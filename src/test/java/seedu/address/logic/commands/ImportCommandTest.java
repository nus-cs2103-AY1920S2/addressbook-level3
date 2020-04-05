package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ImportCommand.PROCESS_FAILED_MESSAGE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.CsvUtil.ADDTIONAL_NAME_CSV_ORDER_AMY;
import static seedu.address.testutil.CsvUtil.ADDTIONAL_NAME_CSV_RETURN_ORDER_AMY;
import static seedu.address.testutil.CsvUtil.INVALID_CSV_ORDER_AMY;
import static seedu.address.testutil.CsvUtil.INVALID_CSV_ORDER_BOB;
import static seedu.address.testutil.CsvUtil.INVALID_CSV_RETURN_ORDER_AMY;
import static seedu.address.testutil.CsvUtil.INVALID_CSV_RETURN_ORDER_BOB;
import static seedu.address.testutil.CsvUtil.VALID_CSV_ORDER_AMY;
import static seedu.address.testutil.CsvUtil.VALID_CSV_ORDER_BOB;
import static seedu.address.testutil.CsvUtil.VALID_CSV_RETURN_ORDER_AMY;
import static seedu.address.testutil.CsvUtil.VALID_CSV_RETURN_ORDER_BOB;
import static seedu.address.testutil.CsvUtil.VALID_WHITESPACE_IN_BETWEEN_ORDER_BOB;
import static seedu.address.testutil.CsvUtil.VALID_WHITESPACE_IN_BETWEEN_RETURN_ORDER_BOB;
import static seedu.address.testutil.TypicalOrders.ADDITIONAL_NAME_AMY;
import static seedu.address.testutil.TypicalOrders.AMY;
import static seedu.address.testutil.TypicalOrders.BOB;
import static seedu.address.testutil.TypicalReturnOrders.ADDITIONAL_NAME_AMY_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.AMY_RETURN;
import static seedu.address.testutil.TypicalReturnOrders.BOB_RETURN;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OrderBook;
import seedu.address.model.ReturnOrderBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.CsvProcessor;

class ImportCommandTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "CsvFileTest");
    private static final Path VALID_CSV_FILE = TEST_DATA_FOLDER.resolve("validCsvFile.csv");
    private Model model;

    @BeforeEach
    public void setup() {
        model = new ModelManager(new OrderBook(), new ReturnOrderBook(), new UserPrefs());
    }


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ImportCommand(null));
    }

    @Test
    public void constructor_emptyList_returnCorrectMessage() {
        String expectedMessage = "0 delivery order(s) and 0 return order(s) being imported.\n";
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        assertCommandSuccess(new ImportCommand(new ArrayList<>()), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allValidReturnOrderFormat_returnCorrectCounter() {
        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());

        expectedModel.addReturnOrder(AMY_RETURN);
        expectedModel.addReturnOrder(BOB_RETURN);
        expectedModel.addReturnOrder(ADDITIONAL_NAME_AMY_RETURN);

        List<String> dataRetrieved = new ArrayList<>();
        dataRetrieved.add(VALID_CSV_RETURN_ORDER_AMY);
        dataRetrieved.add(VALID_WHITESPACE_IN_BETWEEN_RETURN_ORDER_BOB);
        // Extra Bob name after Amy name
        dataRetrieved.add(ADDTIONAL_NAME_CSV_RETURN_ORDER_AMY);

        ImportCommand importCommand = new ImportCommand(dataRetrieved);
        HashMap<Integer, String> errorMessages = new HashMap<>();

        String expectedMessage = ImportCommand.printResult(0, dataRetrieved.size(), 0, 0, errorMessages);

        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allValidDeliveryOrderFormat_returnCorrectCounter() {
        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());

        expectedModel.addOrder(AMY);
        expectedModel.addOrder(BOB);
        expectedModel.addOrder(ADDITIONAL_NAME_AMY);

        List<String> dataRetrieved = new ArrayList<>();
        dataRetrieved.add(VALID_CSV_ORDER_AMY);
        dataRetrieved.add(VALID_WHITESPACE_IN_BETWEEN_ORDER_BOB);
        // Extra Bob name after Amy name
        dataRetrieved.add(ADDTIONAL_NAME_CSV_ORDER_AMY);

        ImportCommand importCommand = new ImportCommand(dataRetrieved);
        HashMap<Integer, String> errorMessages = new HashMap<>();

        String expectedMessage = ImportCommand.printResult(dataRetrieved.size(), 0, 0, 0, errorMessages);

        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allValidDeliveryAndReturnOrderFormat_returnCorrectCounter() {
        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());

        expectedModel.addOrder(AMY);
        expectedModel.addReturnOrder(AMY_RETURN);
        expectedModel.addOrder(BOB);
        expectedModel.addReturnOrder(BOB_RETURN);

        List<String> dataRetrieved = new ArrayList<>();
        dataRetrieved.add(VALID_CSV_ORDER_AMY);
        dataRetrieved.add(VALID_CSV_RETURN_ORDER_AMY);
        dataRetrieved.add(VALID_CSV_ORDER_BOB);
        dataRetrieved.add(VALID_CSV_RETURN_ORDER_BOB);
        ImportCommand importCommand = new ImportCommand(dataRetrieved);

        String expectedMessage = "2 delivery order(s) and 2 return order(s) being imported.\n";

        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allInvalidDeliveryOrderFormat_returnErrorMessage() {
        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());

        List<String> dataRetrieved = new ArrayList<>();
        dataRetrieved.add(INVALID_CSV_ORDER_AMY);
        dataRetrieved.add(INVALID_CSV_ORDER_BOB);
        ImportCommand importCommand = new ImportCommand(dataRetrieved);
        HashMap<Integer, String> errorMessages = new HashMap<>();
        errorMessages.put(1, PROCESS_FAILED_MESSAGE + INVALID_CSV_ORDER_AMY);
        errorMessages.put(2, PROCESS_FAILED_MESSAGE + INVALID_CSV_ORDER_BOB);

        String expectedMessage = ImportCommand.printResult(0, 0, 0, dataRetrieved.size(), errorMessages);

        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allInvalidReturnOrderFormat_returnErrorMessage() {
        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());

        List<String> dataRetrieved = new ArrayList<>();
        dataRetrieved.add(INVALID_CSV_RETURN_ORDER_AMY);
        dataRetrieved.add(INVALID_CSV_RETURN_ORDER_BOB);

        ImportCommand importCommand = new ImportCommand(dataRetrieved);
        HashMap<Integer, String> errorMessages = new HashMap<>();
        errorMessages.put(1, PROCESS_FAILED_MESSAGE + INVALID_CSV_RETURN_ORDER_AMY);
        errorMessages.put(2, PROCESS_FAILED_MESSAGE + INVALID_CSV_RETURN_ORDER_BOB);

        String expectedMessage = ImportCommand.printResult(0, 0, 0, dataRetrieved.size(), errorMessages);

        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateDeliveryOrder_returnErrorMessage() {
        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());
        expectedModel.addOrder(AMY);

        List<String> dataRetrieved = new ArrayList<>();
        dataRetrieved.add(VALID_CSV_ORDER_AMY);
        dataRetrieved.add(VALID_CSV_ORDER_AMY);

        ImportCommand importCommand = new ImportCommand(dataRetrieved);
        HashMap<Integer, String> errorMessages = new HashMap<>();

        String expectedMessage = ImportCommand.printResult(1, 0, 1, 0, errorMessages);

        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_addDuplicateReturnOrder_returnErrorMessage() {
        ModelManager expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(),
                new UserPrefs());
        expectedModel.addReturnOrder(AMY_RETURN);

        List<String> dataRetrieved = new ArrayList<>();
        dataRetrieved.add(VALID_CSV_RETURN_ORDER_AMY);
        dataRetrieved.add(VALID_CSV_RETURN_ORDER_AMY);

        ImportCommand importCommand = new ImportCommand(dataRetrieved);
        HashMap<Integer, String> errorMessages = new HashMap<>();

        String expectedMessage = ImportCommand.printResult(0, 1, 1, 0, errorMessages);

        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() throws Exception {
        List<String> sentence = new ArrayList<>();
        sentence.add("order,tid/1023456789,n/Amwos Cheong,a/Blk 572 Hougang st 51 #11-37 S530572,p/90010019,"
                + "dts/2020-03-10 1650,w/Marsiling,cod/$9.50,c/Leave it at the riser,type/glass");
        ImportCommand expectedCommand = new ImportCommand(sentence);
        assertTrue(new ImportCommand(CsvProcessor.retrieveData(VALID_CSV_FILE)).equals(expectedCommand));
    }
}
