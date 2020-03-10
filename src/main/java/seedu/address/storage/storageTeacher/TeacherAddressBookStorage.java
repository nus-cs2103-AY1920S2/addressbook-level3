package seedu.address.storage.storageTeacher;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.modelTeacher.ReadOnlyTeacherAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface TeacherAddressBookStorage {

  /**
   * Returns the file path of the data file.
   */
  Path getTeacherAddressBookFilePath();

  /**
   * Returns TeacherAddressBook data as a {@link ReadOnlyTeacherAddressBook}. Returns {@code
   * Optional.empty()} if storage file is not found.
   *
   * @throws DataConversionException if the data in storage is not in the expected format.
   * @throws IOException             if there was any problem when reading from the storage.
   */
  Optional<ReadOnlyTeacherAddressBook> readTeacherAddressBook()
      throws DataConversionException, IOException;

  /**
   * @see #getTeacherAddressBookFilePath()
   */
  Optional<ReadOnlyTeacherAddressBook> readTeacherAddressBook(Path filePath)
      throws DataConversionException, IOException;

  /**
   * Saves the given {@link ReadOnlyTeacherAddressBook} to the storage.
   *
   * @param teacherAddressBook cannot be null.
   * @throws IOException if there was any problem writing to the file.
   */
  void saveTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook) throws IOException;

  /**
   * @see #saveTeacherAddressBook(ReadOnlyTeacherAddressBook)
   */
  void saveTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook, Path filePath)
      throws IOException;

}
