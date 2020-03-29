package tatracker.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tatracker.commons.exceptions.DataConversionException;
import tatracker.model.ReadOnlyTaTracker;
import tatracker.model.ReadOnlyUserPrefs;
import tatracker.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends TaTrackerStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getTaTrackerFilePath();

    @Override
    Optional<ReadOnlyTaTracker> readTaTracker() throws DataConversionException, IOException;

    @Override
    void saveTaTracker(ReadOnlyTaTracker taTracker) throws IOException;

}
