package seedu.address.logic.commands.commandDelete;

import static java.util.Objects.requireNonNull;

import java.util.List;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandAdd.AddFinanceCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.modelAssignment.Assignment;
import seedu.address.model.modelCourse.Course;
import seedu.address.model.modelFinance.Finance;

/**
 * Deletes a finance identified using it's displayed index from the address book.
 */
public class DeleteFinanceCommand extends DeleteCommand {

  public static final String COMMAND_WORD = "delete-finance";

  public static final String MESSAGE_USAGE = COMMAND_WORD
      + ": Deletes the finance identified by the index number used in the displayed finance list.\n"
      + "Parameters: INDEX (must be a positive integer)\n"
      + "Example: " + COMMAND_WORD + " 1";

  public static final String MESSAGE_DELETE_FINANCE_SUCCESS = "Deleted Finance: %1$s";

  private Index targetIndex;

  private Finance toDelete;

  public DeleteFinanceCommand(Index targetIndex) {
    this.targetIndex = targetIndex;
  }

  public DeleteFinanceCommand(Finance toDelete) {
    this.toDelete = toDelete;
  }

  // TODO: Find way to abstract this
  public Index getIndex(List<Finance> lastShownList) throws CommandException {
    for (int i = 0; i < lastShownList.size(); i++) {
      if (lastShownList.get(i).equals(this.toDelete)) {
        return Index.fromZeroBased(i);
      }
    }
    throw new CommandException("This id not in list");
  }

  @Override
  protected void preprocessUndoableCommand(Model model) throws CommandException {
    List<Finance> lastShownList = model.getFilteredFinanceList();
    if (this.toDelete == null) {
      if (targetIndex.getZeroBased() >= lastShownList.size()) {
        throw new CommandException(Messages.MESSAGE_INVALID_FINANCE_DISPLAYED_INDEX);
      }

      this.toDelete = lastShownList.get(targetIndex.getZeroBased());
    }

    if (this.targetIndex == null) {
      this.targetIndex = getIndex(lastShownList);
    }
  }

  @Override
  protected void generateOppositeCommand() throws CommandException {
    oppositeCommand = new AddFinanceCommand(toDelete, targetIndex.getZeroBased());
  }

  @Override
  public CommandResult executeUndoableCommand(Model model) throws CommandException {
    requireNonNull(model);
    model.delete(this.toDelete);
    return new CommandResult(String.format(MESSAGE_DELETE_FINANCE_SUCCESS, this.toDelete));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof DeleteFinanceCommand // instanceof handles nulls
        && targetIndex.equals(((DeleteFinanceCommand) other).targetIndex)); // state check
  }
}
