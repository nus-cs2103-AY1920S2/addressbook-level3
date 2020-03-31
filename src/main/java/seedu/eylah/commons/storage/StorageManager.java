package seedu.eylah.commons.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.model.ReadOnlyUserPrefs;
import seedu.eylah.commons.model.UserPrefs;

/**
 * API of the Storage component.
 */
public class StorageManager implements Storage {

    private UserPrefsStorage userPrefsStorage;

    public StorageManager(UserPrefsStorage userPrefsStorage) {
        this.userPrefsStorage = userPrefsStorage;
    }

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
}
