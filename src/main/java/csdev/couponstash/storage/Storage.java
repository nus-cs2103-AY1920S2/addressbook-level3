package csdev.couponstash.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import csdev.couponstash.commons.exceptions.DataConversionException;
import csdev.couponstash.model.ReadOnlyAddressBook;
import csdev.couponstash.model.ReadOnlyUserPrefs;
import csdev.couponstash.model.UserPrefs;

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
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

}
