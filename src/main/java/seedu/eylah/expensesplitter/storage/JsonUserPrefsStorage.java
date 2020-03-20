package seedu.eylah.expensesplitter.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.expensesplitter.model.ReadOnlyUserPrefs;
import seedu.eylah.expensesplitter.model.UserPrefs;
import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.commons.util.JsonUtil;


public class JsonUserPrefsStorage implements UserPrefsStorage {

    private Path filePath;

    public JsonUserPrefsStorage(Path filePath) { this.filePath = filePath; }

    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException {
        return readUserPrefs(filePath);
    }

    public Optional<UserPrefs> readUserPrefs(Path presFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(presFilePath, UserPrefs.class);
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs , filePath);
    }


}
