package seedu.expensela.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.expensela.commons.core.Messages;
import seedu.expensela.model.Filter;
import seedu.expensela.model.Model;
import seedu.expensela.model.transaction.NameContainsKeywordsPredicate;

/**
 * Finds and lists all transactions in expensela whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " pizza sushi bread";
    public static final String MESSAGE_NOT_FOUND = Messages.MESSAGE_WORD_NOT_FOUND;

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setFilter(new Filter(null, null));
        model.updateFilteredTransactionList(predicate, null);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTION_LISTED_OVERVIEW, model.getFilteredTransactionList().size()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
