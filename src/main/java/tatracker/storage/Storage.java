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
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyTaTracker> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyTaTracker addressBook) throws IOException;

}
