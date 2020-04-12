package seedu.eylah.diettracker.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eylah.commons.core.Messages.MESSAGE_INVALID_FOOD_DISPLAYED_INDEX;
import static seedu.eylah.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.CALORIES_DESC_PASTA;
import static seedu.eylah.diettracker.logic.commands.CommandTestUtil.NAME_DESC_PASTA;
import static seedu.eylah.diettracker.testutil.TypicalFood.PASTA;
import static seedu.eylah.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.eylah.commons.logic.command.CommandResult;
import seedu.eylah.commons.logic.command.exception.CommandException;
import seedu.eylah.commons.logic.parser.exception.ParseException;
import seedu.eylah.commons.model.UserPrefs;
import seedu.eylah.commons.storage.JsonUserPrefsStorage;
import seedu.eylah.diettracker.logic.commands.AddCommand;
import seedu.eylah.diettracker.logic.commands.ListCommand;
import seedu.eylah.diettracker.model.DietModel;
import seedu.eylah.diettracker.model.DietModelManager;
import seedu.eylah.diettracker.model.ReadOnlyFoodBook;
import seedu.eylah.diettracker.model.food.Food;
import seedu.eylah.diettracker.storage.DietStorageManager;
import seedu.eylah.diettracker.storage.JsonFoodBookStorage;
import seedu.eylah.diettracker.storage.JsonMyselfStorage;
import seedu.eylah.diettracker.testutil.FoodBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private DietModel model = new DietModelManager();
    private DietLogic logic;

    @BeforeEach
    public void setUp() {
        JsonFoodBookStorage foodBookStorage =
                new JsonFoodBookStorage(temporaryFolder.resolve("foodBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonMyselfStorage myselfStorage = new JsonMyselfStorage(temporaryFolder.resolve("myself.json"));
        DietStorageManager storage = new DietStorageManager(foodBookStorage, userPrefsStorage, myselfStorage);
        logic = new DietLogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_FOOD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand,
                "List is empty for today! Add a -a flag to access your entire diet history.\n"
                + "\n"
                + "Total Calorie Intake : 0\n"
                + "\n"
                + "You have 2500 calories left for the day!\n"
                + "All foods over period based on input tag has been listed.\n", model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonFoodBookIoExceptionThrowingStub
        JsonFoodBookStorage foodBookStorage =
                new JsonFoodBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionFoodBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonMyselfStorage myselfStorage = new JsonMyselfStorage(temporaryFolder.resolve("ioExceptionMyself.json"));
        DietStorageManager storage = new DietStorageManager(foodBookStorage, userPrefsStorage, myselfStorage);
        logic = new DietLogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_PASTA + CALORIES_DESC_PASTA;
        Food expectedFood = new FoodBuilder(PASTA).withTags().build();
        DietModel expectedModel = model;
        expectedModel.addFood(expectedFood);
        String expectedMessage = DietLogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredFoodList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredFoodList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, DietModel)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      DietModel expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, DietModel)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, DietModel)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, DietModel)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        DietModel expectedModel = new DietModelManager(model.getFoodBook(), new UserPrefs(), model.getMyself());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, DietModel)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, DietModel expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFoodBookIoExceptionThrowingStub extends JsonFoodBookStorage {
        private JsonFoodBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFoodBook(ReadOnlyFoodBook foodBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
