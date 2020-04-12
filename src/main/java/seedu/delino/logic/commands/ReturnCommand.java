package seedu.delino.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.delino.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.delino.logic.commands.exceptions.CommandException;
import seedu.delino.model.Model;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.parcelattributes.TimeStamp;
import seedu.delino.model.parcel.parcelattributes.TransactionId;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

//@@author cherweijie
/**
 * Adds a order to the order book.
 */
public class ReturnCommand extends Command {

    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": With the return command, you can either create a new"
            + " order or convert an existing delivered order into a return order. "
            + "Parameters for creating a new return order: "
            + PREFIX_TID + "TRANSACTION_ID "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_RETURN_TIMESTAMP + "RETURN_DATE_&_TIME "
            + PREFIX_WAREHOUSE + "WAREHOUSE_LOCATION "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TYPE + "TYPE_OF_ITEM] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TID + "A999999 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ADDRESS + "311 Clementi Ave 2 #02-25 S120363 "
            + PREFIX_EMAIL + "johndoe@gmail.com "
            + PREFIX_RETURN_TIMESTAMP + "2020-05-05 1500 "
            + PREFIX_WAREHOUSE + "5 Toh Guan Rd E #02-30 S608831 "
            + PREFIX_COMMENT + "NIL "
            + PREFIX_TYPE + "glass"
            + "\n"
            + "Parameters for converting a delivered order into a return order: "
            + PREFIX_TID + "TRANSACTION_ID "
            + PREFIX_RETURN_TIMESTAMP + "RETURN_DATE_&_TIME "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TID + "A999999 "
            + PREFIX_RETURN_TIMESTAMP + "2020-05-05 1500 ";

    public static final String MESSAGE_SUCCESS = "This return order has been created: %1$s";
    public static final String MESSAGE_DUPLICATE_RETURN = "This return order already exists in the returns book";
    public static final String MESSAGE_ORDER_NOT_DELIVERED = "This order was not delivered. Return Order cannot be"
            + " created";
    public static final String MESSAGE_ORDER_TRANSACTION_ID_NOT_VALID = "The input Transaction ID is not valid.";
    public static final String MESSAGE_ORDER_TIMESTAMP_INVALID = "The input time stamp should be after the delivery "
            + "time stamp.";

    private static final Logger logger = Logger.getLogger(ReturnCommand.class.getName());

    private ReturnOrder toBeCreated;
    private final TimeStamp timeStamp;
    private final TransactionId tid;

    /**
     * Creates an ReturnCommand to add the specified {@code Order}
     */
    public ReturnCommand(ReturnOrder returnOrder, TransactionId tid, TimeStamp timeStamp) {
        requireNonNull(tid);
        requireNonNull(timeStamp);
        toBeCreated = returnOrder;
        this.tid = tid;
        this.timeStamp = timeStamp;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (isReturnOrderNotPresent()) {
            logger.fine("Return Order not present, return Command is being executed.");
            Order orderToBeReturned = getOrderByTransactionId(model);
            checkIfOrderWasDelivered(model);
            checkIfNewTimeStampIsValid(model);
            toBeCreated = new ReturnOrder(orderToBeReturned);
            toBeCreated.setTimestamp(timeStamp);
            model.deleteOrder(orderToBeReturned);
        }
        logger.fine("Execute return command.");
        checkForDuplicateReturnOrder(model);
        model.addReturnOrder(toBeCreated);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toBeCreated));
    }

    /**
     * User input time stamp is assumed to be valid unless it is before the old time stamp of the order.
     * @param model The current model of Delino.
     * @throws CommandException
     */
    private void checkIfNewTimeStampIsValid(Model model) throws CommandException {
        Order orderToBeReturned = getOrderByTransactionId(model);
        LocalDateTime newTimeStamp = timeStamp.getOrderTimeStamp();
        LocalDateTime oldTimeStamp = orderToBeReturned.getTimestamp().getOrderTimeStamp();

        if (newTimeStamp.compareTo(oldTimeStamp) <= 0) {
            throw new CommandException(MESSAGE_ORDER_TIMESTAMP_INVALID);
        }
    }

    /**
     * Checks if the newly created return order exists in the return order list.
     * @param model The current model of Delino.
     * @throws CommandException if the same return order exists in the return order list.
     */
    private void checkForDuplicateReturnOrder(Model model) throws CommandException {
        logger.fine("Check if it causes duplicate return orders in the return order list.");
        if (model.hasParcel(toBeCreated)) {
            logger.info("Exception thrown due to duplicate return orders.");
            throw new CommandException(MESSAGE_DUPLICATE_RETURN);
        }
    }

    /**
     * Checks if order was delivered. An order can only be returned after delivery.
     * @param model The current model of Delino.
     * @throws CommandException if order was not delivered.
     */
    private void checkIfOrderWasDelivered(Model model) throws CommandException {
        logger.fine("Check if order was delivered.");
        Order orderToBeReturned = getOrderByTransactionId(model);
        if (!orderToBeReturned.isDelivered()) {
            logger.info("Exception thrown as order was not delivered. Unable to return.");
            throw new CommandException(MESSAGE_ORDER_NOT_DELIVERED);
        }
    }

    /**
     * Checks if the return order is null. Returns true if return order is not present.
     * @return
     */
    private boolean isReturnOrderNotPresent() {
        logger.fine("Check if return order is not present in the return order list.");
        return toBeCreated == null;
    }

    /**
     * Gets the order from the model based on its Transaction ID.
     * @param model The current Model.
     * @return The order taken from the order book based on the transaction id input by user.
     * @throws CommandException
     */
    private Order getOrderByTransactionId(Model model) throws CommandException {
        logger.fine("Get the order from the model based on its transaction id input by user.");
        List<Order> ordersToBeReturned = model.getOrderBook()
                .getOrderList()
                .stream()
                .filter(order -> order.getTid().equals(tid))
                .collect(Collectors.toList());
        if (ordersToBeReturned.isEmpty()) {
            logger.info("If order list is empty, throw error.");
            throw new CommandException(MESSAGE_ORDER_TRANSACTION_ID_NOT_VALID);
        }
        logger.info("List of orders > 1");
        assert(ordersToBeReturned.size() <= 1);
        Order orderToBeReturned = ordersToBeReturned.get(0);
        return orderToBeReturned;
    }

    @Override
    public boolean equals(Object other) {
        boolean shortCircuitCheck = other == this; // short circuit if same object
        if (shortCircuitCheck) {
            return true;
        }
        if (toBeCreated == null) {
            return ((other instanceof ReturnCommand && ((ReturnCommand) other).toBeCreated == null)
                    && tid.equals(((ReturnCommand) other).tid));
        } else {
            return (other instanceof ReturnCommand
                    && (toBeCreated.equals(((ReturnCommand) other).toBeCreated))
                    && tid.equals(((ReturnCommand) other).tid));
        }
    }
}
