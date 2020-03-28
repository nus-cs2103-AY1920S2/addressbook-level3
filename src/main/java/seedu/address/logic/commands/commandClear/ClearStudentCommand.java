package seedu.address.logic.commands.commandClear;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelStudent.StudentAddressBook;

/**
 * Clears the Student address book.
 */
public class ClearStudentCommand extends ClearCommand {

  public static final String COMMAND_WORD = "clear-student";
  public static final String MESSAGE_SUCCESS = "All students in the database has been removed!";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.setStudentAddressBook(new StudentAddressBook());
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
