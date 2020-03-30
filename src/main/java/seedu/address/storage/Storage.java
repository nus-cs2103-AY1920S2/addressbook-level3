package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.academics.ReadOnlyAcademics;
import seedu.address.model.admin.ReadOnlyAdmin;
import seedu.address.storage.academics.AcademicsStorage;
import seedu.address.storage.admin.AdminStorage;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, AcademicsStorage, AdminStorage, UserPrefsStorage {

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

    @Override
    Path getSavedAcademicsFilePath();

    @Override
    Optional<ReadOnlyAcademics> readAcademics() throws DataConversionException, IOException;

    @Override
    void saveAcademics(ReadOnlyAcademics academics) throws IOException;

    @Override
    Path getAdminFilePath();

    @Override
    Optional<ReadOnlyAdmin> readAdmin() throws DataConversionException, IOException;

    @Override
    void saveAdmin(ReadOnlyAdmin admin) throws IOException;
}
