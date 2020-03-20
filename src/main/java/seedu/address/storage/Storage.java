package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.good.Good;
import seedu.address.model.person.Person;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, InventoryStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyList<Person>> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyList<Person> addressBook) throws IOException;

    @Override
    Path getInventoryFilePath();

    @Override
    Optional<ReadOnlyList<Good>> readInventory() throws DataConversionException, IOException;

    @Override
    void saveInventory(ReadOnlyList<Good> inventory) throws IOException;

}
