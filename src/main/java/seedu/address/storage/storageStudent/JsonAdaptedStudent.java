package seedu.address.storage.storageStudent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.AssignedCourses;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Student}.
 */
class JsonAdaptedStudent {

  public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

  private final String name;
  private final String studentID;
  private final List<JsonStudentAdaptedID> assignedCoursesID = new ArrayList<>();
  private final List<JsonStudentAdaptedTag> tagged = new ArrayList<>();

  /**
   * Constructs a {@code JsonAdaptedPerson} with the given person details.
   */
  @JsonCreator
  public JsonAdaptedStudent(@JsonProperty("name") String name,
      @JsonProperty("studentID") String studentID,
      @JsonProperty("assignedCourses") String assignedCourses,
      @JsonProperty("assignedCoursesID") List<JsonStudentAdaptedID> assignedCoursesID,
      @JsonProperty("tagged") List<JsonStudentAdaptedTag> tagged) {
    this.name = name;
    this.studentID = studentID;
    if (assignedCoursesID != null) {
      this.assignedCoursesID.addAll(assignedCoursesID);
    }
    if (tagged != null) {
      this.tagged.addAll(tagged);
    }
  }

  /**
   * Converts a given {@code Student} into this class for Jackson use.
   */
  public JsonAdaptedStudent(Student source) {
    name = source.getName().fullName;
    studentID = source.getID().value;
    assignedCoursesID.addAll(source.getAssignedCoursesID().stream()
        .map(JsonStudentAdaptedID::new)
        .collect(Collectors.toList()));
    tagged.addAll(source.getTags().stream()
        .map(JsonStudentAdaptedTag::new)
        .collect(Collectors.toList()));
  }

  /**
   * Converts this Jackson-friendly adapted course object into the model's {@code Student} object.
   *
   * @throws IllegalValueException if there were any data constraints violated in the adapted
   *                               course.
   */
  public Student toModelType() throws IllegalValueException {
    if (name == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
    }
    if (!Name.isValidName(name)) {
      throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
    }
    final Name modelName = new Name(name);

    if (studentID == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
    }
    if (!ID.isValidId(studentID)) {
      throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
    }
    final ID modelID = new ID(studentID);

    final List<ID> StudentAssignedCoursesID = new ArrayList<>();
    for (JsonStudentAdaptedID id : assignedCoursesID) {
      StudentAssignedCoursesID.add(id.toModelType());
    }
    final Set<ID> modelAssignedCoursesID = new HashSet<>(StudentAssignedCoursesID);

    final List<Tag> courseTags = new ArrayList<>();
    for (JsonStudentAdaptedTag tag : tagged) {
      courseTags.add(tag.toModelType());
    }

    final Set<Tag> modelTags = new HashSet<>(courseTags);
    return new Student(modelName, modelID, modelAssignedCoursesID, modelTags);
  }

}
