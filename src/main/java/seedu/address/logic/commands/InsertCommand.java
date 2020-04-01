package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERY_TIMESTAMP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WAREHOUSE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Parcel.order.Order;

/**
 * Adds a order to the order book.
 */
public class InsertCommand extends Command {

    public static final String COMMAND_WORD = "insert";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an order to the order book. "
            + "Parameters: "
            + PREFIX_TID + "TRANSACTION_ID "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_DELIVERY_TIMESTAMP + "Delivery_DATE_&_TIME "
            + PREFIX_WAREHOUSE + "WAREHOUSE_LOCATION " + "\n"
            + PREFIX_COD + "CASH_ON_DELIVERY "
            + "[" + PREFIX_COMMENT + "COMMENT] "
            + "[" + PREFIX_TYPE + "TYPE_OF_ITEM] " + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TID + "A999999 "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_EMAIL + "johndoe@gmail.com"
            + PREFIX_DELIVERY_TIMESTAMP + "2019-12-02 1500 "
            + PREFIX_WAREHOUSE + "5 Toh Guan Rd E, #02-30 S608831 "
            + PREFIX_COD + "$2 "
            + PREFIX_COMMENT + "NIL "
            + PREFIX_TYPE + "glass ";

    public static final String MESSAGE_SUCCESS = "New order added: %1$s";
    public static final String MESSAGE_DUPLICATE_ORDER = "This order already exists in the order book";

    private final Order toAdd;

    /**
     * Creates an InsertCommand to add the specified {@code Order}
     */
    public InsertCommand(Order order) {
        requireNonNull(order);
        toAdd = order;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasOrder(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ORDER);
        }

        model.addOrder(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InsertCommand // instanceof handles nulls
                && toAdd.equals(((InsertCommand) other).toAdd));
    }
}
