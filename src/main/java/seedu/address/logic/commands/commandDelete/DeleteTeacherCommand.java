package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelStaff.Staff;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a teacher identified using it's displayed index from the address book.
 */
public class DeleteTeacherCommand extends DeleteCommand {

  public static final String COMMAND_WORD = "delete-teacher";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the teacher identified by the index number used in the displayed teacher list.\n"
      + "Parameters: INDEX (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 1";

  public static final String MESSAGE_DELETE_TEACHER_SUCCESS = "Deleted Teacher: %1$s";

  private final Index targetIndex;

  public DeleteTeacherCommand(Index targetIndex) {
    this.targetIndex = targetIndex;
  }

  protected void generateOppositeCommand() throws CommandException {

  }

  @Override
  public CommandResult executeUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);
    List<Staff> lastShownList = model.getFilteredStaffList();

    if (targetIndex.getZeroBased() >= lastShownList.size()) {
      throw new CommandException(Messages.MESSAGE_INVALID_TEACHER_DISPLAYED_INDEX);
    }

    Staff teacherToDelete = lastShownList.get(targetIndex.getZeroBased());
    model.delete(teacherToDelete);
    return new CommandResult(String.format(MESSAGE_DELETE_TEACHER_SUCCESS, teacherToDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteTeacherCommand // instanceof handles nulls
        && targetIndex.equals(((DeleteTeacherCommand) other).targetIndex)); // state check
  }
}
