package seedu.address.logic.commands.commandAdd;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSEID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINANCETYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEACHERID;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.Finance;

/**
 * Adds a finance to the address book.
 */
public class AddFinanceCommand extends AddCommand {
  //If finance type is misc, then Name, Date, Amount must be provided
  //Else if finance type is CourseStudent, then Date, CourseID, StudentID must be provided
  //Else if finance type is CourseTeacher, then Date, CourseID, TeacherID must be provided

  public static final String COMMAND_WORD = "add-finance";

  public static final String MESSAGE_FINANCETYPE = "Finance type ft/ was not specified. ";
  public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a finance to the address book. "
      + "\nType 1: Adding miscellaneous transactions(Specify ft/ as m "
      + "Parameters: "
      + PREFIX_FINANCETYPE + "FINANCETYPE "
      + PREFIX_DATE + "DATE "
      + PREFIX_NAME + "NAME "
      + PREFIX_AMOUNT + "AMOUNT "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_FINANCETYPE + "m "
      + PREFIX_DATE + "2020-12-09 "
      + PREFIX_NAME + "Paid NTU "
      + PREFIX_AMOUNT + "1200 "
      + PREFIX_TAG + "Partnership "
      + PREFIX_TAG + "Monthly "
      + "\nType 2: A student paying for a course(Specify ft/ as cs "
      + "Parameters: "
      + PREFIX_FINANCETYPE + "FINANCETYPE "
      + PREFIX_DATE + "DATE "
      + PREFIX_COURSEID + "COURSEID "
      + PREFIX_STUDENTID + "STUDENTID "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_FINANCETYPE + "cs "
      + PREFIX_DATE + "2020-12-09 "
      + PREFIX_COURSEID + "829 "
      + PREFIX_STUDENTID + "33 "
      + PREFIX_TAG + "Late "
      + "\nType 3: A teacher is paid for teaching a course(Specify ft/ as ct "
      + "Parameters: "
      + PREFIX_FINANCETYPE + "FINANCETYPE "
      + PREFIX_DATE + "DATE "
      + PREFIX_COURSEID + "COURSEID "
      + PREFIX_TEACHERID + "TEACHERID "
      + "[" + PREFIX_TAG + "TAG]...\n"
      + "Example: " + COMMAND_WORD + " "
      + PREFIX_FINANCETYPE + "ct "
      + PREFIX_DATE + "2020-12-09 "
      + PREFIX_COURSEID + "829 "
      + PREFIX_TEACHERID + "21 "
      + PREFIX_TAG + "Early ";


  public static final String MESSAGE_SUCCESS = "New finance added: %1$s";
  public static final String MESSAGE_DUPLICATE_FINANCE = "This finance already exists in the address book";

  private final Finance toAdd;

  /**
   * Creates an AddCommand to add the specified {@code Finance}
   */
  public AddFinanceCommand(Finance finance) {
    requireNonNull(finance);
    toAdd = finance;
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);

    if (model.hasFinance(toAdd)) {
      throw new CommandException(MESSAGE_DUPLICATE_FINANCE);
    }

    model.addFinance(toAdd);
    return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof AddFinanceCommand // instanceof handles nulls
        && toAdd.equals(((AddFinanceCommand) other).toAdd));
  }
}
