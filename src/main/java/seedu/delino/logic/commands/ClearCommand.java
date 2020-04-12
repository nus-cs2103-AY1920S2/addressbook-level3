package seedu.delino.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.delino.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.delino.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.delino.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.HashSet;
import java.util.logging.Logger;

import seedu.delino.commons.core.LogsCenter;
import seedu.delino.model.Model;
import seedu.delino.model.OrderBook;
import seedu.delino.model.ReturnOrderBook;

//@@author Exeexe93
/**
 * Clears the order book and return order book.
 *
 * Order book and return order book is for storage, while order list and return order list is for the list
 * displaying to the user.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clear either both order list and return order list or one of them.\n"
            + "Parameters: -o/-r/-f\n"
            + "Example: " + COMMAND_WORD + " -o -f";
    public static final String MESSAGE_SUCCESS_ORDER_LIST = "Order list has been cleared!";
    public static final String MESSAGE_SUCCESS_RETURN_LIST = "Return order list has been cleared!";
    public static final String MESSAGE_SUCCESS_BOTH_LIST = "Both order lists have been cleared!";
    public static final String MESSAGE_ENQUIRY_BOTH_LIST = "Are you sure you want to clear both order list and "
            + "return order list?";
    public static final String MESSAGE_ENQUIRY_ORDER_LIST = "Are you sure you want to clear order list?";
    public static final String MESSAGE_ENQUIRY_RETURN_LIST = "Are you sure you want to clear return list?";
    private static final Logger logger = LogsCenter.getLogger(ClearCommand.class);
    private final HashSet<String> flags;

    public ClearCommand(HashSet<String> flags) {
        this.flags = flags;
    }

    public HashSet<String> getFlags() {
        return this.flags;
    }

    @Override
    public CommandResult execute(Model model) {

        requireNonNull(model);

        if (flags == null) {
            logger.fine("No flag encountered");
            logger.fine("Send confirmation message to user to clear both order book and return order book");
            return new CommandResult(MESSAGE_ENQUIRY_BOTH_LIST, false, false, true, false);
        }

        if (flags.contains(FLAG_FORCE_CLEAR.toString())) {
            return updateModel(model);
        }

        if (flags.contains(FLAG_ORDER_BOOK.toString())) {
            logger.fine("Only '-o' flag encountered");
            logger.fine("Send confirmation message to user to clear order book");
            return new CommandResult(MESSAGE_ENQUIRY_ORDER_LIST, false, false, true, false);
        }
        logger.fine("Only '-r' flag encountered");
        logger.fine("Send confirmation message to user to clear return order book");
        return new CommandResult(MESSAGE_ENQUIRY_RETURN_LIST, false, false, true, false);
    }

    /**
     * Update the model based on the flag received.
     * @param model model to be updated.
     * @return CommandResult with the success message back to user.
     */
    private CommandResult updateModel(Model model) {
        if (flags.contains(FLAG_ORDER_BOOK.toString())) {
            logger.fine("'-o' and '-f' flags encountered");
            logger.fine("Clearing order book...");

            model.setOrderBook(new OrderBook());
            return new CommandResult(MESSAGE_SUCCESS_ORDER_LIST);
        }
        if (flags.contains(FLAG_RETURN_BOOK.toString())) {
            logger.fine("'-r' and '-f' flags encountered");
            logger.fine("Clearing return order book...");

            model.setReturnOrderBook(new ReturnOrderBook());
            return new CommandResult(MESSAGE_SUCCESS_RETURN_LIST);
        }
        logger.fine("Only '-f' flag encountered");
        logger.fine("Clearing both order book and return order book...");

        model.setOrderBook(new OrderBook());
        model.setReturnOrderBook(new ReturnOrderBook());
        return new CommandResult(MESSAGE_SUCCESS_BOTH_LIST);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearCommand // instanceof handles nulls
                && flags.equals(((ClearCommand) other).flags));
    }
}
