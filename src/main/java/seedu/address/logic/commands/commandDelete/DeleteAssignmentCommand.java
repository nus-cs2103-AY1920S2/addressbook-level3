package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddAssignmentCommand;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStudent.Student;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a assignment identified using it's displayed index from the address book.
 */
public class DeleteAssignmentCommand extends DeleteCommand {

  public static final String COMMAND_WORD = "delete-assignment";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the assignment identified by the index number used in the displayed assignment list.\n"
      + "Parameters: INDEX (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 1";

  public static final String MESSAGE_DELETE_ASSIGNMENT_SUCCESS = "Deleted assignment: %1$s";

  private Index targetIndex;

  private Assignment toDelete;

  public DeleteAssignmentCommand(Index targetIndex) {
    this.targetIndex = targetIndex;
  }

  public DeleteAssignmentCommand(Assignment toDelete) {
    this.toDelete = toDelete;
  }

  @Override
  protected void preprocessUndoableCommand(Model model) throws CommandException {
    List<Assignment> lastShownList = model.getFilteredAssignmentList();
    if (this.toDelete == null) {
      if (targetIndex.getZeroBased() >= lastShownList.size()) {
        throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
      }

      this.toDelete = lastShownList.get(targetIndex.getZeroBased());
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
