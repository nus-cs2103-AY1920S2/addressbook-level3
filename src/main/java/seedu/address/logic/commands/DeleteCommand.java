package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Deletes an order identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String NEWLINE = System.lineSeparator();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the order identified by the index number used in the displayed list."
            + NEWLINE + "A flag must be given to indicate the list to delete from."
            + NEWLINE + "A flag is either -r (for return order list) or -o (order list)"
            + NEWLINE + "Parameters: FLAG (-r or -o) INDEX (must be a positive integer)"
            + NEWLINE + "Example: " + COMMAND_WORD + " -r" + " 1";

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Person: %1$s";

    private final Index targetIndex;
    private final Flag listType;

    public DeleteCommand(Index targetIndex, Flag listType) {
        this.targetIndex = targetIndex;
        this.listType = listType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteOrder(orderToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)
                && listType.equals(((DeleteCommand) other).listType)); // state check
    }
}
