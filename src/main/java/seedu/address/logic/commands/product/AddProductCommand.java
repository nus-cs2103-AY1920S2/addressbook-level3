package seedu.address.logic.commands.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COSTPRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALES;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;

/**
 * Adds a product to the product list.
 */
public class AddProductCommand extends Command {

    public static final String COMMAND_WORD = "addp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a product to the product list.\n"
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_COSTPRICE + "COST PRICE "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_QUANTITY + "QUANTITY "
            + "[" + PREFIX_SALES + "SALES] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "iphone "
            + PREFIX_COSTPRICE + "400 "
            + PREFIX_PRICE + "1000 "
            + PREFIX_QUANTITY + "10 "
            + PREFIX_SALES + "100 ";

    public static final String MESSAGE_SUCCESS = "New product added: %1$s";

    private final Product toAdd;

    /**
     * Creates an AddProductCommand to add the specified {@code Product}
     */
    public AddProductCommand(Product product) {
        requireNonNull(product);
        toAdd = product;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasProduct(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.addProduct(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddProductCommand // instanceof handles nulls
                && toAdd.equals(((AddProductCommand) other).toAdd));
    }
}

