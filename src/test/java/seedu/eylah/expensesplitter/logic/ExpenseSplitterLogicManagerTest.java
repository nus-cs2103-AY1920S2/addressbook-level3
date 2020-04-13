package seedu.eylah.expensesplitter.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.eylah.expensesplitter.testutil.Assert.assertThrows;
import static seedu.eylah.expensesplitter.testutil.EntryBuilder.DEFAULT_ITEM;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.commons.model.Model;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.commons.storage.JsonUserPrefsStorage;
import seedu.eylah.expensesplitter.logic.commands.AddItemCommand;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;
import seedu.eylah.expensesplitter.model.ReadOnlyReceiptBook;
import seedu.eylah.expensesplitter.model.SplitterModel;
import seedu.eylah.expensesplitter.model.SplitterModelManager;
import seedu.eylah.expensesplitter.model.receipt.Entry;
import seedu.eylah.expensesplitter.storage.JsonPersonAmountBookStorage;
import seedu.eylah.expensesplitter.storage.JsonReceiptBookStorage;
import seedu.eylah.expensesplitter.storage.SplitterStorage;
import seedu.eylah.expensesplitter.storage.SplitterStorageManager;
import seedu.eylah.expensesplitter.testutil.EntryBuilder;
import seedu.eylah.expensesplitter.testutil.TypicalPersons;

public class ExpenseSplitterLogicManagerTest {

    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private SplitterModel model = new SplitterModelManager();
    private SplitterLogic logic;

    @BeforeEach
    public void setUp() {
        //Create for Person Amount Book.
        JsonPersonAmountBookStorage personAmountBookStorage =
            new JsonPersonAmountBookStorage(temporaryFolder.resolve("personamount.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(
            temporaryFolder.resolve("userPrefs.json"));

        // Next need to create for Receipt.
        JsonReceiptBookStorage receiptBookStorage =
            new JsonReceiptBookStorage(temporaryFolder.resolve("receiptbook.json"));

        //Creating the storage for expense splitter.
        SplitterStorage storage = new SplitterStorageManager(personAmountBookStorage,
            receiptBookStorage, userPrefsStorage);

        logic = new SplitterLogicManager(model, storage);

    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }




    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {

        JsonPersonAmountBookStorage personAmountBookStorage =
            new JsonPersonAmountBookIoExceptionThrowingStub(temporaryFolder
                .resolve("ioExceptionPersonAmountBook.json"));

        JsonReceiptBookStorage receiptBookStorage =
            new JsonReceiptBookIoExceptionThrowingStub(temporaryFolder
                .resolve("ioExceptionReceiptBook.json"));

        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));

        SplitterStorageManager storage = new SplitterStorageManager(personAmountBookStorage, receiptBookStorage,
            userPrefsStorage);

        logic = new SplitterLogicManager(model, storage);

        //Execute addItemCommand
        String addItemCommand = AddItemCommand.COMMAND_WORD + " -i " + DEFAULT_ITEM.getItemName().toString()
            + " -p " + DEFAULT_ITEM.getItemPrice().toString().substring(1) + " -n "
            + TypicalPersons.DARREN.getName().toString()
            + " -n " + TypicalPersons.ELYSHA.getName().toString();

        Entry expectedEntry = new EntryBuilder().entryBuilderVersionTwo().build();

        SplitterModelManager expectedModel = new SplitterModelManager();
        expectedModel.addEntry(expectedEntry);
        String expectedMessage = SplitterLogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addItemCommand, CommandException.class, expectedMessage, expectedModel);


    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see#assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        //assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }


    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see#assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        SplitterModel expectedModel = new SplitterModelManager(model.getReceiptBook(),
            model.getPersonAmountBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see#assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, SplitterModel expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see#assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }




    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonPersonAmountBookIoExceptionThrowingStub extends JsonPersonAmountBookStorage {
        private JsonPersonAmountBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);

        }

        @Override
        public void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonReceiptBookIoExceptionThrowingStub extends JsonReceiptBookStorage {
        private JsonReceiptBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);

        }

        @Override
        public void saveReceiptBook(ReadOnlyReceiptBook receiptBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }




}
