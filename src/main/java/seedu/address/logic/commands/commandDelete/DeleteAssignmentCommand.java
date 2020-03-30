package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;

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

  private final Index targetIndex;

  public DeleteAssignmentCommand(Index targetIndex) {
    this.targetIndex = targetIndex;
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);
    List<Assignment> lastShownList = model.getFilteredAssignmentList();

    if (targetIndex.getZeroBased() >= lastShownList.size()) {
      throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
    }

    Assignment assignmentToDelete = lastShownList.get(targetIndex.getZeroBased());
    model.deleteAssignment(assignmentToDelete);
    return new CommandResult(String.format(MESSAGE_DELETE_ASSIGNMENT_SUCCESS, assignmentToDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteAssignmentCommand // instanceof handles nulls
        && targetIndex.equals(((DeleteAssignmentCommand) other).targetIndex)); // state check
  }
}
