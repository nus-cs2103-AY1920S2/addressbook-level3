package seedu.address.model.modelCourse;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Represents a Course in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Course {

  // Identity fields
  private final Name name;
  private final ID id;
  // TODO: Create CourseTeacherTab along with CourseStudentTab
  // TODO: Remove dummy teacher_id
  private ID teacher_id = new ID("123");
  private final Set<Tag> tags = new HashSet<>();
  private String assignedStudents = "hey man";

  /**
   * Every field must be present and not null.
   */
  public Course(Name name, ID id, Set<Tag> tags) {
    requireAllNonNull(name, id, tags);
    this.name = name;
    this.id = id;
    this.tags.addAll(tags);
  }

  public Name getName() {
    return name;
  }

  public ID getId() {
    return id;
  }

  public ID getTeacherID() {
    return this.teacher_id;
  }

  public void setTeacherID(ID teacher_id) {
    this.teacher_id = teacher_id;
  }

  public void setAssignedStudents(String assignedStudents){
    this.assignedStudents = assignedStudents;
  }

  public String getAssignedStudents(){
    return this.assignedStudents;
  }
  /**
   * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
   * modification is attempted.
   */
  public Set<Tag> getTags() {
    return Collections.unmodifiableSet(tags);
  }

  /**
   * Returns true if both courses of the same name have at least one other identity field that is
   * the same. This defines a weaker notion of equality between two courses.
   */
  public boolean isSameCourse(Course otherCourse) {
    if (otherCourse == this) {
      return true;
    }

    return otherCourse != null
//        && otherCourse.getName().equals(getName())
        && otherCourse.getId().equals(getId());
  }

  /**
   * Returns true if both courses have the same identity and data fields. This defines a stronger
   * notion of equality between two courses.
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Course)) {
      return false;
    }

    Course otherCourse = (Course) other;
    return otherCourse.getName().equals(getName())
        && otherCourse.getId().equals(getId())
        && otherCourse.getTags().equals(getTags());
  }

  @Override
  public int hashCode() {
    // use this method for custom fields hashing instead of implementing your own
    return Objects.hash(name, id, tags);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(getName())
        .append("Amount: ")
        .append(getId())
        .append(" Tags: ");
    getTags().forEach(builder::append);
    return builder.toString();
  }

}
