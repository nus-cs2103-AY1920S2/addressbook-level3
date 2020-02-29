package seedu.address.storage.storageFinance;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.modelFinance.ReadOnlyFinanceAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface FinanceAddressBookStorage {

  /**
   * Returns the file path of the data file.
   */
  Path getFinanceAddressBookFilePath();

  /**
   * Returns FinanceAddressBook data as a {@link ReadOnlyFinanceAddressBook}. Returns {@code
   * Optional.empty()} if storage file is not found.
   *
   * @throws DataConversionException if the data in storage is not in the expected format.
   * @throws IOException             if there was any problem when reading from the storage.
   */
  Optional<ReadOnlyFinanceAddressBook> readFinanceAddressBook()
      throws DataConversionException, IOException;

  /**
   * @see #getFinanceAddressBookFilePath()
   */
  Optional<ReadOnlyFinanceAddressBook> readFinanceAddressBook(Path filePath)
      throws DataConversionException, IOException;

  /**
   * Saves the given {@link ReadOnlyFinanceAddressBook} to the storage.
   *
   * @param financeAddressBook cannot be null.
   * @throws IOException if there was any problem writing to the file.
   */
  void saveFinanceAddressBook(ReadOnlyFinanceAddressBook financeAddressBook) throws IOException;

  /**
   * @see #saveFinanceAddressBook(ReadOnlyFinanceAddressBook)
   */
  void saveFinanceAddressBook(ReadOnlyFinanceAddressBook financeAddressBook, Path filePath)
      throws IOException;

}
