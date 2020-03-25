package seedu.address.logic.commands.product;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THRESHOLD;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PRODUCTS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.util.QuantityThreshold;

/**
 * Sets the product low limit threshold for notifications.
 */
public class LowLimitCommand extends Command {

    public static final String COMMAND_WORD = "lowlimit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sets the notification threshold for products."
            + "Alerts the user when the product quantity reached the threshold set by the user. \n"
            + "Parameters: "
            + PREFIX_PRODUCT + "PRODUCT_INDEX "
            + PREFIX_THRESHOLD + "THRESHOLD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PRODUCT + "1 "
            + PREFIX_THRESHOLD + "20";

    public static final String MESSAGE_SUCCESS = "New threshold set: %1$s";

    private final Index productIndex;
    private QuantityThreshold threshold;

    public LowLimitCommand(Index productIndex, QuantityThreshold threshold) {
        this.productIndex = productIndex;
        this.threshold = threshold;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Product product = model.getFilteredProductList().get(productIndex.getZeroBased());
        product.setThreshold(threshold.toString());
        model.updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, product));
    }
}
