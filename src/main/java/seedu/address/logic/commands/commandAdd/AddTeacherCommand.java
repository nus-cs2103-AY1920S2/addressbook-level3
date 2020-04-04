package seedu.address.logic.commands.commandAdd;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandDelete.DeleteTeacherCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelStaff.Staff;

/**
 * Adds a teacher to the address book.
 */
public class AddTeacherCommand extends AddCommand {

  public static final String COMMAND_WORD = "add-staff";

  public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a staff to the address book. "
      + "Parameters: "
      + PREFIX_NAME + "NAME "
      + PREFIX_LEVEL + "LEVEL "
      + PREFIX_GENDER + "GENDER "
      + PREFIX_PHONE + "PHONE "
      + PREFIX_EMAIL + "EMAIL "
      + PREFIX_SALARY + "SALARY "
      + PREFIX_ADDRESS + "ADDRESS "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_NAME + "Bob Ross "
      + PREFIX_LEVEL + "teacher "
      + PREFIX_GENDER + "m "
      + PREFIX_PHONE + "98765432 "
      + PREFIX_EMAIL + "bob.ross@gmail.com "
      + PREFIX_SALARY + "1000 "
      + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
      + PREFIX_TAG + "LovesArt "
      + PREFIX_TAG + "Friendly";

  public static final String MESSAGE_SUCCESS = "New staff added: %1$s";
  public static final String MESSAGE_DUPLICATE_STAFF = "This staff already exists in the address book";

  private final Staff toAdd;
  private Integer index;

  /**
   * Creates an AddCommand to add the specified {@code Staff}
   */
  public AddTeacherCommand(Staff teacher) {
    requireNonNull(teacher);
    toAdd = teacher;
  }

  public AddTeacherCommand(Staff teacher, Integer index) {
    requireNonNull(teacher);
    toAdd = teacher;
    this.index = index;
  }

  protected void generateOppositeCommand() throws CommandException {
    oppositeCommand = new DeleteTeacherCommand(this.toAdd);
  }

  @Override
  public CommandResult executeUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);
    if (model.has(toAdd)) {
      throw new CommandException(MESSAGE_DUPLICATE_STAFF);
    }

    if (index == null) {
      model.add(toAdd);
    } else {
      model.addAtIndex(toAdd, index);
    }
    return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AddTeacherCommand // instanceof handles nulls
        && toAdd.equals(((AddTeacherCommand) other).toAdd));
  }
}
