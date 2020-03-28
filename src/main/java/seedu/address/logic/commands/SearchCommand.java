package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.order.OrderContainsKeywordsPredicate;
import seedu.address.model.order.returnorder.ReturnOrderContainsKeywordsPredicate;

/**
 * Finds and lists all orders in order book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Default, search command searches for all orders "
        + "that contain any of the specified keywords in any of it's field (case-insensitive) "
        + "and displays them as a list with index numbers.\n"
        + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
        + "Example: " + COMMAND_WORD + " alice bob charlie 999 Geylang\n"
        + "If given a Prefix followed by a set of keywords, "
        + "only orders with that set of keywords under that specific prefix will be displayed.\n"
        + "However, do note that adding any KEYWORD before any prefix given will result in default search "
        + "and everything given with prefixes after that will not be considered!";

    private final OrderContainsKeywordsPredicate orderPredicate;
    private final ReturnOrderContainsKeywordsPredicate returnPredicate;

    public SearchCommand(OrderContainsKeywordsPredicate orderPredicate,
                         ReturnOrderContainsKeywordsPredicate returnPredicate) {
        this.orderPredicate = orderPredicate;
        this.returnPredicate = returnPredicate;
    }

    public SearchCommand(OrderContainsKeywordsPredicate orderPredicate) {
        this.orderPredicate = orderPredicate;
        this.returnPredicate = null;
    }

    public SearchCommand(ReturnOrderContainsKeywordsPredicate returnPredicate) {
        this.returnPredicate = returnPredicate;
        this.orderPredicate = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (orderPredicate != null) {
            model.updateFilteredOrderList(orderPredicate);
        }
        if (returnPredicate != null) {
            model.updateFilteredReturnOrderList(returnPredicate);
        }

        if (returnPredicate != null && orderPredicate != null) {
            return new CommandResult(
                String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW
                        + System.lineSeparator()
                        + Messages.MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW,
                    model.getFilteredOrderList().size(),
                    model.getFilteredReturnOrderList().size()));
        }

        return orderPredicate == null
            ? new CommandResult(
            String.format(
                Messages.MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW, model.getFilteredReturnOrderList().size()))
            : new CommandResult(
            String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW, model.getFilteredOrderList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SearchCommand // instanceof handles nulls
            && orderPredicate.equals(((SearchCommand) other).orderPredicate)); // state check
    }
}
