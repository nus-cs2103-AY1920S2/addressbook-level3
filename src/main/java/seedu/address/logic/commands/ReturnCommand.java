package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RETURN_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.order.Order;
import seedu.address.model.order.returnorder.ReturnOrder;

/**
 * Adds a order to the order book.
 */
public class ReturnCommand extends Command {

    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns an order and adds it to the returns book. "
            + "Parameters: "
            + PREFIX_TID + "TRANSACTION_ID"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_RETURN_TIMESTAMP + "Delivery_DATE_&_TIME "
            + PREFIX_WAREHOUSE + "WAREHOUSE_LOCATION "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TYPE + "TYPE_OF_ITEM] "
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TID + "A999999 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_EMAIL + "johndoe@gmail.com "
            + PREFIX_RETURN_TIMESTAMP + "2019-12-02 1500 "
            + PREFIX_WAREHOUSE + "5 Toh Guan Rd E, #02-30 S608831 "
            + PREFIX_COMMENT + "NIL "
            + PREFIX_TYPE + "glass";

    public static final String MESSAGE_SUCCESS = "This order has been marked as a return: %1$s";
    public static final String MESSAGE_DUPLICATE_RETURN = "This return already exists in the returns book";

    private final Order toReturn;
    private final ReturnOrder toBeCreated;

    /**
     * Creates an ReturnCommand to add the specified {@code Order}
     */
    public ReturnCommand(ReturnOrder returnOrder) {
        requireNonNull(returnOrder);
        toBeCreated = returnOrder;
        toReturn = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasReturnOrder(toBeCreated)) {
            throw new CommandException(MESSAGE_DUPLICATE_RETURN);
        }

        model.addReturnOrder(toBeCreated);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toBeCreated));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReturnCommand // instanceof handles nulls
                && toReturn.equals(((ReturnCommand) other).toReturn));
    }
}
