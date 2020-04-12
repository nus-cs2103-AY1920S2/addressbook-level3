package seedu.address.logic.commands.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PRODUCT;
import static seedu.address.logic.commands.product.EditProductCommand.EditProductDescriptor;
import static seedu.address.logic.commands.product.EditProductCommand.createEditedProduct;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.product.Product;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.util.Money;
import seedu.address.model.util.Quantity;

/**
 * Undo a transaction identified using it's displayed index from the transaction list.
 */
public class UndoTransactionCommand extends Command {

    public static final String COMMAND_WORD = "undot";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Undo the transaction identified by the index number used in the displayed transaction list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNDO_PERSON_SUCCESS = "Undo Transaction: %1$s";

    private final Index targetIndex;

    public UndoTransactionCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // delete transaction
        List<Transaction> lastShownList = model.getFilteredTransactionList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        Transaction transactionToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTransaction(transactionToDelete);

        // update product quantity and money
        updateProduct(model, transactionToDelete);
        return new CommandResult(String.format(MESSAGE_UNDO_PERSON_SUCCESS, transactionToDelete));
    }

    /**
     * Adds the quantity and money back to the product of the transaction to be undo.
     * @param model
     * @param transactionToUndo
     * @throws CommandException
     */
    private void updateProduct(Model model, Transaction transactionToUndo) throws CommandException {
        EditProductDescriptor editProductDescriptor = new EditProductDescriptor();
        Product productToEdit = model.findProductById(transactionToUndo.getProductId());

        Quantity oldQuantity = productToEdit.getQuantity();
        Quantity newQuantity = oldQuantity.plus(transactionToUndo.getQuantity());
        editProductDescriptor.setQuantity(newQuantity);

        Money oldSales = productToEdit.getMoney();
        Money newSales = oldSales.minus(transactionToUndo.getMoney());
        editProductDescriptor.setSales(newSales);

        Product editedProduct = createEditedProduct(productToEdit, editProductDescriptor);
        if (!productToEdit.isSameProduct(editedProduct) && model.hasProduct(editedProduct)) {
            throw new CommandException(MESSAGE_DUPLICATE_PRODUCT);
        }

        model.setProduct(productToEdit, editedProduct);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UndoTransactionCommand // instanceof handles nulls
                && targetIndex.equals(((UndoTransactionCommand) other).targetIndex)); // state check
    }
}

