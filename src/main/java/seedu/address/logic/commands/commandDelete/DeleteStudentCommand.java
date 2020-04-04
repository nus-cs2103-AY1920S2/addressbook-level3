package seedu.address.logic.commands.commandDelete;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddStudentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelStudent.Student;
import seedu.address.model.person.ID;

/**
 * Deletes a student identified using it's displayed index from the address book.
 */
public class DeleteStudentCommand extends DeleteCommand {

  public static final String COMMAND_WORD = "delete-student";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the student identified by the ID number used in the displayed student list.\n"
      + "Parameters: ID (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 16100";

  public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

  private ID targetID;
  private Index targetIndex;
  private Student toDelete;

  public DeleteStudentCommand(ID targetID) {
    this.targetID = targetID;
  }

  public DeleteStudentCommand(Student toDelete) {
    this.toDelete = toDelete;
  }

  @Override
  protected void preprocessUndoableCommand(Model model) throws CommandException {
    List<Student> lastShownList = model.getFilteredStudentList();
    if (this.toDelete == null) {
      if (!ID.isValidId(targetID.toString())) {
        throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_ID);
      }
      this.toDelete = getStudent(lastShownList);
    }
    if (this.targetID == null) {
      this.targetID = getID(lastShownList);
    }
    if (this.targetIndex == null) {
      this.targetIndex = getIndex(lastShownList);
    }
  }

  @Override
  protected void generateOppositeCommand() {
    oppositeCommand = new AddStudentCommand(toDelete, targetIndex.getZeroBased());
  }

  // Find way to abstract this
  public Index getIndex(List<Student> lastShownList) throws CommandException {
    for (int i = 0; i < lastShownList.size(); i++) {
      if (lastShownList.get(i).equals(this.toDelete)) {
        return Index.fromZeroBased(i);
      }
    }
    throw new CommandException("This id not in list");
  }

  // Find way to abstract this
  public ID getID(List<Student> lastShownList) throws CommandException {
    for (int i = 0; i < lastShownList.size(); i++) {
      if (lastShownList.get(i).getId().equals(this.toDelete.getId())) {
        return lastShownList.get(i).getId();
      }
    }
    throw new CommandException("Cannot find this student in the list");
  }

  // Find way to abstract this
  public Student getStudent(List<Student> lastShownList) throws CommandException {
    for (int i = 0; i < lastShownList.size(); i++) {
      if (lastShownList.get(i).getId().equals(this.targetID)) {
        return lastShownList.get(i);
      }
    }
    throw new CommandException("This student ID does not exist");
  }

  @Override
  public CommandResult executeUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);
    model.delete(this.toDelete);
    return new CommandResult(String.format(MESSAGE_DELETE_STUDENT_SUCCESS, this.toDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteStudentCommand // instanceof handles nulls
        && targetID.equals(((DeleteStudentCommand) other).targetID)); // state check
  }
}
