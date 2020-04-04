package seedu.address.model.modelProgress;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelGeneric.ModelObject;
import seedu.address.model.person.CompositeID;
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
  private final CompositeID progressID;
  private boolean isDone;

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
    try {
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
    } catch (CommandException e) {
      e.printStackTrace();
    }
    return builder.toString();
  }
}
