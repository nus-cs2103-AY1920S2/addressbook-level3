package seedu.address.logic.commands.commandFind;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.modelFinance.FinanceNameContainsKeywordsPredicate;

import static java.util.Objects.requireNonNull;

/**
 * Finds and lists all finances in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindFinanceCommand extends Command {

    public static final String COMMAND_WORD = "find-finance";

    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Finds all finances whose names contain any of "
                    + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
                    + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
                    + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final FinanceNameContainsKeywordsPredicate predicate;

    public FindFinanceCommand(FinanceNameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredFinanceList(predicate);
        model.getMainWindow().callSwitchToFinance();
        return new CommandResult(
                String.format(Messages.MESSAGE_FINANCES_LISTED_OVERVIEW,
                        model.getFilteredFinanceList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindFinanceCommand // instanceof handles nulls
                && predicate.equals(((FindFinanceCommand) other).predicate)); // state check
    }
}
