package seedu.expensela.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.expensela.commons.exceptions.DataConversionException;
import seedu.expensela.model.ExpenseLa;
import seedu.expensela.model.ReadOnlyExpenseLa;

/**
 * Represents a storage for {@link ExpenseLa}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns ExpenseLa data as a {@link ReadOnlyExpenseLa}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyExpenseLa> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyExpenseLa> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyExpenseLa} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyExpenseLa addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyExpenseLa)
     */
    void saveAddressBook(ReadOnlyExpenseLa addressBook, Path filePath) throws IOException;

}
