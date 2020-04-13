package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.ITEM_TYPE_RESUME;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_NAME_ME;
import static seedu.address.logic.commands.CommandTestUtil.PREFIXED_TAG_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_JAVA;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalResume.ME_RESUME;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.add.AddCommand;
import seedu.address.logic.commands.delete.DeleteCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.list.ListCommand;
import seedu.address.logic.commands.results.CommandResult;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyResumeBook;
import seedu.address.model.ResumeBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedResumeBook;
import seedu.address.model.item.Resume;
import seedu.address.storage.JsonResumeBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.ResumeBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonResumeBookStorage addressBookStorage =
                new JsonResumeBookStorage(temporaryFolder.resolve("resume.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = DeleteCommand.COMMAND_WORD + " 9" + ITEM_TYPE_INTERNSHIP;
        assertCommandException(deleteCommand, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD + ITEM_TYPE_RESUME;
        assertCommandSuccess(listCommand,
                String.format(ListCommand.MESSAGE_SUCCESS, Resume.class.getSimpleName()), model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonResumeBookIoExceptionThrowingStub
        JsonResumeBookStorage addressBookStorage =
                new JsonResumeBookIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command success in model but saving fails
        String addCommand = AddCommand.COMMAND_WORD + ITEM_TYPE_RESUME + PREFIXED_NAME_ME + PREFIXED_TAG_JAVA;
        Resume expectedResume = new ResumeBuilder(ME_RESUME).withTags(VALID_TAG_JAVA).build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addResume(expectedResume);
        expectedModel.setResumeToDisplay();
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        // Test list passed up not modifiable
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredItemList().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredNoteEntryList().remove(0));
    }

    @Test
    public void getMethods_assertEquals_success() {
        assertEquals(logic.getAddressBook(),
                new VersionedResumeBook(new ResumeBook()));
        assertEquals(logic.getFilteredItemList(),
                new VersionedResumeBook(new ResumeBook()).getItemToDisplayList());
        assertEquals(logic.getFilteredNoteEntryList(),
                new VersionedResumeBook(new ResumeBook()).getNoteToDisplayList());
        assertEquals(logic.getResumeBookFilePath(), Path.of("data/resume.json"));
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
        assertEquals(expectedModel.getStatelessResumeBook(), model.getStatelessResumeBook());
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
        Model expectedModel = new ModelManager(model.getResumeBook(), new UserPrefs());
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
        assertEquals(expectedModel.getStatelessResumeBook(), model.getStatelessResumeBook());
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonResumeBookIoExceptionThrowingStub extends JsonResumeBookStorage {
        private JsonResumeBookIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveResumeBook(ReadOnlyResumeBook addressBook, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
