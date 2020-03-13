package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.Finance;
import seedu.address.model.modelFinance.FinanceNameContainsKeywordsPredicate;

/**
 * Finds and lists all finances in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class CalculateExpensesFinanceCommand extends Command {

  public static final String COMMAND_WORD = "expenses";

  public static final String MESSAGE_USAGE =
      COMMAND_WORD + ": Finds all finances whose names contain any of "
          + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
          + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
          + "Example: " + COMMAND_WORD + " alice bob charlie";


  @Override
  public CommandResult execute(Model model) {
    requireNonNull(model);
    ObservableList<Finance> filteredFinances = model.getFilteredFinanceList();
    for (Finance finance : filteredFinances){
      System.out.println(finance.getAmount());
    }

    return new CommandResult(
        String.format(Messages.MESSAGE_FINANCES_LISTED_OVERVIEW,
            model.getFilteredFinanceList().size()));
  }

  @Override
  public boolean equals(Object other) {
    return other == this // short circuit if same object
        || (other instanceof CalculateExpensesFinanceCommand); // instanceof handles nulls
  }
}
