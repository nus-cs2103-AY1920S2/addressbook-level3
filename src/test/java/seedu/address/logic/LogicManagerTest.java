package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecipes.CARBONARA;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.cookbook.CookbookAddCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyCart;
import seedu.address.model.ReadOnlyCookbook;
import seedu.address.model.ReadOnlyInventory;
import seedu.address.model.UserPrefs;
import seedu.address.model.recipe.Recipe;
import seedu.address.storage.JsonCartStorage;
import seedu.address.storage.JsonCookbookStorage;
import seedu.address.storage.JsonInventoryStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.RecipeBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonCookbookStorage cookbookStorage =
                new JsonCookbookStorage(temporaryFolder.resolve("cookbook.json"));
        JsonInventoryStorage inventoryStorage =
                new JsonInventoryStorage(temporaryFolder.resolve("inventory.json"));
        JsonCartStorage cartStorage =
                new JsonCartStorage(temporaryFolder.resolve("cart.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(cookbookStorage, inventoryStorage, cartStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String helpCommand = HelpCommand.COMMAND_CATEGORY;
        assertCommandSuccess(helpCommand, HelpCommand.SHOWING_HELP_MESSAGE, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonCookbookIoExceptionThrowingStub, JsonInventoryIoExceptionThrowingStub,
        // and JsonCartIoExceptionThrowingStub
        JsonCookbookStorage cookbookStorage =
                new JsonCookbookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionCookbook.json"));
        JsonInventoryStorage inventoryStorage =
                new JsonInventoryIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionInventory.json"));
        JsonCartStorage cartStorage =
                new JsonCartIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionCart.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(cookbookStorage, inventoryStorage, cartStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute cookbook add command
        String cookbookAddCommand = CookbookAddCommand.COMMAND_CATEGORY + " " + CookbookAddCommand.COMMAND_WORD
                + " n/Carbonara d/description";
        Recipe expectedRecipe = new RecipeBuilder(CARBONARA).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addCookbookRecipe(expectedRecipe);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        // assertCommandFailure(cookbookAddCommand, CommandException.class, expectedMessage, expectedModel);
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
        Model expectedModel = new ModelManager(model.getCookbook(), model.getInventory(),
                model.getCart(), new UserPrefs());
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
    private static class JsonCookbookIoExceptionThrowingStub extends JsonCookbookStorage {
        private JsonCookbookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCookbook(ReadOnlyCookbook cookbook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonInventoryIoExceptionThrowingStub extends JsonInventoryStorage {
        private JsonInventoryIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveInventory(ReadOnlyInventory inventory, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonCartIoExceptionThrowingStub extends JsonCartStorage {
        private JsonCartIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCart(ReadOnlyCart cart, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
