package seedu.address.logic.commands.commandList;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FINANCES;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListFinanceCommand extends Command {

  public static final String COMMAND_WORD = "list-finance";

  public static final String MESSAGE_SUCCESS = "Listed all finances";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    model.updateFilteredFinanceList(PREDICATE_SHOW_ALL_FINANCES);
    return new CommandResult(MESSAGE_SUCCESS);
  }
}
