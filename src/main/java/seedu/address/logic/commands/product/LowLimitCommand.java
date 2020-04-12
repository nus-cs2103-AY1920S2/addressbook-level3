package seedu.address.logic.commands.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import static seedu.address.logic.commands.product.EditProductCommand.createEditedProduct;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_THRESHOLD;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.util.QuantityThreshold;
import seedu.address.ui.NotificationWindow;

/**
 * Sets the product low limit threshold for notifications.
 */
public class LowLimitCommand extends Command {

    public static final String COMMAND_WORD = "lowlimit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the notification threshold for products."
            + " Updates the balance indicator, as the threshold represents 20% of desired quantity. \n"
            + "Alerts the user when the product quantity reached the threshold set by the user. \n"
            + "Parameters: "
            + PREFIX_PRODUCT + "PRODUCT_INDEX "
            + PREFIX_THRESHOLD + "THRESHOLD \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PRODUCT + "1 "
            + PREFIX_THRESHOLD + "20";

    public static final String MESSAGE_SUCCESS = "New threshold set: %1$s";

    private final Index productIndex;
    private final EditProductDescriptor editProductDescriptor = new EditProductDescriptor();
    private QuantityThreshold threshold;

    public LowLimitCommand(Index productIndex, QuantityThreshold threshold) {
        requireNonNull(productIndex);
        requireNonNull(threshold);

        this.productIndex = productIndex;
        this.threshold = threshold;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Product> lastShownList = model.getFilteredProductList();

        if (productIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }

        Product productToEdit = lastShownList.get(productIndex.getZeroBased());
        int thresholdValue = threshold.value;
        Product editedProduct = createEditedProduct(productToEdit, editProductDescriptor);
        if (thresholdValue > 0) {
            editedProduct.setThreshold(String.valueOf(thresholdValue));
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_THRESHOLD_AMOUNT);
        }

        // update product list
        model.setProduct(productToEdit, editedProduct);
        model.updateFilteredProductList();

        // show notification if quantity < threshold
        if (editedProduct.getQuantity().getValue() <= thresholdValue) {
            NotificationWindow window = new NotificationWindow();
            window.show(editedProduct.getDescription(), editedProduct.getQuantity());
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedProduct));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LowLimitCommand)) {
            return false;
        }

        // state check
        LowLimitCommand e = (LowLimitCommand) other;

        return productIndex.equals(e.productIndex) && threshold.equals(e.threshold);
    }
}
