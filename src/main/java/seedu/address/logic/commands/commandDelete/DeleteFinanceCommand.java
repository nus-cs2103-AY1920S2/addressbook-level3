package seedu.address.logic.commands.commandDelete;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.Finance;

/**
 * Deletes a finance identified using it's displayed index from the address book.
 */
public class DeleteFinanceCommand extends Command {

  public static final String COMMAND_WORD = "delete-finance";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the finance identified by the index number used in the displayed finance list.\n"
      + "Parameters: INDEX (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 1";

  public static final String MESSAGE_DELETE_FINANCE_SUCCESS = "Deleted Finance: %1$s";

  private final Index targetIndex;

  public DeleteFinanceCommand(Index targetIndex) {
    this.targetIndex = targetIndex;
  }

  @Override
  public CommandResult execute(Model model) throws CommandException {
    requireNonNull(model);
    List<Finance> lastShownList = model.getFilteredFinanceList();

    if (targetIndex.getZeroBased() >= lastShownList.size()) {
      throw new CommandException(Messages.MESSAGE_INVALID_FINANCE_DISPLAYED_INDEX);
    }

    Finance financeToDelete = lastShownList.get(targetIndex.getZeroBased());
    model.deleteFinance(financeToDelete);
    return new CommandResult(String.format(MESSAGE_DELETE_FINANCE_SUCCESS, financeToDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteFinanceCommand // instanceof handles nulls
        && targetIndex.equals(((DeleteFinanceCommand) other).targetIndex)); // state check
  }
}
