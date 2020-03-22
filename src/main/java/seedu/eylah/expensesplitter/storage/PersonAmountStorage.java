package seedu.eylah.expensesplitter.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.eylah.commons.exceptions.DataConversionException;
import seedu.eylah.expensesplitter.model.ReadOnlyPersonAmountBook;

/**
 * Represents a storage for {here should put link to receipt!!!}.
 */
public interface PersonAmountStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPersonAmountBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyPersonAmountBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPersonAmountBook> readPersonAmountBook() throws DataConversionException, IOException;

    /**
     * @see @getPersonAmountBookPath()
     */
    Optional<ReadOnlyPersonAmountBook> readPersonAmountBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyPersonAmountBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePersonAmountBook(ReadOnlyPersonAmountBook addressBook) throws IOException;

    /**
     * @see @savePersonAmountBook(ReadOnlyAddressBook)
     */
    void savePersonAmountBook(ReadOnlyPersonAmountBook personAmountBook, Path filePath) throws IOException;

}
