package seedu.address.model.modelCourse;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.person.Amount;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.CourseNotFoundException;
import seedu.address.model.person.exceptions.DuplicateCourseException;
import seedu.address.model.tag.Tag;

/**
 * Represents a Course in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class Course extends ModelObject {

  // Identity fields
  private final String ENTITY_NAME = "Course";
  private final Name name;
  private final ID id;
  // TODO: Create CourseTeacherTab along with CourseStudentTab
  // TODO: Remove dummy teacher_id
  private ID teacher_id = new ID("123");
  private final Set<Tag> tags = new HashSet<>();
  private Amount amount;
  private String assignedStudents = "";

  /**
   * Every field must be present and not null.
   */
  public Course(Name name, Amount amount, Set<Tag> tags) throws ParseException {
    requireAllNonNull(name, tags);
    this.name = name;
    this.id = UuidManager.assignNewUUID(this);
    this.amount = amount;
    this.tags.addAll(tags);
  }

  /**
   * Overloaded constructor for edited object, loaded from storage, or sample data
   */
  public Course(Name name, ID id, Amount amount, Set<Tag> tags) {
    requireAllNonNull(name, tags);
    this.name = name;
    this.id = id;
    this.amount = amount;
    this.tags.addAll(tags);
  }

  public Name getName() {
    return name;
  }

  public ID getID() {
    return id;
  }

  public Amount getAmount() {
    return amount;
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
  public boolean weakEquals(ModelObject otherCourse) {
    if (otherCourse == this) {
      return true;
    }

    if (otherCourse instanceof Course == false) {
      return false;
    }
    Course otherCourseCast = (Course) otherCourse;
    return otherCourseCast != null
//        && otherCourse.getName().equals(getName())
            && otherCourseCast.getID().equals(getID());
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
        && otherCourse.getID().equals(getID())
        && otherCourse.getAmount().equals(getAmount())
        && otherCourse.getTags().equals(getTags());
  }

  @Override
  public int hashCode() {
    // use this method for custom fields hashing instead of implementing your own
    return Objects.hash(name, id, amount, tags);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(getName())
        .append(" ID: ")
        .append(getID())
        .append(" Amount: ")
        .append(getAmount())
        .append(" Tags: ");
    getTags().forEach(builder::append);
    return builder.toString();
  }

}
