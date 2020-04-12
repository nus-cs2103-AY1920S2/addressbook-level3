package seedu.delino.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.delino.model.Model.PREDICATE_SHOW_ALL_ORDERS;
import static seedu.delino.model.Model.PREDICATE_SHOW_ALL_RETURNS;
import static seedu.delino.model.Model.PREDICATE_SHOW_DONE_ORDERS;
import static seedu.delino.model.Model.PREDICATE_SHOW_DONE_RETURNS;
import static seedu.delino.model.Model.PREDICATE_SHOW_UNDONE_ORDERS;
import static seedu.delino.model.Model.PREDICATE_SHOW_UNDONE_RETURNS;

import java.util.logging.Logger;

import seedu.delino.model.Model;

//@@author Amoscheong97
/**
 * Lists all orders in the order book to the user.
 *
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String DONE = "done";

    public static final String UNDONE = "undone";

    public static final String EMPTY_ARGUMENT = "";

    public static final String MESSAGE_SUCCESS = "Listed all orders and return orders.";

    public static final String MESSAGE_SUCCESS_DONE = "Listed all delivered orders and return orders";

    public static final String MESSAGE_SUCCESS_UNDONE = "Listed all undelivered orders and return orders";

    public static final String MESSAGE_FAILURE = "Invalid use of List Command.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List the orders from order list based on arguments. "
            + "Argument: "
            + DONE + "/"
            + UNDONE + "\n"
            + "Example"
            + COMMAND_WORD
            + DONE;

    private static final Logger logger = Logger.getLogger(ListCommand.class.getName());

    private String listArg;

    public ListCommand(String argument) {
        requireNonNull(argument);
        listArg = argument.trim();
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        switch (listArg) {
        case DONE:
            logger.fine("Filter both lists to show delivered orders");
            model.updateFilteredOrderList(PREDICATE_SHOW_DONE_ORDERS);
            model.updateFilteredReturnOrderList(PREDICATE_SHOW_DONE_RETURNS);
            return new CommandResult(MESSAGE_SUCCESS_DONE);

        case UNDONE:
            logger.fine("Filter both lists to show undelivered orders");
            model.updateFilteredOrderList(PREDICATE_SHOW_UNDONE_ORDERS);
            model.updateFilteredReturnOrderList(PREDICATE_SHOW_UNDONE_RETURNS);
            return new CommandResult(MESSAGE_SUCCESS_UNDONE);

        case EMPTY_ARGUMENT:
            logger.fine("Show everything in both lists");
            model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
            model.updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);
            return new CommandResult(MESSAGE_SUCCESS);

        default:
            logger.fine("Invalid syntax for List Command");
            return new CommandResult(MESSAGE_FAILURE);
        }
    }
}
