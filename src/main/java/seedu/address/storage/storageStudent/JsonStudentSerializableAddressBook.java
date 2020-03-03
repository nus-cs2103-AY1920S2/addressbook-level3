package seedu.address.storage.storageStudent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelStudent.ReadOnlyStudentAddressBook;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.modelStudent.StudentAddressBook;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "studentaddressbook")
class JsonStudentSerializableAddressBook {

  public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";

  private final List<JsonAdaptedStudent> students = new ArrayList<>();

  /**
   * Constructs a {@code JsonSerializableAddressBook} with the given students.
   */
  @JsonCreator
  public JsonStudentSerializableAddressBook(
      @JsonProperty("students") List<JsonAdaptedStudent> students) {
    this.students.addAll(students);
  }

  /**
   * Converts a given {@code ReadOnlyStudentAddressBook} into this class for Jackson use.
   *
   * @param source future changes to this will not affect the created {@code
   *               JsonStudentSerializableAddressBook}.
   */
  public JsonStudentSerializableAddressBook(ReadOnlyStudentAddressBook source) {
    students.addAll(source.getStudentList().stream().map(
        JsonAdaptedStudent::new).collect(Collectors.toList()));
  }

  /**
   * Converts this address book into the model's {@code AddressBook} object.
   *
   * @throws IllegalValueException if there were any data constraints violated.
   */
  public StudentAddressBook toModelType() throws IllegalValueException {
    StudentAddressBook studentAddressBook = new StudentAddressBook();
    for (JsonAdaptedStudent jsonAdaptedStudent : students) {
      Student student = jsonAdaptedStudent.toModelType();
      if (studentAddressBook.hasStudents(student)) {
        throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
      }
      studentAddressBook.addStudent(student);
    }
    return studentAddressBook;
  }

}
