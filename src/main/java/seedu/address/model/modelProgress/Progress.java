package seedu.address.model.modelProgress;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.person.CompositeID;
import seedu.address.model.person.ID;

/**
 * Represents a Progress of a student's assignment in the address book.
 */
public class Progress extends ModelObject {

  // Identity fields
  private final String ENTITY_NAME = "progress";
  private final CompositeID progressID;
  private Boolean isDone;

  /**
   * Every field must be present and not null.
   */
  public Progress(CompositeID progressID) {
    requireAllNonNull(progressID);
    this.progressID = progressID;
    this.isDone = false;
  }

  /**
   * Every field must be present and not null.
   */
  public Progress(CompositeID progressID, boolean isDone) {
    this(progressID);
    this.isDone = isDone;
  }

  public Boolean getIsDone() {
    return this.isDone;
  }

  // Can be done as a toggle but this is more robust as it prevents accidental toggling from done to undone and vice versa
  public void done() {
    this.isDone = true;
  }

  public void undone() {
    this.isDone = false;
  }
  /*
  public Boolean isOverDue() {
  }
   */

  @Override
  public CompositeID getId() {
    return this.progressID;
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
        && otherProgressCast.getId().equals(getId());
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
            && otherProgress.getId().equals(getId());
  }

  @Override
  public int hashCode() {
    // use this method for custom fields hashing instead of implementing your own
    return Objects.hash(progressID, isDone);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    ID studentID = getId().getStudentID();
    ID assignmentID = getId().getAssignmentID();

    builder.append("\n")
            .append("StudentID: ")
            .append(studentID.toString())
            .append("\n")
            .append("AssignmentID: ")
            .append(assignmentID.toString())
            .append("\n")
            .append("Done: ");

    if(isDone) {
      builder.append("[O]");
    } else {
      builder.append("[X]");
    }
    return builder.toString();
  }
}
