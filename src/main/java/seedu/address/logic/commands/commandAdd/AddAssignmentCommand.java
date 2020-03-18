package seedu.address.logic.commands.commandAdd;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Adds a ASSIGNMENT to the address book.
 */
public class AddAssignmentCommand extends Command {

  public static final String COMMAND_WORD = "add-assignment";

  public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an assignment to the address book. "
      + "Parameters: "
      + PREFIX_NAME + "NAME "
      + PREFIX_ASSIGNMENTID + "ASSIGNMENTID "
      + PREFIX_DEADLINE + "DEADLINE "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_NAME + "Adversarial Search Assignment 2 "
      + PREFIX_DEADLINE + "2020-12-30 "
      + PREFIX_TAG + "AI "
      + PREFIX_TAG + "Difficult ";

  public static final String MESSAGE_SUCCESS = "New assignment added: %1$s";
  public static final String MESSAGE_DUPLICATE_ASSIGNMENT = "This assignment already exists in the address book";

  private final Assignment toAdd;

  /**
   * Creates an AddCommand to add the specified {@code Assignment}
   */
  public AddAssignmentCommand(Assignment asgmt) {
    requireNonNull(asgmt);
    toAdd = asgmt;
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);

    if (model.hasAssignment(toAdd)) {
      throw new CommandException(MESSAGE_DUPLICATE_ASSIGNMENT);
    }

    model.addAssignment(toAdd);
    return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AddAssignmentCommand // instanceof handles nulls
        && toAdd.equals(((AddAssignmentCommand) other).toAdd));
  }
}
