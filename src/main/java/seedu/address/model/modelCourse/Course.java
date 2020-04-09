package seedu.address.model.modelCourse;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import seedu.address.commons.core.UuidManager;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.modelGeneric.ModelObject;
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
    requireAllNonNull(name, id, amount, tags);
    this.name = name;
    this.id = id;
    this.amount = amount;
    this.tags.addAll(tags);
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
  }

  public Course clone() {
    Course cloned = new Course(this.name, this.id, this.amount, this.assignedStaffID,
            this.assignedStudentsID, this.assignedAssignmentsID, this.tags);
    return cloned;
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

  public boolean hasTeacher() {
    if(this.assignedStaffID == null || this.assignedStaffID.equals(new ID(""))) {
      return false;
    } else {
      return true;
    }
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

  public void assignStaff(ID staffid) {
    this.assignedStaffID = staffid;
  }

    public boolean containsStaff(ID staffID) {
      if(assignedStaffID != null || assignedStaffID.equals(new ID(""))) {
        return true;
      } else {
        return false;
      }
    }

    // ================================== FOR UNASSIGN COMMANDS =================================

    public void removeAssignment(ID assignmentID) {
      this.assignedAssignmentsID.remove(assignmentID);
    }

    public void removeStudent(ID studentID) {
      this.assignedStudentsID.remove(studentID);
    }

    public void removeStaff() {
      this.assignedStaffID = null;
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
