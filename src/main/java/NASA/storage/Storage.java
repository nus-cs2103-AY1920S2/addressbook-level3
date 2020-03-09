package NASA.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import NASA.commons.exceptions.DataConversionException;
import NASA.model.ReadOnlyNasaBook;
import NASA.model.ReadOnlyUserPrefs;
import NASA.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends NasaBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getNasaBookFilePath();

    @Override
    Optional<ReadOnlyNasaBook> readNasaBook() throws DataConversionException, IOException;

    @Override
    void saveNasaBook(ReadOnlyNasaBook NASABook) throws IOException;

}
