package seedu.address.storage.storageCourseStudent;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.modelCourseStudent.ReadOnlyCourseStudentAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.modelCourseStudent.CourseStudentAddressBook}.
 */
public interface CourseStudentAddressBookStorage {

  /**
   * Returns the file path of the data file.
   */
  Path getCourseStudentAddressBookFilePath();

  /**
   * Returns CourseStudentAddressBook data as a {@link ReadOnlyCourseStudentAddressBook}. Returns {@code
   * Optional.empty()} if storage file is not found.
   *
   * @throws DataConversionException if the data in storage is not in the expected format.
   * @throws IOException             if there was any problem when reading from the storage.
   */
  Optional<ReadOnlyCourseStudentAddressBook> readCourseStudentAddressBook()
      throws DataConversionException, IOException;

  /**
   * @see #getCourseStudentAddressBookFilePath()
   */
  Optional<ReadOnlyCourseStudentAddressBook> readCourseStudentAddressBook(Path filePath)
      throws DataConversionException, IOException;

  /**
   * Saves the given {@link ReadOnlyCourseStudentAddressBook} to the storage.
   *
   * @param courseStudentAddressBook cannot be null.
   * @throws IOException if there was any problem writing to the file.
   */
  void saveCourseStudentAddressBook(ReadOnlyCourseStudentAddressBook courseStudentAddressBook) throws IOException;

  /**
   * @see #saveCourseStudentAddressBook(ReadOnlyCourseStudentAddressBook)
   */
  void saveCourseStudentAddressBook(ReadOnlyCourseStudentAddressBook courseStudentAddressBook, Path filePath)
      throws IOException;

}
