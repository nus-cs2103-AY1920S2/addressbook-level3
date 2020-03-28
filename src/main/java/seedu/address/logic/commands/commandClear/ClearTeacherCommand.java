package seedu.address.logic.commands.commandClear;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelStaff.StaffAddressBook;

/**
 * Clears the address book.
 */
public class ClearTeacherCommand extends ClearCommand {

  public static final String COMMAND_WORD = "clear-teachers";
  public static final String MESSAGE_SUCCESS = "All teachers in the database has been removed!";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.setStaffAddressBook(new StaffAddressBook());
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
