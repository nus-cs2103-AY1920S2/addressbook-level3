package nasa.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import nasa.commons.exceptions.DataConversionException;
import nasa.model.ReadOnlyHistory;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.ReadOnlyUserPrefs;
import nasa.model.UserPrefs;

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
    Path getHistoryBookFilePath();

    @Override
    Optional<ReadOnlyNasaBook> readNasaBook() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyHistory> readHistoryBook() throws DataConversionException, IOException;

    @Override
    void saveNasaBook(ReadOnlyNasaBook nasaBook) throws IOException;

}
