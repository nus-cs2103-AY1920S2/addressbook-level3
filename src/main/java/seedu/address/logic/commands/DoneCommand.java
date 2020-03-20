package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
/**
 * Adds a order to the order book.
 */
public class DoneCommand extends Command {

    public static final String COMMAND_WORD = "delivered";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks an order as delivered based on its index in the current list.\n"
            + "Parameters: INDEX (must be a positive integer) \n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELIVERED_SUCCESS = "The order has been delivered: %1$s";
    public static final String MESSAGE_ORDER_ALREADY_DELIVERED = "This order was already delivered";

    private final Index targetIndex;

    public DoneCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToBeDelivered = lastShownList.get(targetIndex.getZeroBased());
        if (orderToBeDelivered.isDelivered() == false) {
            model.deliverOrder(orderToBeDelivered);
            model.updateFilteredOrderList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELIVERED_SUCCESS, orderToBeDelivered));
        } else {
            model.updateFilteredOrderList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_ORDER_ALREADY_DELIVERED, orderToBeDelivered));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                && targetIndex.equals(((DoneCommand) other).targetIndex)); // state check
    }
}
