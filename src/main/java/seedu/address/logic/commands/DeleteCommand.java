package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;
import seedu.address.model.parcel.order.Order;
import seedu.address.model.parcel.returnorder.ReturnOrder;

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

    public static final String MESSAGE_DELETE_ORDER_SUCCESS = "Deleted Order: %1$s";
    public static final String MESSAGE_INVALID_FLAG = "Invalid flag given!";

    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);
    private final Index targetIndex;
    private final Flag listType;

    public DeleteCommand(Index targetIndex, Flag listType) {
        logger.fine("DeleteCommand constructor invoked");
        this.targetIndex = targetIndex;
        this.listType = listType;
        logger.fine("DeleteCommand constructor successfully executed");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (listType.equals(FLAG_ORDER_BOOK)) {
            logger.fine(String.format("Order list flag (%s) given for deletion", FLAG_ORDER_BOOK));
            return deleteFromOrderList(model);
        }
        if (listType.equals(FLAG_RETURN_BOOK)) {
            logger.fine(String.format("Return order list flag (%s) given for deletion", FLAG_RETURN_BOOK));
            return deleteFromReturnList(model);
        }
        logger.info("Invalid flag given for DeleteCommand execute function");
        throw new CommandException(MESSAGE_INVALID_FLAG);
    }

    /**
     * Delete an order from the order list.
     *
     * @param model used to delete order from
     * @return CommandResult representing the delete operation
     * @throws CommandException invalid {@code targetIndex} given
     */
    private CommandResult deleteFromOrderList(Model model) throws CommandException {
        List<Order> lastShownList = model.getFilteredOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.info("Invalid index given for deleteFromOrderList function");
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        Order orderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteOrder(orderToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ORDER_SUCCESS, orderToDelete));
    }

    /**
     * Delete an order from the return order list.
     *
     * @param model used to delete order from
     * @return CommandResult representing the delete operation
     * @throws CommandException invalid {@code targetIndex} given
     */
    private CommandResult deleteFromReturnList(Model model) throws CommandException {
        List<ReturnOrder> lastShownList = model.getFilteredReturnOrderList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.info("Invalid index given for deleteFromReturnList function");
            throw new CommandException(Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
        }

        ReturnOrder orderToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReturnOrder(orderToDelete);
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
