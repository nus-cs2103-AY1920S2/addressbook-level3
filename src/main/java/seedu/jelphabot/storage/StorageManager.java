package seedu.jelphabot.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.jelphabot.commons.core.LogsCenter;
import seedu.jelphabot.commons.exceptions.DataConversionException;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.ReadOnlyUserPrefs;
import seedu.jelphabot.model.UserPrefs;

/**
 * Manages storage of JelphaBot data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private JelphaBotStorage jelphaBotStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(JelphaBotStorage jelphaBotStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.jelphaBotStorage = jelphaBotStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ JelphaBot methods ==============================

    @Override
    public Path getJelphaBotFilePath() {
        return jelphaBotStorage.getJelphaBotFilePath();
    }

    @Override
    public Path getJelphaBotReminderPath() {
        return jelphaBotStorage.getJelphaBotReminderPath();
    }

    @Override
    public Optional<ReadOnlyJelphaBot> readJelphaBot() throws DataConversionException, IOException {
        return readJelphaBot(jelphaBotStorage.getJelphaBotFilePath());
    }

    public Optional<ReadOnlyJelphaBot> readJelphaBot(boolean isReminder) throws DataConversionException, IOException {
        return jelphaBotStorage.readJelphaBot(isReminder);
    }

    @Override
    public Optional<ReadOnlyJelphaBot> readJelphaBot(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return jelphaBotStorage.readJelphaBot(filePath);
    }

    @Override
    public void saveJelphaBot(ReadOnlyJelphaBot jelphaBot) throws IOException {
        saveJelphaBot(jelphaBot, jelphaBotStorage.getJelphaBotFilePath());
    }

    //@Override
    //public void saveJelphaBot(ReadOnlyJelphaBot jelphaBot, boolean isReminder) throws IOException {
    // jelphaBotStorage.saveJelphaBot(jelphaBot, true);
    //}

    @Override
    public void saveJelphaBot(ReadOnlyJelphaBot jelphaBot, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        jelphaBotStorage.saveJelphaBot(jelphaBot, filePath);
    }

}
