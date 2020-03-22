package seedu.address.logic.commands.commandAdd;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelStudent.Student;

/**
 * Adds a student to the address book.
 */
public class AddStudentCommand extends Command {

  public static final String COMMAND_WORD = "add-student";

  public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to the address book. "
      + "Parameters: "
      + PREFIX_NAME + "NAME "
      + PREFIX_STUDENTID + "ID "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_NAME + "John AppleSeed "
      + PREFIX_STUDENTID + "005 "
      + PREFIX_TAG + "Old "
      + PREFIX_TAG + "Lazy";

  public static final String MESSAGE_SUCCESS = "New student added: %1$s";
  public static final String MESSAGE_DUPLICATE_STUDENT = "This student already exists in the address book";

  private final Student toAdd;

  /**
   * Creates an AddCommand to add the specified {@code Student}
   */
  public AddStudentCommand(Student student) {
    requireNonNull(student);
    toAdd = student;
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);

    if (model.hasStudent(toAdd)) {
      throw new CommandException(MESSAGE_DUPLICATE_STUDENT);
    }

    model.addStudent(toAdd);
    return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AddStudentCommand // instanceof handles nulls
        && toAdd.equals(((AddStudentCommand) other).toAdd));
  }
}
