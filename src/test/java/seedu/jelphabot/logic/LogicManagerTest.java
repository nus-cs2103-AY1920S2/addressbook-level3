package seedu.jelphabot.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.jelphabot.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DATETIME_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TUTORIAL;
import static seedu.jelphabot.logic.commands.CommandTestUtil.MODULE_CODE_DESC_TUTORIAL;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalTasks.TUTORIAL;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jelphabot.logic.commands.AddCommand;
import seedu.jelphabot.logic.commands.CommandResult;
import seedu.jelphabot.logic.commands.ListCommand;
import seedu.jelphabot.logic.commands.exceptions.CommandException;
import seedu.jelphabot.logic.parser.exceptions.ParseException;
import seedu.jelphabot.model.Model;
import seedu.jelphabot.model.ModelManager;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.UserPrefs;
import seedu.jelphabot.model.task.Task;
import seedu.jelphabot.storage.JsonJelphaBotStorage;
import seedu.jelphabot.storage.JsonUserPrefsStorage;
import seedu.jelphabot.storage.StorageManager;
import seedu.jelphabot.testutil.TaskBuilder;

public class LogicManagerTest {

    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonJelphaBotStorage jelphaBotStorage = new JsonJelphaBotStorage(
            temporaryFolder.resolve("jelphaBot.json"), temporaryFolder.resolve("reminder.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(jelphaBotStorage, userPrefsStorage);
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
        assertCommandException(deleteCommand, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SWITCH_PANEL_ACKNOWLEDGEMENT, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonJelphaBotIoExceptionThrowingStub
        JsonJelphaBotStorage jelphaBotStorage = new JsonJelphaBotIoExceptionThrowingStub(
            temporaryFolder.resolve("ioExceptionJelphaBot.json"),
            temporaryFolder.resolve("ioExceptionReminder.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(
            temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(jelphaBotStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand =
            AddCommand.COMMAND_WORD + DESCRIPTION_DESC_TUTORIAL + DATETIME_DESC_TUTORIAL + MODULE_CODE_DESC_TUTORIAL;
        Task expectedTask = new TaskBuilder(TUTORIAL).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addTask(expectedTask);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredTaskList().remove(0));
    }

    //@@author yapdianhao
    @Test
    public void getFilteredReminderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getReminderList().remove(0));
    }

    //@@author yapdianhao
    @Test
    public void getFilteredByReminder_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredByReminder().remove(0));
    }

    //@@author eedenong
    @Test
    public void getFilteredByCompleteTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredByCompleteTaskList().remove(0));
    }

    //@@author eedenong
    @Test
    public void getFilteredByIncompleteTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredByIncompleteTaskList().remove(0));
    }

    //@@author eedenong
    @Test
    public void getFilteredByIncompleteDueTodayTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic
                                                                    .getFilteredByIncompleteDueTodayTaskList()
                                                                    .remove(0));
    }

    /**
     * Executes the command and confirms that - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in
     * {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage, Model expectedModel)
        throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the
     * result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the
     * result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the
     * result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
        String expectedMessage) {
        Model expectedModel = new ModelManager(model.getJelphaBot(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that - the {@code expectedException} is
     * thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in
     * {@code expectedModel} <br>
     *
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
    private static class JsonJelphaBotIoExceptionThrowingStub extends JsonJelphaBotStorage {
        private JsonJelphaBotIoExceptionThrowingStub(Path filePath, Path reminderPath) {
            super(filePath, reminderPath);
        }

        @Override
        public void saveJelphaBot(ReadOnlyJelphaBot jelphaBot, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
