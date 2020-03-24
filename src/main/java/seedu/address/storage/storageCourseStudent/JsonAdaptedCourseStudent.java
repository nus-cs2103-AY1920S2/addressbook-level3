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
import seedu.address.model.person.Courseid;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Studentid;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedCourseStudent {

  public static final String MISSING_FIELD_MESSAGE_FORMAT = "CourseStudent's %s field is missing!";
  private final String courseid;
  private final String studentid;
  private final List<JsonCourseStudentAdaptedTag> tagged = new ArrayList<>();

  /**
   * Constructs a {@code JsonAdaptedPerson} with the given person details.
   */
  @JsonCreator
  public JsonAdaptedCourseStudent(@JsonProperty("courseid") String courseid,
      @JsonProperty("studentid") String studentid,
      @JsonProperty("tagged") List<JsonCourseStudentAdaptedTag> tagged) {
    this.courseid = courseid;
    this.studentid = studentid;
    if (tagged != null) {
      this.tagged.addAll(tagged);
    }
  }

  /**
   * Converts a given {@code CourseStudent} into this class for Jackson use.
   */
  public JsonAdaptedCourseStudent(CourseStudent source) {
    courseid = source.getCourseid().toString();
    studentid = source.getStudentid().toString();
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

    if (courseid == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Courseid.class.getSimpleName()));
    }
    if (!Courseid.isValidCourseid(courseid)) {
      throw new IllegalValueException(Courseid.MESSAGE_CONSTRAINTS);
    }
    final Courseid modelCourseid = new Courseid(courseid);

    if (studentid == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, Studentid.class.getSimpleName()));
    }
    if (!Studentid.isValidStudentid(studentid)) {
      throw new IllegalValueException(Studentid.MESSAGE_CONSTRAINTS);
    }
    final Studentid modelStudentid = new Studentid(studentid);


    final Set<Tag> modelTags = new HashSet<>(CourseStudentTags);

    return new CourseStudent(modelCourseid, modelStudentid, modelTags);
  }

}
