package seedu.delino.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.delino.commons.core.LogsCenter;
import seedu.delino.commons.core.Messages;
import seedu.delino.model.Model;
import seedu.delino.model.parcel.OrderContainsKeywordsPredicate;
import seedu.delino.model.parcel.ReturnOrderContainsKeywordsPredicate;

//@@author khsc96
/**
 *
 * Finds and lists all orders in order book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    private static final String NEWLINE = System.lineSeparator();
    private static final Logger LOGGER = LogsCenter.getLogger(SearchCommand.class.getName());

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Default, search command searches for all parcels "
        + "(orders/returns) that contain any of the specified keywords in any of it's field (case-insensitive) "
        + "and displays them as a list with index numbers." + NEWLINE
        + "If -o flag is given order is searched, If -r flag is given return is searched." + NEWLINE
        + "Parameters: [FLAG] ORDER_ATTRIBUTE_PREFIX/KEYWORD [ORDER_ATTRIBUTE_PREFIX/MORE_KEYWORDS]..." + NEWLINE
        + "Example: " + COMMAND_WORD + " -o alice bob charlie 999 Geylang" + NEWLINE
        + "If given a Prefix followed by a set of keywords, "
        + "only orders with that set of keywords under that specific prefix will be displayed." + NEWLINE
        + "However, do note that adding any KEYWORD before any prefix given will result in default search "
        + "and everything given with prefixes after that will not be considered!";

    public static final String MULTIPLE_FLAGS_DETECTED = "Different flags detected, please check your input." + NEWLINE
        + "Format example: " + COMMAND_WORD + " -o alice bob charlie 999 Geylang" + NEWLINE
        + "OR " + COMMAND_WORD + " -r alice bob charlie 999 Geylang" + NEWLINE
        + "OR " + COMMAND_WORD + " alice bob charlie 999 Geylang" + NEWLINE;

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
            LOGGER.fine("Search perform on both order list and return list");
            return new CommandResult(
                String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW
                        + System.lineSeparator()
                        + Messages.MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW,
                    model.getFilteredOrderList().size(),
                    model.getFilteredReturnOrderList().size()));
        }
        LOGGER.fine("Specific search is performed.");
        return orderPredicate == null
            ? new CommandResult(
            String.format(Messages.MESSAGE_RETURN_ORDERS_LISTED_OVERVIEW, model.getFilteredReturnOrderList().size()))
            : new CommandResult(
            String.format(Messages.MESSAGE_ORDERS_LISTED_OVERVIEW, model.getFilteredOrderList().size()));
    }

    @Override
    public boolean equals(Object other) {
        boolean shortCircuitCheck = other == this;
        if (orderPredicate == null) {
            assert (returnPredicate != null);
            return shortCircuitCheck
                || (other instanceof SearchCommand
                && (((SearchCommand) other).orderPredicate == null)
                && returnPredicate.equals(((SearchCommand) other).returnPredicate));
        }
        if (returnPredicate == null) {
            return shortCircuitCheck
                || (other instanceof SearchCommand
                && (((SearchCommand) other).returnPredicate == null)
                && orderPredicate.equals(((SearchCommand) other).orderPredicate));
        }

        return shortCircuitCheck // short circuit if same object
            || (other instanceof SearchCommand // instanceof handles nulls
            && orderPredicate.equals(((SearchCommand) other).orderPredicate)
            && returnPredicate.equals(((SearchCommand) other).returnPredicate)); // state check
    }
}
