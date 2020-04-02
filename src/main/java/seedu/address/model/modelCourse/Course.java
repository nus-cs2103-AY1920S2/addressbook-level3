package seedu.address.model.modelCourse;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.Amount;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
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
  private final Set<Tag> tags = new HashSet<>();
  private Amount amount;
  private ID assignedStaffID;
  private Set<ID> assignedStudentsID = new HashSet<>();
  private Set<ID> assignedAssignmentsID = new HashSet<>();
  private String assignedStaffWithName;
  private String assignedStudentsWithNames;
  /**
   * Every field must be present and not null.
   */
  public Course(Name name, Amount amount, Set<Tag> tags) throws ParseException {
    requireAllNonNull(name, tags);
    this.name = name;
    this.id = UuidManager.assignNewUUID(this);
    this.amount = amount;
    this.tags.addAll(tags);
    this.assignedStaffWithName = "None";
    this.assignedStudentsWithNames = "None";
  }

  /**
   * Overloaded constructor for edited object, loaded from storage, or sample data
   */

  public Course(Name name, ID id, Amount amount, Set<Tag> tags) {
    requireAllNonNull(name, id, amount, tags);
    this.name = name;
    this.id = id;
    this.amount = amount;
    this.tags.addAll(tags);
    this.assignedStaffWithName = "None";
    this.assignedStudentsWithNames = "None";
  }

  public Course(Name name, ID id, Amount amount, ID assignedStaffID, Set<ID> assignedStudentsID, Set<ID> assignedAssignmentsID, Set<Tag> tags) {
    requireAllNonNull(name, id, amount, tags);
    this.name = name;
    this.id = id;
    this.amount = amount;
    this.assignedStaffID = assignedStaffID;
    this.assignedStudentsID.addAll(assignedStudentsID);
    this.assignedAssignmentsID.addAll(assignedAssignmentsID);
    this.tags.addAll(tags);
    this.assignedStaffWithName = "None";
    this.assignedStudentsWithNames = "None";
  }


  public Name getName() {
    return name;
  }

  public ID getId() {
    return id;
  }

  public Amount getAmount() {
    return amount;
  }

  public ID getAssignedStaffID() {
    return assignedStaffID;
  }

  public void addStudent(ID studentID) {
    this.assignedStudentsID.add(studentID);
  }

  public void addStudents(Set<ID> studentIDs) {
    this.assignedStudentsID.addAll(studentIDs);
  }

  public void addAssignment(ID assignmentID) {
    this.assignedAssignmentsID.add(assignmentID);
  }

  public void addAssignments(Set<ID> assignmentIDs) {
    this.assignedAssignmentsID.addAll(assignmentIDs);
  }
  // ================================== FOR ASSIGN COMMANDS =================================
  public boolean containsStudent(ID studentID) {
    if(assignedStudentsID.contains(studentID)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean containsAssignment(ID assignmentID) {
    if(assignedAssignmentsID.contains(assignmentID)) {
      return true;
    } else {
      return false;
    }
  }
  // ================================== FOR ASSIGN COMMANDS =================================

  public void assignStaff(ID staffid) {
    this.assignedStaffID = staffid;
  }

  public Set<ID> getAssignedAssignmentsID() {
    return Collections.unmodifiableSet(assignedAssignmentsID);
  }

  /**
   * Get List of String of the ID
   * @return Array of String
   */
  public List<String> getAssignedStudentsIDString() {
    List<String> IDList = new ArrayList<>();
    for (ID id : assignedStudentsID) {
      IDList.add(id.toString());
    }
    return IDList;
  }

  /**
   * Returns an immutable ID set, which throws {@code UnsupportedOperationException} if
   * modification is attempted.
   */
  public Set<ID> getAssignedStudentsID() {
    return Collections.unmodifiableSet(assignedStudentsID);
  }

  public String getAssignedStudentsWithNames(){
    return this.assignedStudentsWithNames;
  }

  public String getAssignedStaffWithName(){
    return this.assignedStaffWithName;
  }
  /**
   * Converts internal list of assigned staff ID into the name with the ID
   */
  public void processAssignedStaff(FilteredList<Staff> filteredStaffs){
    this.assignedStaffWithName = "None";
    for (Staff staff : filteredStaffs) {
      if (staff.getId().toString().equals(this.assignedStaffID.toString())) {
        this.assignedStaffWithName = staff.getName().toString() + "(" + staff.getId().toString() + ")";
      }
    }
  }

  /**
   * Converts internal list of assigned student IDs into the name with the IDs
   */
  public void processAssignedStudents(FilteredList<Student> filteredStudents){
    StringBuilder s = new StringBuilder();
    int count = 1;
    for (ID studentid : assignedStudentsID) {
      for (Student student : filteredStudents) {
        if (studentid.toString().equals(student.getId().toString())) {
          String comma = ", ";
          if (count == assignedStudentsID.size()) {
            comma = "";
          }
          s.append(student.getName()).append("(").append(studentid).append(")").append(comma);
        }
      }
      count++;
    }

    if (s.toString().equals("")) {
      this.assignedStudentsWithNames = "None";
    } else {
      this.assignedStudentsWithNames = "[" + s.toString() + "]";
    }
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
            && otherCourseCast.getId().equals(getId());
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
    return otherCourse.getId().equals(getId());
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
        .append(getId())
        .append(" Amount: ")
        .append(getAmount())
        .append(" AssignedStaff: ")
        .append(getAssignedStaffID())
        .append(" Assigned Students: ");
    getAssignedStudentsID().forEach(builder::append);

    builder
        .append(" Tags: ");
    getTags().forEach(builder::append);
    return builder.toString();
  }

}
