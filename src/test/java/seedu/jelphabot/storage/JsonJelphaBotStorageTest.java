package seedu.jelphabot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.jelphabot.testutil.Assert.assertThrows;
import static seedu.jelphabot.testutil.TypicalTasks.ASSESSMENT;
import static seedu.jelphabot.testutil.TypicalTasks.TUTORIAL;
import static seedu.jelphabot.testutil.TypicalTasks.getTypicalJelphaBot;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.jelphabot.commons.exceptions.DataConversionException;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.model.ReadOnlyJelphaBot;

public class JsonJelphaBotStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonJelphaBotStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readJelphaBot_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readJelphaBot(null));
    }

    private java.util.Optional<ReadOnlyJelphaBot> readJelphaBot(String filePath) throws Exception {
        return new JsonJelphaBotStorage(Paths.get(filePath), testFolder.resolve("reminder.json"))
                   .readJelphaBot(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readJelphaBot("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readJelphaBot("notJsonFormatJelphaBot.json"));
    }

    @Test
    public void readJelphaBot_invalidTaskJelphaBot_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readJelphaBot("invalidTaskJelphaBot.json"));
    }

    @Test
    public void readJelphaBot_invalidAndValidTaskJelphaBot_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readJelphaBot("invalidAndValidTaskJelphaBot.json"));
    }

    @Test
    public void readAndSaveJelphaBot_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempJelphaBot.json");
        Path reminderPath = testFolder.resolve("TempReminder.json");
        JelphaBot original = getTypicalJelphaBot();
        JsonJelphaBotStorage jsonJelphaBotStorage = new JsonJelphaBotStorage(filePath, reminderPath);

        // Save in new file and read back
        jsonJelphaBotStorage.saveJelphaBot(original, filePath);
        ReadOnlyJelphaBot readBack = jsonJelphaBotStorage.readJelphaBot(filePath).get();
        assertEquals(original, new JelphaBot(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTask(TUTORIAL);
        original.removeTask(ASSESSMENT);
        jsonJelphaBotStorage.saveJelphaBot(original, filePath);
        readBack = jsonJelphaBotStorage.readJelphaBot(filePath).get();
        assertEquals(original, new JelphaBot(readBack));

        // Save and read without specifying file path
        original.addTask(ASSESSMENT);
        jsonJelphaBotStorage.saveJelphaBot(original); // file path not specified
        readBack = jsonJelphaBotStorage.readJelphaBot().get(); // file path not specified
        assertEquals(original, new JelphaBot(readBack));

    }

    @Test
    public void saveJelphaBot_nullJelphaBot_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveJelphaBot(null, "SomeFile.json"));
    }

    /**
     * Saves {@code jelphaBot} at the specified {@code filePath}.
     */
    private void saveJelphaBot(ReadOnlyJelphaBot jelphaBot, String filePath) {
        try {
            new JsonJelphaBotStorage(Paths.get(filePath), testFolder.resolve("reminder.json"))
                    .saveJelphaBot(jelphaBot, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveJelphaBot_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveJelphaBot(new JelphaBot(), null));
    }
}
