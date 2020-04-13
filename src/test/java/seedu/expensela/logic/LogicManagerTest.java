package seedu.expensela.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.expensela.commons.core.Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX;
import static seedu.expensela.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.expensela.logic.commands.CommandTestUtil.AMOUNT_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.CATEGORY_DESC_FOOD;
import static seedu.expensela.logic.commands.CommandTestUtil.DATE_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.NAME_DESC_PIZZA;
import static seedu.expensela.logic.commands.CommandTestUtil.REMARK_DESC_PIZZA;
import static seedu.expensela.testutil.Assert.assertThrows;
import static seedu.expensela.testutil.TypicalTransactions.PIZZA;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.expensela.logic.commands.AddCommand;
import seedu.expensela.logic.commands.CommandResult;
import seedu.expensela.logic.commands.ListCommand;
import seedu.expensela.logic.commands.exceptions.CommandException;
import seedu.expensela.logic.parser.exceptions.ParseException;
import seedu.expensela.model.GlobalData;
import seedu.expensela.model.Model;
import seedu.expensela.model.ModelManager;
import seedu.expensela.model.ReadOnlyExpenseLa;
import seedu.expensela.model.UserPrefs;
import seedu.expensela.model.transaction.Transaction;
import seedu.expensela.storage.JsonExpenseLaStorage;
import seedu.expensela.storage.JsonGlobalDataStorage;
import seedu.expensela.storage.JsonUserPrefsStorage;
import seedu.expensela.storage.StorageManager;
import seedu.expensela.testutil.MonthlyDataBuilder;
import seedu.expensela.testutil.TransactionBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        model.setMonthlyData(new MonthlyDataBuilder().build());
        JsonExpenseLaStorage expenseLaStorage =
                new JsonExpenseLaStorage(temporaryFolder.resolve("expenseLa.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonGlobalDataStorage globalDataStorage = new JsonGlobalDataStorage(temporaryFolder.resolve("globalData.json"));
        StorageManager storage = new StorageManager(expenseLaStorage, userPrefsStorage, globalDataStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonExpenseLaIoExceptionThrowingStub
        JsonExpenseLaStorage expenseLaStorage =
                new JsonExpenseLaIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionExpenseLa.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonGlobalDataStorage globalDataStorage =
                new JsonGlobalDataStorage(temporaryFolder.resolve("ioExceptionGlobalData.json"));
        StorageManager storage = new StorageManager(expenseLaStorage, userPrefsStorage, globalDataStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_PIZZA + AMOUNT_DESC_PIZZA + DATE_DESC_PIZZA
                + REMARK_DESC_PIZZA + CATEGORY_DESC_FOOD;
        Transaction expectedTransaction = new TransactionBuilder(PIZZA).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.setMonthlyData(new MonthlyDataBuilder().build());
        expectedModel.addTransaction(expectedTransaction);
        expectedModel.updateFilteredTransactionList(expectedModel.getFilter().getDateMonthPredicate(), null);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredTransactionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTransactionList().remove(0));
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
        Model expectedModel = new ModelManager(model.getExpenseLa(), new UserPrefs(), new GlobalData());
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
    private static class JsonExpenseLaIoExceptionThrowingStub extends JsonExpenseLaStorage {
        private JsonExpenseLaIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveExpenseLa(ReadOnlyExpenseLa expenseLa, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
