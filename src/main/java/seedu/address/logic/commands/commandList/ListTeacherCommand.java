package seedu.address.logic.commands.commandList;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TEACHERS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListTeacherCommand extends ListCommand {

  public static final String COMMAND_WORD = "list-teacher";

  public static final String MESSAGE_SUCCESS = "Listed all teachers";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.updateFilteredTeacherList(PREDICATE_SHOW_ALL_TEACHERS);
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
