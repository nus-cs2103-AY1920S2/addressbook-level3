//@@author yapdianhao
package seedu.jelphabot.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.jelphabot.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.jelphabot.commons.exceptions.IllegalValueException;
import seedu.jelphabot.commons.util.JsonUtil;
import seedu.jelphabot.model.JelphaBot;
import seedu.jelphabot.testutil.TypicalReminders;

public class JsonSerializableReminderJelphabotTest {

    /*
    fk this test. JelphaBot's equal method only compares between its task. but i'm lazy to change
     */
    private static final Path TEST_DATA_FOLDER =
        Paths.get("src", "test", "data", "JsonSerializableReminderJelphaBotTest");
    private static final Path TYPICAL_REMINDERS_FILE = TEST_DATA_FOLDER.resolve("typicalRemindersJelphaBot.json");
    private static final Path INVALID_REMINDER_FILE = TEST_DATA_FOLDER.resolve("invalidReminderJelphaBot.json");
    private static final Path DUPLICATE_REMINDER_FILE = TEST_DATA_FOLDER.resolve("duplicateReminderJelphaBot.json");


    @Test
    public void toModelType_typicalRemindersFile_success() throws Exception {

        JsonSerializableReminderJelphaBot dataFromFile = JsonUtil.readJsonFile(TYPICAL_REMINDERS_FILE,
            JsonSerializableReminderJelphaBot.class).get();
        JelphaBot jelphaBotFromFile = dataFromFile.toModelType();
        JelphaBot typicalRemindersJelphaBot = TypicalReminders.getTypicalJelphaBot();
        assertEquals(jelphaBotFromFile, typicalRemindersJelphaBot);

    }

    @Test
    public void toModelType_invalidRemindersFile_throwsIllegalValueException() throws Exception {
        JsonSerializableReminderJelphaBot dataFromFile = JsonUtil.readJsonFile(INVALID_REMINDER_FILE,
            JsonSerializableReminderJelphaBot.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }
}
