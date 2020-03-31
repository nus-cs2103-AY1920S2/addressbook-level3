package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.HashSet;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Model;
import seedu.address.model.OrderBook;
import seedu.address.model.ReturnOrderBook;

/**
 * Clears the order book.
 */
public class ClearCommand extends Command {
    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clear the order book list.\n"
            + "Parameters: -o/-r/-f\n"
            + "Example: " + COMMAND_WORD + " -o -f";
    public static final String MESSAGE_SUCCESS_ORDER_BOOK = "Order book has been cleared!";
    public static final String MESSAGE_SUCCESS_RETURN_BOOK = "Return order book has been cleared!";
    public static final String MESSAGE_SUCCESS_BOTH_BOOK = "Both order books have been cleared!";
    public static final String MESSAGE_ENQUIRY_BOTH_BOOK = "Are you sure you want to clear both order book and "
            + "return order book?";
    public static final String MESSAGE_ENQUIRY_ORDER_BOOK = "Are you sure you want to clear order book?";
    public static final String MESSAGE_ENQUIRY_RETURN_BOOK = "Are you sure you want to clear return order book?";
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
            return new CommandResult(MESSAGE_ENQUIRY_BOTH_BOOK, false, false, true, false);
        }

        if (flags.contains(FLAG_FORCE_CLEAR.toString())) {
            return updateModel(model);
        }

        if (flags.contains(FLAG_ORDER_BOOK.toString())) {
            logger.fine("Only '-o' flag encountered");
            logger.fine("Send confirmation message to user to clear order book");
            return new CommandResult(MESSAGE_ENQUIRY_ORDER_BOOK, false, false, true, false);
        }
        logger.fine("Only '-r' flag encountered");
        logger.fine("Send confirmation message to user to clear return order book");
        return new CommandResult(MESSAGE_ENQUIRY_RETURN_BOOK, false, false, true, false);
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
            return new CommandResult(MESSAGE_SUCCESS_ORDER_BOOK);
        }
        if (flags.contains(FLAG_RETURN_BOOK.toString())) {
            logger.fine("'-r' and '-f' flags encountered");
            logger.fine("Clearing return order book...");

            model.setReturnOrderBook(new ReturnOrderBook());
            return new CommandResult(MESSAGE_SUCCESS_RETURN_BOOK);
        }
        logger.fine("Only '-f' flag encountered");
        logger.fine("Clearing both order book and return order book...");

        model.setOrderBook(new OrderBook());
        model.setReturnOrderBook(new ReturnOrderBook());
        return new CommandResult(MESSAGE_SUCCESS_BOTH_BOOK);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearCommand // instanceof handles nulls
                && flags.equals(((ClearCommand) other).flags));
    }
}
