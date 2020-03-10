package seedu.address.storage.storageStudent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.modelStudent.ReadOnlyStudentAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface StudentAddressBookStorage {

  /**
   * Returns the file path of the data file.
   */
  Path getStudentAddressBookFilePath();

  /**
   * Returns StudentAddressBook data as a {@link ReadOnlyStudentAddressBook}. Returns {@code
   * Optional.empty()} if storage file is not found.
   *
   * @throws DataConversionException if the data in storage is not in the expected format.
   * @throws IOException             if there was any problem when reading from the storage.
   */
  Optional<ReadOnlyStudentAddressBook> readStudentAddressBook()
      throws DataConversionException, IOException;

  /**
   * @see #getStudentAddressBookFilePath()
   */
  Optional<ReadOnlyStudentAddressBook> readStudentAddressBook(Path filePath)
      throws DataConversionException, IOException;

  /**
   * Saves the given {@link ReadOnlyStudentAddressBook} to the storage.
   *
   * @param studentAddressBook cannot be null.
   * @throws IOException if there was any problem writing to the file.
   */
  void saveStudentAddressBook(ReadOnlyStudentAddressBook studentAddressBook) throws IOException;

  /**
   * @see #saveStudentAddressBook(ReadOnlyStudentAddressBook)
   */
  void saveStudentAddressBook(ReadOnlyStudentAddressBook studentAddressBook, Path filePath)
      throws IOException;

}
