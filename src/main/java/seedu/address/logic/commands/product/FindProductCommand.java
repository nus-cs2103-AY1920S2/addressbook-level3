package seedu.address.logic.commands.product;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.product.DescriptionContainsKeywordsPredicate;

/**
 * Finds and lists all products in product list whose description contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindProductCommand extends Command {

    public static final String COMMAND_WORD = "findp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all products whose descriptions contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [KEYWORD]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final DescriptionContainsKeywordsPredicate predicate;

    public FindProductCommand(DescriptionContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredProductList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PRODUCTS_LISTED_OVERVIEW, model.getFilteredProductList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindProductCommand // instanceof handles nulls
                && predicate.equals(((FindProductCommand) other).predicate)); // state check
    }
}
