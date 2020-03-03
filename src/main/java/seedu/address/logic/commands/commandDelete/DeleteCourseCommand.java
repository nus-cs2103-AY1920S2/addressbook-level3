package seedu.address.logic.commands.commandDelete;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.Course;

/**
 * Deletes a course identified using it's displayed index from the address book.
 */
public class DeleteCourseCommand extends Command {

  public static final String COMMAND_WORD = "delete-course";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the course identified by the index number used in the displayed course list.\n"
      + "Parameters: INDEX (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 1";

  public static final String MESSAGE_DELETE_COURSE_SUCCESS = "Deleted Course: %1$s";

  private final Index targetIndex;

  public DeleteCourseCommand(Index targetIndex) {
    this.targetIndex = targetIndex;
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);
    List<Course> lastShownList = model.getFilteredCourseList();

    if (targetIndex.getZeroBased() >= lastShownList.size()) {
      throw new CommandException(Messages.MESSAGE_INVALID_COURSE_DISPLAYED_INDEX);
    }

    Course courseToDelete = lastShownList.get(targetIndex.getZeroBased());
    model.deleteCourse(courseToDelete);
    return new CommandResult(String.format(MESSAGE_DELETE_COURSE_SUCCESS, courseToDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteCourseCommand // instanceof handles nulls
        && targetIndex.equals(((DeleteCourseCommand) other).targetIndex)); // state check
  }
}
