package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;
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
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

  private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
  private AddressBookStorage addressBookStorage;
  private TeacherAddressBookStorage teacherAddressBookStorage;
  private StudentAddressBookStorage studentAddressBookStorage;
  private FinanceAddressBookStorage financeAddressBookStorage;
  private CourseAddressBookStorage courseAddressBookStorage;

  private UserPrefsStorage userPrefsStorage;


  public StorageManager(AddressBookStorage addressBookStorage,
      TeacherAddressBookStorage teacherAddressBookStorage,
      StudentAddressBookStorage studentAddressBookStorage,
      FinanceAddressBookStorage financeAddressBookStorage,
      CourseAddressBookStorage courseAddressBookStorage, UserPrefsStorage userPrefsStorage) {
    super();
    this.addressBookStorage = addressBookStorage;
    this.teacherAddressBookStorage = teacherAddressBookStorage;
    this.studentAddressBookStorage = studentAddressBookStorage;
    this.financeAddressBookStorage = financeAddressBookStorage;
    this.courseAddressBookStorage = courseAddressBookStorage;
    this.userPrefsStorage = userPrefsStorage;
  }

  // ================ UserPrefs methods ==============================

  @Override
  public Path getUserPrefsFilePath() {
    return userPrefsStorage.getUserPrefsFilePath();
  }

  @Override
  public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
    return userPrefsStorage.readUserPrefs();
  }

  @Override
  public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
    userPrefsStorage.saveUserPrefs(userPrefs);
  }

  // ================ AddressBook methods ==============================

  @Override
  public Path getAddressBookFilePath() {
    return addressBookStorage.getAddressBookFilePath();
  }

  @Override
  public Optional<ReadOnlyAddressBook> readAddressBook()
      throws DataConversionException, IOException {
    return readAddressBook(addressBookStorage.getAddressBookFilePath());
  }

  @Override
  public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath)
      throws DataConversionException, IOException {
    logger.fine("Attempting to read data from file: " + filePath);
    return addressBookStorage.readAddressBook(filePath);
  }

  @Override
  public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
    saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
  }

  @Override
  public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
    logger.fine("Attempting to write to data file: " + filePath);
    addressBookStorage.saveAddressBook(addressBook, filePath);
  }

  // ================ TeacherAddressBook methods ==============================

  @Override
  public Path getTeacherAddressBookFilePath() {
    return teacherAddressBookStorage.getTeacherAddressBookFilePath();
  }

  @Override
  public Optional<ReadOnlyTeacherAddressBook> readTeacherAddressBook()
      throws DataConversionException, IOException {
    return readTeacherAddressBook(teacherAddressBookStorage.getTeacherAddressBookFilePath());
  }

  @Override
  public Optional<ReadOnlyTeacherAddressBook> readTeacherAddressBook(Path filePath)
      throws DataConversionException, IOException {
    logger.fine("Attempting to read data from file: " + filePath);
    return teacherAddressBookStorage.readTeacherAddressBook(filePath);
  }

  @Override
  public void saveTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook)
      throws IOException {
    saveTeacherAddressBook(teacherAddressBook,
        teacherAddressBookStorage.getTeacherAddressBookFilePath());
  }

  @Override
  public void saveTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook, Path filePath)
      throws IOException {
    logger.fine("Attempting to write to data file: " + filePath);
    teacherAddressBookStorage.saveTeacherAddressBook(teacherAddressBook, filePath);
  }

  // ================ StudentAddressBook methods ==============================

  @Override
  public Path getStudentAddressBookFilePath() {
    return studentAddressBookStorage.getStudentAddressBookFilePath();
  }

  @Override
  public Optional<ReadOnlyStudentAddressBook> readStudentAddressBook()
      throws DataConversionException, IOException {
    return readStudentAddressBook(studentAddressBookStorage.getStudentAddressBookFilePath());
  }

  @Override
  public Optional<ReadOnlyStudentAddressBook> readStudentAddressBook(Path filePath)
      throws DataConversionException, IOException {
    logger.fine("Attempting to read data from file: " + filePath);
    return studentAddressBookStorage.readStudentAddressBook(filePath);
  }

  @Override
  public void saveStudentAddressBook(ReadOnlyStudentAddressBook studentAddressBook)
      throws IOException {
    saveStudentAddressBook(studentAddressBook,
        studentAddressBookStorage.getStudentAddressBookFilePath());
  }

  @Override
  public void saveStudentAddressBook(ReadOnlyStudentAddressBook studentAddressBook, Path filePath)
      throws IOException {
    logger.fine("Attempting to write to data file: " + filePath);
    studentAddressBookStorage.saveStudentAddressBook(studentAddressBook, filePath);
  }

  // ================ FinanceAddressBook methods ==============================

  @Override
  public Path getFinanceAddressBookFilePath() {
    return financeAddressBookStorage.getFinanceAddressBookFilePath();
  }

  @Override
  public Optional<ReadOnlyFinanceAddressBook> readFinanceAddressBook()
      throws DataConversionException, IOException {
    return readFinanceAddressBook(financeAddressBookStorage.getFinanceAddressBookFilePath());
  }

  @Override
  public Optional<ReadOnlyFinanceAddressBook> readFinanceAddressBook(Path filePath)
      throws DataConversionException, IOException {
    logger.fine("Attempting to read data from file: " + filePath);
    return financeAddressBookStorage.readFinanceAddressBook(filePath);
  }

  @Override
  public void saveFinanceAddressBook(ReadOnlyFinanceAddressBook financeAddressBook)
      throws IOException {
    saveFinanceAddressBook(financeAddressBook,
        financeAddressBookStorage.getFinanceAddressBookFilePath());
  }

  @Override
  public void saveFinanceAddressBook(ReadOnlyFinanceAddressBook financeAddressBook, Path filePath)
      throws IOException {
    logger.fine("Attempting to write to data file: " + filePath);
    financeAddressBookStorage.saveFinanceAddressBook(financeAddressBook, filePath);
  }

  // ================ CourseAddressBook methods ==============================

  @Override
  public Path getCourseAddressBookFilePath() {
    return courseAddressBookStorage.getCourseAddressBookFilePath();
  }

  @Override
  public Optional<ReadOnlyCourseAddressBook> readCourseAddressBook()
      throws DataConversionException, IOException {
    return readCourseAddressBook(courseAddressBookStorage.getCourseAddressBookFilePath());
  }

  @Override
  public Optional<ReadOnlyCourseAddressBook> readCourseAddressBook(Path filePath)
      throws DataConversionException, IOException {
    logger.fine("Attempting to read data from file: " + filePath);
    return courseAddressBookStorage.readCourseAddressBook(filePath);
  }

  @Override
  public void saveCourseAddressBook(ReadOnlyCourseAddressBook courseAddressBook)
      throws IOException {
    saveCourseAddressBook(courseAddressBook,
        courseAddressBookStorage.getCourseAddressBookFilePath());
  }

  @Override
  public void saveCourseAddressBook(ReadOnlyCourseAddressBook courseAddressBook, Path filePath)
      throws IOException {
    logger.fine("Attempting to write to data file: " + filePath);
    courseAddressBookStorage.saveCourseAddressBook(courseAddressBook, filePath);
  }
}
