package seedu.address.logic.commands.commandDelete;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddTeacherCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelStaff.Staff;
import seedu.address.model.person.ID;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a teacher identified using it's displayed index from the address book.
 */
public class DeleteTeacherCommand extends DeleteCommand {

  public static final String COMMAND_WORD = "delete-teacher";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the teacher identified by the existing ID number used in the displayed teacher list.\n"
      + "Parameters: ID (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 1";

  public static final String MESSAGE_DELETE_STAFF_SUCCESS = "Deleted Staff: %1$s";

  private ID targetID;
  private Index targetIndex;
  private Staff toDelete;

  public DeleteTeacherCommand(ID targetID) {
    this.targetID = targetID;
  }

  public DeleteTeacherCommand(Staff toDelete) {
    this.toDelete = toDelete;
  }

  @Override
  protected void preprocessUndoableCommand(Model model) throws CommandException {
    List<Staff> lastShownList = model.getFilteredStaffList();
    if (this.toDelete == null) {
      if (!ID.isValidId(targetID.toString())) {
        throw new CommandException(Messages.MESSAGE_INVALID_STAFF_DISPLAYED_ID);
      }
      this.toDelete = getStaff(lastShownList);
    }
    if (this.targetID == null) {
      this.targetID = getID(lastShownList);
    }
    if (this.targetIndex == null) {
      this.targetIndex = getIndex(lastShownList);
    }
  }

  protected void generateOppositeCommand() {
    oppositeCommand = new AddTeacherCommand(toDelete, targetIndex.getZeroBased());
  }

  // Find way to abstract this
  public Index getIndex(List<Staff> lastShownList) throws CommandException {
    for (int i = 0; i < lastShownList.size(); i++) {
      if (lastShownList.get(i).equals(this.toDelete)) {
        return Index.fromZeroBased(i);
      }
    }
    throw new CommandException("This id not in list");
  }

  // Find way to abstract this
  public ID getID(List<Staff> lastShownList) throws CommandException {
    for (Staff staff : lastShownList) {
      if (staff.getId().equals(this.toDelete.getId())) {
        return staff.getId();
      }
    }
    throw new CommandException("Cannot find this student in the list");
  }

  // Find way to abstract this
  public Staff getStaff(List<Staff> lastShownList) throws CommandException {
    for (Staff staff : lastShownList) {
      if (staff.getId().equals(this.targetID)) {
        return staff;
      }
    }
    throw new CommandException("This staff ID does not exist");
  }

  @Override
  public CommandResult executeUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);
    model.delete(this.toDelete);
    return new CommandResult(String.format(MESSAGE_DELETE_STAFF_SUCCESS, this.toDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteTeacherCommand // instanceof handles nulls
        && targetIndex.equals(((DeleteTeacherCommand) other).targetIndex)); // state check
  }
}
