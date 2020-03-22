package seedu.address.storage.storageCourseStudent;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.modelCourseStudent.CourseStudent;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedCourseStudent {

  public static final String MISSING_FIELD_MESSAGE_FORMAT = "CourseStudent's %s field is missing!";
  private final String name;
  private final String courseStudentID;
  private final String deadline;
  private final List<JsonCourseStudentAdaptedTag> tagged = new ArrayList<>();

  /**
   * Constructs a {@code JsonAdaptedPerson} with the given person details.
   */
  @JsonCreator
  public JsonAdaptedCourseStudent(@JsonProperty("name") String name,
      @JsonProperty("courseStudentID") String courseStudentID, @JsonProperty("deadline") String deadline,
      @JsonProperty("tagged") List<JsonCourseStudentAdaptedTag> tagged) {
    this.name = name;
    this.courseStudentID = courseStudentID;
    this.deadline = deadline;
    if (tagged != null) {
      this.tagged.addAll(tagged);
    }
  }

  /**
   * Converts a given {@code CourseStudent} into this class for Jackson use.
   */
  public JsonAdaptedCourseStudent(CourseStudent source) {
    name = source.getName().fullName;
    courseStudentID = source.getId().value;
    deadline = source.getDeadline().toString();
    tagged.addAll(source.getTags().stream()
        .map(JsonCourseStudentAdaptedTag::new)
        .collect(Collectors.toList()));
  }

  /**
   * Converts this Jackson-friendly adapted CourseStudent object into the model's {@code CourseStudent} object.
   *
   * @throws IllegalValueException if there were any data constraints violated in the adapted
   *                               CourseStudent.
   */
  public CourseStudent toModelType() throws IllegalValueException {
    final List<Tag> CourseStudentTags = new ArrayList<>();
    for (JsonCourseStudentAdaptedTag tag : tagged) {
      CourseStudentTags.add(tag.toModelType());
    }

    if (name == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
    }
    if (!Name.isValidName(name)) {
      throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
    }
    final Name modelName = new Name(name);

    if (courseStudentID == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
    }
    if (!ID.isValidId(courseStudentID)) {
      throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
    }
    final ID modelId = new ID(courseStudentID);

    if (deadline == null) {
      throw new IllegalValueException(
              String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName())
      );
    }
    if (!Deadline.isValidDeadline(deadline)) {
      throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
    };

    final Deadline modelDeadline = new Deadline(deadline);

    final Set<Tag> modelTags = new HashSet<>(CourseStudentTags);

    return new CourseStudent(modelName, modelId, modelDeadline, modelTags);
  }

}
