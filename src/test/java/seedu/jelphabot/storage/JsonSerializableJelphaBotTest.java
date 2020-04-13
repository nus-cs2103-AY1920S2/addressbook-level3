package seedu.jelphabot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jelphabot.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.exceptions.IllegalValueException;
import seedu.jelphabot.commons.util.JsonUtil;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.testutil.TypicalTasks;

public class JsonSerializableJelphaBotTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableJelphaBotTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksJelphaBot.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskJelphaBot.json");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskJelphaBot.json");

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableJelphaBot dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializableJelphaBot.class).get();
        JelphaBot jelphaBotFromFile = dataFromFile.toModelType();
        JelphaBot typicalTasksJelphaBot = TypicalTasks.getTypicalJelphaBot();
        assertEquals(jelphaBotFromFile, typicalTasksJelphaBot);
    }

    @Test
    public void toModelType_invalidTaskFile_throwsIllegalValueException() throws Exception {
        JsonSerializableJelphaBot dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableJelphaBot.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTasks_throwsIllegalValueException() throws Exception {
        JsonSerializableJelphaBot dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASK_FILE,
                JsonSerializableJelphaBot.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableJelphaBot.MESSAGE_DUPLICATE_TASK,
                dataFromFile::toModelType);
    }

}
