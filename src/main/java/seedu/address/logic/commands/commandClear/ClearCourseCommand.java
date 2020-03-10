package seedu.address.logic.commands.commandClear;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelCourse.CourseAddressBook;

/**
 * Clears the address book.
 */
public class ClearCourseCommand extends Command {

  public static final String COMMAND_WORD = "clear-course";
  public static final String MESSAGE_SUCCESS = "Course Address book has been cleared!";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.setCourseAddressBook(new CourseAddressBook());
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
