package com.flashspeed.logic;

import static com.flashspeed.commons.core.Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX;
import static com.flashspeed.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static com.flashspeed.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.flashspeed.logic.commands.CommandResult;
import com.flashspeed.logic.commands.deckcommands.CreateDeckCommand;
import com.flashspeed.logic.commands.exceptions.CommandException;
import com.flashspeed.logic.parser.exceptions.ParseException;
import com.flashspeed.model.Model;
import com.flashspeed.model.ModelManager;
import com.flashspeed.model.ReadOnlyLibrary;
import com.flashspeed.model.UserPrefs;
import com.flashspeed.model.deck.Deck;
import com.flashspeed.storage.JsonLibraryStorage;
import com.flashspeed.storage.JsonUserPrefsStorage;
import com.flashspeed.storage.StorageManager;
import com.flashspeed.testutil.DeckBuilder;

public class LogicManagerTest {

    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonLibraryStorage addressBookStorage =
                new JsonLibraryStorage(temporaryFolder.resolve("library.json"));
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
        String deleteCommand = "remove 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    // @Test
    // public void execute_validCommand_success() throws Exception {
    //     String listCommand = ListCommand.COMMAND_WORD;
    //     assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    // }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonLibraryStorage libraryStorage =
                new JsonLibraryIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionLibrary.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(libraryStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCommand = CreateDeckCommand.COMMAND_WORD + " Japanese";
        Deck expectedDeck = new DeckBuilder().withName("Japanese").build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.createDeck(expectedDeck);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredDeckList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredDeckList().remove(0));
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
        Model expectedModel = new ModelManager(model.getLibrary(), new UserPrefs());
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
    private static class JsonLibraryIoExceptionThrowingStub extends JsonLibraryStorage {
        private JsonLibraryIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveLibrary(ReadOnlyLibrary library, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
