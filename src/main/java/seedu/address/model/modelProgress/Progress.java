package seedu.address.model.modelProgress;

import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.ID;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Progress of a student's assignment in the address book.
 */
public class Progress extends ModelObject {

  // Identity fields
  private final String ENTITY_NAME = "progress";
  private final ID studentID;
  private final ID assignmentID;
  private boolean isDone;

  /**
   * Every field must be present and not null.
   */
  public Progress(Name name, ID studentID, ID assignmentId) {
    requireAllNonNull(name, assignmentId, studentID);
    this.assignmentID = assignmentId;
    this.studentID = studentID;
    this.isDone = false;
  }

  /**
   * Every field must be present and not null.
   */
  public Progress(Name name, ID studentID, ID assignmentId, boolean isDone) {
    this(name, studentID, assignmentId);
    this.isDone = isDone;
  }

  public ID getStudentID() {
    return this.studentID;
  }

  public ID getAssignmentID() {
    return this.assignmentID;
  }

  public Boolean getIsDone() {
    return this.isDone;
  }


  @Override
  public ID getId() {
    return null;
  }

  /**
   * Returns true if both courses of the same name have at least one other identity field that is
   * the same. This defines a weaker notion of equality between two courses.
   */
  @Override
  public boolean weakEquals(ModelObject otherProgress) {
    if (otherProgress == this) {
      return true;
    }

    if (otherProgress instanceof Progress == false) {
      return false;
    }
    Progress otherProgressCast = (Progress)otherProgress;
    return otherProgressCast != null
        && otherProgressCast.getIsDone().equals(getIsDone())
        && otherProgressCast.getAssignmentID().equals(getAssignmentID())
        && otherProgressCast.getStudentID().equals(getStudentID());
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

    if (!(other instanceof Progress)) {
      return false;
    }

    Progress otherProgress = (Progress) other;
    return otherProgress != null
            && otherProgress.getIsDone().equals(getIsDone())
            && otherProgress.getAssignmentID().equals(getAssignmentID())
            && otherProgress.getStudentID().equals(getStudentID());
  }

  @Override
  public int hashCode() {
    // use this method for custom fields hashing instead of implementing your own
    return Objects.hash(assignmentID, studentID, isDone);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("\n")
            .append("Progress: ")
            .append("\n")
            .append("aid: ")
            .append(getAssignmentID())
            .append(" sid: ")
            .append(getStudentID());

    if(isDone) {
      builder.append("[O]");
    } else {
      builder.append("[X]");
    }
    return builder.toString();
  }
}
