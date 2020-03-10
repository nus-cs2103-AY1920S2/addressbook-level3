package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.modelCourse.ReadOnlyCourseAddressBook;
import seedu.address.model.modelFinance.ReadOnlyFinanceAddressBook;
import seedu.address.model.modelStudent.ReadOnlyStudentAddressBook;
import seedu.address.model.modelTeacher.ReadOnlyTeacherAddressBook;
import seedu.address.storage.storageCourse.CourseAddressBookStorage;
import seedu.address.storage.storageFinance.FinanceAddressBookStorage;
import seedu.address.storage.storageStudent.StudentAddressBookStorage;
import seedu.address.storage.storageTeacher.TeacherAddressBookStorage;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, TeacherAddressBookStorage,
    StudentAddressBookStorage,
    FinanceAddressBookStorage, CourseAddressBookStorage, UserPrefsStorage {

  @Override
  Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

  @Override
  void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

  ///
  @Override
  Path getAddressBookFilePath();

  @Override
  Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

  @Override
  void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;


  ///
  @Override
  Path getTeacherAddressBookFilePath();

  @Override
  Optional<ReadOnlyTeacherAddressBook> readTeacherAddressBook()
      throws DataConversionException, IOException;

  @Override
  void saveTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook) throws IOException;

  ///
  @Override
  Path getStudentAddressBookFilePath();

  @Override
  Optional<ReadOnlyStudentAddressBook> readStudentAddressBook()
      throws DataConversionException, IOException;

  @Override
  void saveStudentAddressBook(ReadOnlyStudentAddressBook studentAddressBook) throws IOException;

  ///
  @Override
  Path getFinanceAddressBookFilePath();

  @Override
  Optional<ReadOnlyFinanceAddressBook> readFinanceAddressBook()
      throws DataConversionException, IOException;

  @Override
  void saveFinanceAddressBook(ReadOnlyFinanceAddressBook financeAddressBook) throws IOException;

  ///
  @Override
  Path getCourseAddressBookFilePath();

  @Override
  Optional<ReadOnlyCourseAddressBook> readCourseAddressBook()
      throws DataConversionException, IOException;

  @Override
  void saveCourseAddressBook(ReadOnlyCourseAddressBook courseAddressBook) throws IOException;

}
