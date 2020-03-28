package seedu.address.logic.commands.commandClear;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.FinanceAddressBook;

/**
 * Clears the finance address book.
 */
public class ClearFinanceCommand extends ClearCommand {

  public static final String COMMAND_WORD = "clear-finances";
  public static final String MESSAGE_SUCCESS = "Database of Finances has been cleared!";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.setFinanceAddressBook(new FinanceAddressBook());
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
