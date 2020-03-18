package seedu.zerotoone.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX;
import static seedu.zerotoone.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.zerotoone.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.zerotoone.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.zerotoone.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.zerotoone.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.zerotoone.testutil.Assert.assertThrows;
import static seedu.zerotoone.testutil.TypicalExercises.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.zerotoone.logic.commands.AddCommand;
import seedu.zerotoone.logic.commands.CommandResult;
import seedu.zerotoone.logic.commands.ListCommand;
import seedu.zerotoone.logic.commands.exceptions.CommandException;
import seedu.zerotoone.logic.parser.exceptions.ParseException;
import seedu.zerotoone.model.Model;
import seedu.zerotoone.model.ModelManager;
import seedu.zerotoone.model.exercise.Exercise;
import seedu.zerotoone.model.exercise.ReadOnlyExerciseList;
import seedu.zerotoone.model.userprefs.UserPrefs;
import seedu.zerotoone.storage.JsonExerciseListStorage;
import seedu.zerotoone.storage.StorageManager;
import seedu.zerotoone.storage.userprefs.JsonUserPrefsStorage;
import seedu.zerotoone.testutil.ExerciseBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonExerciseListStorage exerciseListStorage =
                new JsonExerciseListStorage(temporaryFolder.resolve("exerciseList.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(exerciseListStorage, userPrefsStorage);
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
        assertCommandException(deleteCommand, MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonExerciseListIoExceptionThrowingStub
        JsonExerciseListStorage exerciseListStorage =
                new JsonExerciseListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionExerciseList.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(exerciseListStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        Exercise expectedExercise = new ExerciseBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addExercise(expectedExercise);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredExerciseList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredExerciseList().remove(0));
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
        Model expectedModel = new ModelManager(model.getExerciseList(), new UserPrefs());
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
    private static class JsonExerciseListIoExceptionThrowingStub extends JsonExerciseListStorage {
        private JsonExerciseListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveExerciseList(ReadOnlyExerciseList exerciseList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
