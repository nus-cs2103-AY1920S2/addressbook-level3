package seedu.address.logic.commands.commandList;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COURSES;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCourseCommand extends Command {

  public static final String COMMAND_WORD = "list-course";

  public static final String MESSAGE_SUCCESS = "Listed all courses";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.updateFilteredCourseList(PREDICATE_SHOW_ALL_COURSES);
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
