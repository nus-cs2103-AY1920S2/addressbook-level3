package seedu.jelphabot.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.jelphabot.commons.exceptions.DataConversionException;
import seedu.jelphabot.model.ReadOnlyJelphaBot;
import seedu.jelphabot.model.ReadOnlyUserPrefs;
import seedu.jelphabot.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends JelphaBotStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getJelphaBotFilePath();

    @Override
    Optional<ReadOnlyJelphaBot> readJelphaBot() throws DataConversionException, IOException;

    @Override
    void saveJelphaBot(ReadOnlyJelphaBot jelphaBot) throws IOException;

    //@Override
    //void saveJelphaBot(ReadOnlyJelphaBot jelphaBot, boolean isReminder) throws IOException;

}
