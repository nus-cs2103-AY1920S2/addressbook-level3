package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddAssignmentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.person.ID;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a assignment identified using it's displayed ID from the address book.
 */
public class DeleteAssignmentCommand extends DeleteCommand {

  public static final String COMMAND_WORD = "delete-assignment";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the assignment identified by the existing ID number used in the displayed assignment list.\n"
      + "Parameters: ID (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 16100";

  public static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Deleted assignment: %1$s";

  private Index targetIndex;
  private ID targetID;
  private Assignment toDelete;

  public DeleteAssignmentCommand(ID targetID) {
    this.targetID = targetID;
  }

  public DeleteAssignmentCommand(Assignment toDelete) {
    this.toDelete = toDelete;
  }

  @Override
  protected void preprocessUndoableCommand(Model model) throws CommandException {
    List<Assignment> lastShownList = model.getFilteredAssignmentList();
    if (this.toDelete == null) {
      if (!ID.isValidId(targetID.toString())) {
        throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_ID);
      }
      this.toDelete = getAssignment(lastShownList);
    }
    if (this.targetID == null) {
      this.targetID = getID(lastShownList);
    }
    if (this.targetIndex == null) {
      this.targetIndex = getIndex(lastShownList);
    }
  }

  @Override
  protected void generateOppositeCommand() throws CommandException {
    oppositeCommand = new AddAssignmentCommand(toDelete, targetIndex.getZeroBased());
  }

  // TODO: Find way to abstract this
  public Index getIndex(List<Assignment> lastShownList) throws CommandException {
    for (int i = 0; i < lastShownList.size(); i++) {
      if (lastShownList.get(i).equals(this.toDelete)) {
        return Index.fromZeroBased(i);
      }
    }
    throw new CommandException("This id not in list");
  }

  // Find way to abstract this
  public ID getID(List<Assignment> lastShownList) throws CommandException {
    for (Assignment assignment : lastShownList) {
      if (assignment.getId().equals(this.toDelete.getId())) {
        return assignment.getId();
      }
    }
    throw new CommandException("Cannot find this course in the list");
  }

  // Find way to abstract this
  public Assignment getAssignment(List<Assignment> lastShownList) throws CommandException {
    for (Assignment assignment : lastShownList) {
      if (assignment.getId().equals(this.targetID)) {
        return assignment;
      }
    }
    throw new CommandException("This course ID does not exist");
  }

  @Override
  public CommandResult executeUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);
    model.delete(this.toDelete);
    return new CommandResult(String.format(MESSAGE_DELETE_ASSIGNMENT_SUCCESS, this.toDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteAssignmentCommand // instanceof handles nulls
        && targetIndex.equals(((DeleteAssignmentCommand) other).targetIndex)); // state check
  }
}
