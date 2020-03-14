package seedu.address.storage.storageAssignments;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.modelAssignment.ReadOnlyAssignmentAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.modelAssignment.AssignmentAddressBook}.
 */
public interface AssignmentAddressBookStorage {

  /**
   * Returns the file path of the data file.
   */
  Path getAssignmentAddressBookFilePath();

  /**
   * Returns AssignmentAddressBook data as a {@link ReadOnlyAssignmentAddressBook}. Returns {@code
   * Optional.empty()} if storage file is not found.
   *
   * @throws DataConversionException if the data in storage is not in the expected format.
   * @throws IOException             if there was any problem when reading from the storage.
   */
  Optional<ReadOnlyAssignmentAddressBook> readAssignmentAddressBook()
      throws DataConversionException, IOException;

  /**
   * @see #getAssignmentAddressBookFilePath()
   */
  Optional<ReadOnlyAssignmentAddressBook> readAssignmentAddressBook(Path filePath)
      throws DataConversionException, IOException;

  /**
   * Saves the given {@link ReadOnlyAssignmentAddressBook} to the storage.
   *
   * @param assignmentAddressBook cannot be null.
   * @throws IOException if there was any problem writing to the file.
   */
  void saveAssignmentAddressBook(ReadOnlyAssignmentAddressBook assignmentAddressBook) throws IOException;

  /**
   * @see #saveAssignmentAddressBook(ReadOnlyAssignmentAddressBook)
   */
  void saveAssignmentAddressBook(ReadOnlyAssignmentAddressBook assignmentAddressBook, Path filePath)
      throws IOException;

}
