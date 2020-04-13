package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.model.Filter;
import seedu.expensela.model.Model;
import seedu.expensela.model.transaction.CategoryEqualsKeywordPredicate;
import seedu.expensela.model.transaction.DateEqualsKeywordPredicate;
import seedu.expensela.model.transaction.Transaction;

/**
 * Finds and lists all transactions in expenseLa whose category is of the argument keyword.
 * Keyword matching is case insensitive.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions whose category/date is of "
            + "the specified keyword (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " c/GROCERIES m/2020-03";

    private final Predicate<Transaction> categoryPredicate;
    private final Predicate<Transaction> datePredicate;

    public FilterCommand(CategoryEqualsKeywordPredicate categoryPredicate, DateEqualsKeywordPredicate datePredicate) {
        this.categoryPredicate = categoryPredicate;
        this.datePredicate = datePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFilter(new Filter(categoryPredicate, datePredicate));

        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTION_LISTED_OVERVIEW, model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterCommand // instanceof handles nulls
                && categoryPredicate.equals(((FilterCommand) other).categoryPredicate)
                && datePredicate.equals(((FilterCommand) other).datePredicate)); // state check
    }
}
