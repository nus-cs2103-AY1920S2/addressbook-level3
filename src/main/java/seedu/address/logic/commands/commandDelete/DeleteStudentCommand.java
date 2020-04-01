package seedu.address.logic.commands.commandDelete;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.commands.commandAdd.AddStudentCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelStudent.Student;

/**
 * Deletes a student identified using it's displayed index from the address book.
 */
public class DeleteStudentCommand extends DeleteCommand {

  public static final String COMMAND_WORD = "delete-student";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the student identified by the index number used in the displayed student list.\n"
      + "Parameters: INDEX (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 1";

  public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

  private Index targetIndex;

  private Student toDelete;

  public DeleteStudentCommand(Index targetIndex) {
    this.targetIndex = targetIndex;
  }

  public DeleteStudentCommand(Student toDelete) {
    this.toDelete = toDelete;
  }

  @Override
  protected void preprocessUndoableCommand(Model model) throws CommandException {
    List<Student> lastShownList = model.getFilteredStudentList();
    if (this.toDelete == null) {
      if (targetIndex.getZeroBased() >= lastShownList.size()) {
        throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
      }

      this.toDelete = lastShownList.get(targetIndex.getZeroBased());
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
        && targetIndex.equals(((DeleteStudentCommand) other).targetIndex)); // state check
  }
}
