package seedu.address.storage.storageProgress;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelProgress.Progress;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedProgress {

  public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";
  private final String assignmentID;
  private final String studentID;
  private final String isDone;

  /**
   * Constructs a {@code JsonAdaptedPerson} with the given person details.
   */
  @JsonCreator
  public JsonAdaptedProgress(@JsonProperty("assignmentID") String assignmentID, @JsonProperty("studentID") String studentID, @JsonProperty("isDone") String isDone) {
    this.assignmentID = assignmentID;
    this.studentID = studentID;
    this.isDone = isDone;
  }

  /**
   * Converts a given {@code Assignment} into this class for Jackson use.
   */
  public JsonAdaptedProgress(Progress source) {
    assignmentID = source.getId().getAssignmentID().value;
    studentID = source.getId().getStudentID().value;
    isDone = source.getIsDone().toString();
  }

  /**
   * Converts this Jackson-friendly adapted Assignment object into the model's {@code Assignment} object.
   *
   * @throws IllegalValueException if there were any data constraints violated in the adapted
   *                               Assignment.
   */
  public Progress toModelType() throws IllegalValueException, CommandException {
    if (assignmentID == null) {
      throw new IllegalValueException(
          String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
    }
    if (!ID.isValidId(assignmentID)) {
      throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
    }
    final ID modelAssignmentID = new ID(assignmentID);

    if (studentID == null) {
      throw new IllegalValueException(
              String.format(MISSING_FIELD_MESSAGE_FORMAT, ID.class.getSimpleName()));
    }
    if (!ID.isValidId(studentID)) {
      throw new IllegalValueException(ID.MESSAGE_CONSTRAINTS);
    }
    final ID modelStudentID = new ID(studentID);

    if (isDone == null) {
      throw new IllegalValueException(
              String.format(MISSING_FIELD_MESSAGE_FORMAT, Boolean.class.getSimpleName())
      );
    }
    if (!Boolean.parseBoolean(isDone)) {
      throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
    };

    final Boolean modelIsDone = Boolean.valueOf(isDone);

    CompositeID curr = new CompositeID(modelAssignmentID, modelStudentID);

    return new Progress(curr, modelIsDone);
  }

}
