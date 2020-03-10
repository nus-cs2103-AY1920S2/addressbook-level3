package seedu.address.storage.storageTeacher;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.modelTeacher.ReadOnlyTeacherAddressBook;

/**
 * A class to access TeacherAddressBook data stored as a json file on the hard disk.
 */
public class JsonTeacherAddressBookStorage implements TeacherAddressBookStorage {

  private static final Logger logger = LogsCenter.getLogger(JsonTeacherAddressBookStorage.class);

  private Path filePath;

  public JsonTeacherAddressBookStorage(Path filePath) {
    this.filePath = filePath;
  }

  public Path getTeacherAddressBookFilePath() {
    return filePath;
  }

  @Override
  public Optional<ReadOnlyTeacherAddressBook> readTeacherAddressBook()
      throws DataConversionException {
    return readTeacherAddressBook(filePath);
  }

  /**
   * Similar to {@link #readTeacherAddressBook()}.
   *
   * @param filePath location of the data. Cannot be null.
   * @throws DataConversionException if the file is not in the correct format.
   */
  public Optional<ReadOnlyTeacherAddressBook> readTeacherAddressBook(Path filePath)
      throws DataConversionException {
    requireNonNull(filePath);

    Optional<JsonTeacherSerializableAddressBook> jsonTeacherAddressBook = JsonUtil.readJsonFile(
        filePath, JsonTeacherSerializableAddressBook.class);
    if (!jsonTeacherAddressBook.isPresent()) {
      return Optional.empty();
    }

    try {
      return Optional.of(jsonTeacherAddressBook.get().toModelType());
    } catch (IllegalValueException ive) {
      logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
      throw new DataConversionException(ive);
    }
  }

  @Override
  public void saveTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook)
      throws IOException {
    saveTeacherAddressBook(teacherAddressBook, filePath);
  }

  /**
   * Similar to {@link #saveTeacherAddressBook(ReadOnlyTeacherAddressBook)}.
   *
   * @param filePath location of the data. Cannot be null.
   */
  public void saveTeacherAddressBook(ReadOnlyTeacherAddressBook teacherAddressBook, Path filePath)
      throws IOException {
    System.out.println("h");
    requireNonNull(teacherAddressBook);
    requireNonNull(filePath);

    FileUtil.createIfMissing(filePath);
    JsonUtil.saveJsonFile(new JsonTeacherSerializableAddressBook(teacherAddressBook), filePath);
  }

}
