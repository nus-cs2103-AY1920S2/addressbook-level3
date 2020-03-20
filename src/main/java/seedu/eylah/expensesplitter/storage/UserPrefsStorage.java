package seedu.eylah.expensesplitter.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.expensesplitter.model.ReadOnlyUserPrefs;
import seedu.eylah.expensesplitter.model.UserPrefs;


public interface UserPrefsStorage {

    Path getUserPrefsFilePath();

    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    void saveUserPrefs(ReadOnlyUserPrefs userPref) throws IOException;
}
