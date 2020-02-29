package seedu.address.storage.storageCourse;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.modelCourse.ReadOnlyCourseAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface CourseAddressBookStorage {

  /**
   * Returns the file path of the data file.
   */
  Path getCourseAddressBookFilePath();

  /**
   * Returns CourseAddressBook data as a {@link ReadOnlyCourseAddressBook}. Returns {@code
   * Optional.empty()} if storage file is not found.
   *
   * @throws DataConversionException if the data in storage is not in the expected format.
   * @throws IOException             if there was any problem when reading from the storage.
   */
  Optional<ReadOnlyCourseAddressBook> readCourseAddressBook()
      throws DataConversionException, IOException;

  /**
   * @see #getCourseAddressBookFilePath()
   */
  Optional<ReadOnlyCourseAddressBook> readCourseAddressBook(Path filePath)
      throws DataConversionException, IOException;

  /**
   * Saves the given {@link ReadOnlyCourseAddressBook} to the storage.
   *
   * @param courseAddressBook cannot be null.
   * @throws IOException if there was any problem writing to the file.
   */
  void saveCourseAddressBook(ReadOnlyCourseAddressBook courseAddressBook) throws IOException;

  /**
   * @see #saveCourseAddressBook(ReadOnlyCourseAddressBook)
   */
  void saveCourseAddressBook(ReadOnlyCourseAddressBook courseAddressBook, Path filePath)
      throws IOException;

}
