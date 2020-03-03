package seedu.address.logic.commands.commandClear;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelTeacher.TeacherAddressBook;

/**
 * Clears the address book.
 */
public class ClearTeacherCommand extends Command {

  public static final String COMMAND_WORD = "clear-teacher";
  public static final String MESSAGE_SUCCESS = "Teacher Address book has been cleared!";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.setTeacherAddressBook(new TeacherAddressBook());
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
