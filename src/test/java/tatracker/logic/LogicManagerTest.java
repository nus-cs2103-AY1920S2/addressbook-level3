package tatracker.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tatracker.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static tatracker.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.GROUP_DESC_T04;
import static tatracker.logic.commands.CommandTestUtil.MATRIC_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.MODULE_DESC_CS2030;
import static tatracker.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static tatracker.logic.commands.CommandTestUtil.VALID_GROUP_T04;
import static tatracker.logic.commands.CommandTestUtil.VALID_MODULE_CS2030;
import static tatracker.testutil.Assert.assertThrows;
import static tatracker.testutil.student.TypicalStudents.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import tatracker.commons.core.Messages;
import tatracker.logic.commands.CommandResult;
import tatracker.logic.commands.commons.ListCommand;
import tatracker.logic.commands.exceptions.CommandException;
import tatracker.logic.commands.student.AddStudentCommand;
import tatracker.logic.parser.exceptions.ParseException;
import tatracker.model.Model;
import tatracker.model.ModelManager;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.UserPrefs;
import tatracker.model.group.Group;
import tatracker.model.module.Module;
import tatracker.model.student.Student;
import tatracker.storage.JsonTaTrackerStorage;
import tatracker.storage.JsonUserPrefsStorage;
import tatracker.storage.StorageManager;
import tatracker.testutil.student.StudentBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonTaTrackerStorage taTrackerStorage =
                new JsonTaTrackerStorage(temporaryFolder.resolve("tatracker.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(taTrackerStorage, userPrefsStorage);

        model = new ModelManager();

        Module module = new Module(VALID_MODULE_CS2030);
        Group group = new Group(VALID_GROUP_T04);

        module.addGroup(group);
        model.addModule(module);

        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String expectedMsg = Messages.getInvalidCommandMessage(Messages.MESSAGE_INVALID_SESSION_DISPLAYED_INDEX);
        String sessionDeleteCommand = "session delete 9";
        assertCommandException(sessionDeleteCommand, expectedMsg);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.DETAILS.getFullCommandWord();
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_LISTED_SESSIONS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonTaTrackerIoExceptionThrowingStub
        JsonTaTrackerStorage taTrackerStorage =
                new JsonTaTrackerIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionTaTracker.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(taTrackerStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Setup Model
        ModelManager expectedModel = new ModelManager();

        Module expectedModule = new Module(VALID_MODULE_CS2030);
        Group expectedGroup = new Group(VALID_GROUP_T04);

        expectedModule.addGroup(expectedGroup);
        expectedModel.addModule(expectedModule);

        // Execute add command
        String addCommand = AddStudentCommand.DETAILS.getFullCommandWord()
                + MATRIC_DESC_AMY + MODULE_DESC_CS2030 + GROUP_DESC_T04
                + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY;

        Student expectedStudent = new StudentBuilder(AMY).withTags().build();

        expectedModel.addStudent(expectedStudent, expectedGroup.getIdentifier(), expectedModule.getIdentifier());
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredStudentList().remove(0));
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
        Model expectedModel = new ModelManager(model.getTaTracker(), new UserPrefs());
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
    private static class JsonTaTrackerIoExceptionThrowingStub extends JsonTaTrackerStorage {
        private JsonTaTrackerIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTaTracker(ReadOnlyTaTracker taTracker, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
