package fithelper.logic;

import static fithelper.commons.core.Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX;
import static fithelper.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static fithelper.testutil.AssertUtil.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import fithelper.logic.exceptions.CommandException;
import fithelper.logic.parser.exceptions.ParseException;
import fithelper.model.Model;
import fithelper.model.ModelManager;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.storage.JsonFitHelperStorage;
import fithelper.storage.JsonUserProfileStorage;
import fithelper.storage.JsonWeightRecordsStorage;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonFitHelperStorage fitHelperStorage =
                new JsonFitHelperStorage(temporaryFolder.resolve("fitHelper.json"));
        JsonUserProfileStorage userProfileStorage =
                new JsonUserProfileStorage(temporaryFolder.resolve("userProfile.json"));
        JsonWeightRecordsStorage weightRecordsStorage =
                new JsonWeightRecordsStorage(temporaryFolder.resolve("weightRecords.json"));
        logic = new LogicManager(model, fitHelperStorage, userProfileStorage, weightRecordsStorage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete x/f i/9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
    }

    @Test
    public void getFilteredEntryList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredFoodEntryList().remove(0));
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
        Model expectedModel = new ModelManager(model.getFitHelper(), model.getUserProfile(), model.getWeightRecords());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonFitHelperIoExceptionThrowingStub extends JsonFitHelperStorage {
        private JsonFitHelperIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFitHelper(ReadOnlyFitHelper fitHelper, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}

