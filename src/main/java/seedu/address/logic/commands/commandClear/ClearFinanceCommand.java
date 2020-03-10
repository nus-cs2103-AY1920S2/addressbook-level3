package seedu.address.logic.commands.commandClear;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.FinanceAddressBook;

/**
 * Clears the address book.
 */
public class ClearFinanceCommand extends Command {

  public static final String COMMAND_WORD = "clear-finance";
  public static final String MESSAGE_SUCCESS = "Finance Address book has been cleared!";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.setFinanceAddressBook(new FinanceAddressBook());
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
