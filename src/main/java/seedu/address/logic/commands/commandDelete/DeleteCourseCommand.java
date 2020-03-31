package seedu.address.logic.commands.commandDelete;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddCourseCommand;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.person.ID;

/**
 * Deletes a course identified using it's displayed index.
 */
public class DeleteCourseCommand extends DeleteCommand {

  public static final String COMMAND_WORD = "delete-course";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the course identified by the index number used in the displayed course list.\n"
      + "Parameters: INDEX (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 1";

  public static final String MESSAGE_DELETE_COURSE_SUCCESS = "Deleted Assignment: %1$s";

  private Index targetIndex;

  private Course toDelete;

  public DeleteCourseCommand(Index targetIndex) {
    this.targetIndex = targetIndex;
  }

  public DeleteCourseCommand(Course toDelete) {
    this.toDelete = toDelete;
  }

  @Override
  protected void preprocessUndoableCommand(Model model) throws CommandException {
    List<Course> lastShownList = model.getFilteredCourseList();

    if (this.toDelete == null) {
      if (targetIndex.getZeroBased() >= lastShownList.size()) {
        throw new CommandException(Messages.MESSAGE_INVALID_COURSE_DISPLAYED_INDEX);
      }
      this.toDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    if (this.targetIndex == null) {
      this.targetIndex = getIndex(lastShownList);
    }

  }

  @Override
  protected void generateOppositeCommand() throws CommandException {
    oppositeCommand = new AddCourseCommand(toDelete, targetIndex.getZeroBased());
  }

  // TODO: Find way to abstract this
  public Index getIndex(List<Course> lastShownList) throws CommandException {
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
    return new CommandResult(String.format(MESSAGE_DELETE_COURSE_SUCCESS, this.toDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteCourseCommand // instanceof handles nulls
        && targetIndex.equals(((DeleteCourseCommand) other).targetIndex)); // state check
  }
}
