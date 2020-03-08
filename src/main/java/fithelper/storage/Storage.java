package fithelper.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import fithelper.commons.exceptions.DataConversionException;
import fithelper.model.ReadOnlyFitHelper;
import fithelper.model.ReadOnlyUserPrefs;
import fithelper.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FitHelperStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFitHelperFilePath();

    @Override
    Optional<ReadOnlyFitHelper> readFitHelper() throws DataConversionException, IOException;

    @Override
    void saveFitHelper(ReadOnlyFitHelper fitHelper) throws IOException;

}
