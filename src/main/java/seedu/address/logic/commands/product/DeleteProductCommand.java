package seedu.address.logic.commands.product;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;

/**
 * Deletes a product identified using it's displayed index from the product list.
 */
public class DeleteProductCommand extends Command {

    public static final String COMMAND_WORD = "deletep";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the product identified by the index number used in the displayed product list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PRODUCT_SUCCESS = "Deleted Product: %1$s";

    private final Index targetIndex;

    public DeleteProductCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Product> lastShownList = model.getFilteredProductList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
        }

        Product productToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteProduct(productToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PRODUCT_SUCCESS, productToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteProductCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteProductCommand) other).targetIndex)); // state check
    }
}
