package seedu.address.model.modelStaff;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

/**
 * Represents a Staff in the address book. Guarantees: details are present and not null, field
 * values are validated, immutable.
 * A Staff can be a teacher or an admin. This can be check by isTeacher() method.
 */
public class Staff extends ModelObject {
  // Permission level
  public enum Level {
    TEACHER,
    ADMIN
  }
  // Identity fields
  private final Name name;
  private final ID id;
  private final Level level;
  private final Phone phone;
  private final Email email;
  private final Address address;

  // Data fields
  private Set<ID> assignedCoursesID = new HashSet<>();
  private String assignedCourses = "";
  private final Salary salary;
  private final Set<Tag> tags = new HashSet<>();

  /**
   * Every field must be present and not null.
   */
  public Staff(Name name, Level level, Phone phone, Email email, Salary salary, Address address, Set<Tag> tags) throws ParseException {
    requireAllNonNull(name,level, phone, email, address, tags);
    this.name = name;
    this.id = UuidManager.assignNewUUID(this);
    this.level = level;
    this.phone = phone;
    this.email = email;
    this.salary = salary;
    this.address = address;
    this.tags.addAll(tags);
  }

  public Staff(Name name, ID id, Level level, Phone phone, Email email, Salary salary, Address address, Set<Tag> tags) {
    requireAllNonNull(name,level, phone, email, address, tags);
    this.name = name;
    this.id = id;
    this.level = level;
    this.phone = phone;
    this.email = email;
    this.salary = salary;
    this.address = address;
    this.tags.addAll(tags);
  }

  public Staff(Name name, ID id, Level level, Phone phone, Email email, Salary salary, Address address, Set<ID> assignedCoursesID, Set<Tag> tags) {
    requireAllNonNull(name,level, phone, email, address, tags);
    this.name = name;
    this.id = id;
    this.level = level;
    this.phone = phone;
    this.email = email;
    this.salary = salary;
    this.address = address;
    this.assignedCoursesID.addAll(assignedCoursesID);
    this.tags.addAll(tags);
  }

  /**
   * Get Name of a staff.
   */
  public Name getName() {
    return name;
  }

  /**
   * Get ID of a staff.
   */
  public ID getId() {
    return id;
  }

  /**
   * Returns an immutable ID set, which throws {@code UnsupportedOperationException} if
   * modification is attempted.
   */
  public Set<ID> getAssignedCoursesID() {
    return Collections.unmodifiableSet(assignedCoursesID);
  }

  /**
   * Assign a course ID to a staff (the staff must be a teacher)
   * @param courseID
   */
  public void addCourse(ID courseID) {
    if (isTeacher())
      this.assignedCoursesID.add(courseID);
  }

  /**
   * Assign a course ID to a staff (the staff must be a teacher)
   * @param courseID
   */
  public void addCourses(Set<ID> courseID) {
    if (isTeacher())
      this.assignedCoursesID.addAll(courseID);
  }

  /**
   * Converts internal list of assigned student IDs into the name with the IDs
   */
  public void processAssignedCourses(FilteredList<Course> filteredCourses){
    StringBuilder s = new StringBuilder();
    int count = 1;
    for (ID courseid : assignedCoursesID) {
      for (Course course : filteredCourses) {
        if (courseid.toString().equals(course.getId().toString())) {
          String comma = ", ";
          if (count == assignedCoursesID.size()) {
            comma = "";
          }
          s.append(course.getName().toString()).append("(").append(courseid).append(")").append(comma);
        }
      }
      count++;
    }

    if (s.toString().equals("")) {
      this.assignedCourses = "None";
    } else {
      this.assignedCourses = "[" + s.toString() + "]";
    }
  }

  /**
   * Check if this staff is a teacher.
   * @return true if this is a teacher, false if this is an admin.
   */
  public boolean isTeacher() {
    return level == Level.TEACHER;
  }

  /**
   * Check if this staff is an admin.
   * @return true if this is a teacher, false if this is an admin.
   */
  public boolean isAdmin() {
    return level == Level.ADMIN;
  }

  /**
   * Check if this staff is an admin.
   * @return true if this is a teacher, false if this is an admin.
   */
  public Level getLevel() {
    return level;
  }

  /**
   * Get phone of a staff.
   */
  public Phone getPhone() {
    return phone;
  }

  /**
   * Get Email of a staff.
   */
  public Email getEmail() {
    return email;
  }

  /**
   * Get salary of a staff.
   */
  public Salary getSalary() {
    return salary;
  }

  /**
   * Get address of a staff.
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Returns an immutable tag set, which throws {@code UnsupportedOperationException} if
   * modification is attempted.
   */
  public Set<Tag> getTags() {
    return Collections.unmodifiableSet(tags);
  }

  /**
   * Check if a staff has any Tags.
   * @return true if the staff has at least 1 tag, false otherwise
   */
  public boolean hasTags() {
      return !tags.isEmpty();
  }

  /**
   * Get set an assigned course to a staff. The staff need to be a teacher to be assigned a course.
   */
  public void setAssignedCourses(String assignedCourses) {
      this.assignedCourses = assignedCourses;
  }

  public String getAssignedCourses() {
      return this.assignedCourses;
  }

  /**
   * Returns true if both staffs of the same name have at least one other identity field that is
   * the same. This defines a weaker notion of equality between two teachers.
   */
  @Override
  public boolean weakEquals(ModelObject otherStaff) {
    if (otherStaff == this) {
      return true;
    }
    if (!(otherStaff instanceof Staff)) {
      return false;
    }
    Staff otherStaffCast = (Staff) otherStaff;
    return otherStaffCast.getName().equals(getName())
            && otherStaffCast.getPhone().equals(getPhone())
            && otherStaffCast.getEmail().equals(getEmail())
            && otherStaffCast.getAddress().equals(getAddress());
  }

  /**
   * Returns true if both staffs have the same identity and data fields. This defines a stronger
   * notion of equality between two staffs.
   */
  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }

    if (!(other instanceof Staff)) {
      return false;
    }

    Staff otherStaff = (Staff) other;
    return otherStaff.getName().equals(getName())
        && otherStaff.getPhone().equals(getPhone())
            && otherStaff.getId().equals(getId())
        && otherStaff.getEmail().equals(getEmail())
        && otherStaff.getSalary().equals(getSalary())
        && otherStaff.getAddress().equals(getAddress())
        && otherStaff.getTags().equals(getTags());
  }

  @Override
  public int hashCode() {
    // use this method for custom fields hashing instead of implementing your own
    return Objects.hash(name, phone, email, salary, address, tags);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    String title = "";
    if (isTeacher()) {
      title = "Teacher ";
    } else {
      title = "Admin ";
    }
    builder.append(title);
    builder.append(" Name: ").append(getName());
    if (getPhone().isKnown()) {
      builder.append(" Phone: ").append(getPhone());
    }
    if (getEmail().isKnown()) {
      builder.append(" Email: ").append(getEmail());
    }
    builder.append(" Salary: ").append(getSalary());
    if (getAddress().isKnown()) {
      builder.append(" Address: ").append(getAddress());
    }
    if (hasTags()) {
      builder.append(" Tags: ");
      getTags().forEach(builder::append);
    }
    return builder.toString();
  }
}
