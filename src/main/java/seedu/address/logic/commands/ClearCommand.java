package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_FORCE_CLEAR;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORDER_BOOK;
import static seedu.address.logic.parser.CliSyntax.FLAG_RETURN_BOOK;

import java.util.HashSet;

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
            return new CommandResult(MESSAGE_ENQUIRY_BOTH_BOOK, false, false, true, false);
        }

        if (flags.contains(FLAG_FORCE_CLEAR.toString())) {
            if (flags.contains(FLAG_ORDER_BOOK.toString())) {
                model.setOrderBook(new OrderBook());
                return new CommandResult(MESSAGE_SUCCESS_ORDER_BOOK);
            }
            if (flags.contains(FLAG_RETURN_BOOK.toString())) {
                model.setReturnOrderBook(new ReturnOrderBook());
                return new CommandResult(MESSAGE_SUCCESS_RETURN_BOOK);
            }
            model.setOrderBook(new OrderBook());
            model.setReturnOrderBook(new ReturnOrderBook());
            return new CommandResult(MESSAGE_SUCCESS_BOTH_BOOK);
        }

        if (flags.contains(FLAG_ORDER_BOOK.toString())) {
            return new CommandResult(MESSAGE_ENQUIRY_ORDER_BOOK, false, false, true, false);
        }
        return new CommandResult(MESSAGE_ENQUIRY_RETURN_BOOK, false, false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearCommand // instanceof handles nulls
                && flags.equals(((ClearCommand) other).flags));
    }
}
