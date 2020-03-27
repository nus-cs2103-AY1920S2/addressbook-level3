package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.OrderBook;

/**
 * Clears the order book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clear the order book list.\n"
            + "Parameters: -f\n"
            + "Example: " + COMMAND_WORD + " -f";
    public static final String MESSAGE_SUCCESS = "Order book has been cleared!";
    public static final String MESSAGE_ENQUIRY = "Are you sure you want to clear the order book list?";
    private final String flag;

    public ClearCommand(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return this.flag;
    }

    @Override
    public CommandResult execute(Model model) {

        requireNonNull(model);

        if (flag == null) {
            return new CommandResult(MESSAGE_ENQUIRY, false, false, true, false);
        }

        model.setOrderBook(new OrderBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClearCommand // instanceof handles nulls
                && flag.equals(((ClearCommand) other).flag));
    }
}
