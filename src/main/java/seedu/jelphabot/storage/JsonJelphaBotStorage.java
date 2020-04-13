package seedu.jelphabot.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.commons.exceptions.DataConversionException;
import seedu.jelphabot.commons.exceptions.IllegalValueException;
import seedu.jelphabot.commons.util.FileUtil;
import seedu.jelphabot.commons.util.JsonUtil;
import seedu.jelphabot.model.ReadOnlyJelphaBot;


/**
 * A class to access JelphaBot data stored as a json file on the hard disk.
 */
public class JsonJelphaBotStorage implements JelphaBotStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonJelphaBotStorage.class);

    private Path filePath;

    private Path reminderPath;

    public JsonJelphaBotStorage(Path filePath, Path reminderPath) {
        this.filePath = filePath;
        this.reminderPath = reminderPath;
    }

    public Path getJelphaBotFilePath() {
        return filePath;
    }

    public Path getJelphaBotReminderPath() {
        return reminderPath;
    }

    @Override
    public Optional<ReadOnlyJelphaBot> readJelphaBot() throws DataConversionException {
        return readJelphaBot(filePath);
    }

    @Override
    public Optional<ReadOnlyJelphaBot> readJelphaBot(boolean isReminder) throws DataConversionException {
        requireNonNull(reminderPath);

        Optional<JsonSerializableReminderJelphaBot> jsonReminderJelphaBot = JsonUtil.readJsonFile(
            reminderPath, JsonSerializableReminderJelphaBot.class);
        if (!jsonReminderJelphaBot.isPresent()) {
            return Optional.empty();
        }
        try {
            return Optional.of(jsonReminderJelphaBot.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + reminderPath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Similar to {@link #readJelphaBot()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyJelphaBot> readJelphaBot(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableJelphaBot> jsonJelphaBot = JsonUtil.readJsonFile(
                filePath, JsonSerializableJelphaBot.class);
        if (!jsonJelphaBot.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonJelphaBot.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveJelphaBot(ReadOnlyJelphaBot jelphaBot) throws IOException {
        saveJelphaBot(jelphaBot, filePath);
    }

    /**
     * Similar to {@link #saveJelphaBot(ReadOnlyJelphaBot)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveJelphaBot(ReadOnlyJelphaBot jelphaBot, Path filePath) throws IOException {
        requireNonNull(jelphaBot);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableJelphaBot(jelphaBot), filePath);
        FileUtil.createIfMissing(reminderPath);
        JsonUtil.saveJsonFile(new JsonSerializableReminderJelphaBot(jelphaBot), reminderPath);
    }

}
