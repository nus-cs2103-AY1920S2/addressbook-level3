package seedu.recipe.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.recipe.commons.core.Messages.MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX;
import static seedu.recipe.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.recipe.logic.commands.CommandTestUtil.FRUIT_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.GRAIN_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.NAME_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.OTHER_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.PROTEIN_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.STEP_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.TIME_DESC_TURKEY_SANDWICH;
import static seedu.recipe.logic.commands.CommandTestUtil.VEGETABLE_DESC_TURKEY_SANDWICH;
import static seedu.recipe.testutil.Assert.assertThrows;
import static seedu.recipe.testutil.TypicalRecipes.TURKEY_SANDWICH;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.recipe.logic.commands.AddCommand;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.ListCommand;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.Model;
import seedu.recipe.model.ModelManager;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.UserPrefs;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.storage.JsonCookedRecordBookStorage;
import seedu.recipe.storage.JsonRecipeBookStorage;
import seedu.recipe.storage.JsonUserPrefsStorage;
import seedu.recipe.storage.StorageManager;
import seedu.recipe.storage.plan.JsonPlannedBookStorage;
import seedu.recipe.testutil.RecipeBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonRecipeBookStorage recipeBookStorage =
                new JsonRecipeBookStorage(temporaryFolder.resolve("recipeBook.json"));
        JsonPlannedBookStorage plannedBookStorage =
                new JsonPlannedBookStorage(temporaryFolder.resolve("plannedBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonCookedRecordBookStorage recordBookStorage =
                new JsonCookedRecordBookStorage(temporaryFolder.resolve("cookedRecordBook.json"));
        StorageManager storage = new StorageManager(recipeBookStorage, recordBookStorage,
                plannedBookStorage, userPrefsStorage);

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
        assertCommandException(deleteCommand, MESSAGE_INVALID_RECIPE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonRecipeBookIoExceptionThrowingStub
        JsonRecipeBookStorage recipeBookStorage =
                new JsonRecipeBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionRecipeBook.json"));
        JsonPlannedBookStorage plannedBookStorage =
                new JsonPlannedBookStorage(temporaryFolder.resolve("ioExceptionPlannedBook.json"));
        // todo add tests for plannedbook
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonCookedRecordBookStorage recordBookStorage =
                new JsonCookedRecordBookStorage(temporaryFolder.resolve("ioExceptionRecordBook.json"));
        StorageManager storage = new StorageManager(recipeBookStorage, recordBookStorage,
                plannedBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_TURKEY_SANDWICH + TIME_DESC_TURKEY_SANDWICH
                + STEP_DESC_TURKEY_SANDWICH + GRAIN_DESC_TURKEY_SANDWICH + VEGETABLE_DESC_TURKEY_SANDWICH
                + PROTEIN_DESC_TURKEY_SANDWICH + FRUIT_DESC_TURKEY_SANDWICH + OTHER_DESC_TURKEY_SANDWICH;
        Recipe expectedRecipe = new RecipeBuilder(TURKEY_SANDWICH).withGoals().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addRecipe(expectedRecipe);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredRecipeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredRecipeList().remove(0));
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

        Model expectedModel = new ModelManager(model.getRecipeBook(), new UserPrefs(),
                model.getRecordBook(), new PlannedBook());
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
    private static class JsonRecipeBookIoExceptionThrowingStub extends JsonRecipeBookStorage {
        private JsonRecipeBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveRecipeBook(ReadOnlyRecipeBook recipeBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
