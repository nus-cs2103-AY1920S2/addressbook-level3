package seedu.delino.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.delino.commons.core.Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX;
import static seedu.delino.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.delino.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.COD_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.TID_DESC_AMY;
import static seedu.delino.logic.commands.CommandTestUtil.WAREHOUSE_DESC_AMY;
import static seedu.delino.testutil.Assert.assertThrows;
import static seedu.delino.testutil.TypicalOrders.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.delino.logic.commands.CommandResult;
import seedu.delino.logic.commands.InsertCommand;
import seedu.delino.logic.commands.ListCommand;
import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.logic.parser.exceptions.ParseException;

import seedu.delino.model.Model;
import seedu.delino.model.ModelManager;
import seedu.delino.model.OrderBook;
import seedu.delino.model.ReadOnlyOrderBook;
import seedu.delino.model.ReadOnlyReturnOrderBook;
import seedu.delino.model.ReturnOrderBook;
import seedu.delino.model.UserPrefs;
import seedu.delino.model.parcel.order.Order;

import seedu.delino.storage.JsonOrderBookStorage;
import seedu.delino.storage.JsonReturnOrderBookStorage;
import seedu.delino.storage.JsonUserPrefsStorage;
import seedu.delino.storage.StorageManager;
import seedu.delino.testutil.OrderBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonOrderBookStorage orderBookStorage =
                new JsonOrderBookStorage(temporaryFolder.resolve("DeliveryOrderBook.json"));
        JsonReturnOrderBookStorage returnOrderBookStorage =
                new JsonReturnOrderBookStorage(temporaryFolder.resolve("ReturnOrderBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(orderBookStorage, returnOrderBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void getOrderBook_returnCorrectOrderBook_success() {
        assertEquals(new OrderBook(), logic.getOrderBook());
        assertEquals(new ReturnOrderBook(), logic.getReturnOrderBook());
    }

    @Test
    public void getOrderBookPath_returnDeliveryOrderBookPath_success() {
        assertEquals(new UserPrefs().getOrderBookFilePath(), logic.getOrderBookFilePath());
    }

    @Test
    public void getReturnOrderBookPath_returnReturnOrderBookPath_success() {
        assertEquals(new UserPrefs().getReturnOrderBookFilePath(), logic.getReturnOrderBookFilePath());
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete -o 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonOrderBookStorage deliveryOrderBookStorage =
                new JsonOrderBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionDeliveryOrderBook.json"));
        JsonReturnOrderBookStorage returnOrderBookStorage =
                new JsonReturnOrderBookIoExceptionThrowingStub(
                        temporaryFolder.resolve("ioExceptionReturnOrderBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(deliveryOrderBookStorage, returnOrderBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = InsertCommand.COMMAND_WORD + TID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DELIVERY_TIMESTAMP_DESC_AMY + WAREHOUSE_DESC_AMY + COD_DESC_AMY;
        Order expectedOrder = new OrderBuilder(AMY).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addOrder(expectedOrder);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }


    @Test
    public void getFilteredOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredOrderList().remove(0));
    }

    @Test
    public void getFilteredReturnOrderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredReturnOrderList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getOrderBook(), model.getReturnOrderBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonOrderBookIoExceptionThrowingStub extends JsonOrderBookStorage {
        private JsonOrderBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveOrderBook(ReadOnlyOrderBook orderBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonReturnOrderBookIoExceptionThrowingStub extends JsonReturnOrderBookStorage {
        private JsonReturnOrderBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveReturnOrderBook(ReadOnlyReturnOrderBook orderBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
